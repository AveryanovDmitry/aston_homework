package com.aston.homework.repository.implementation;

import com.aston.homework.exception.NotFoundException;
import com.aston.homework.repository.util.ConnectionPool;
import com.aston.homework.repository.GroupRepository;
import com.aston.homework.entity.GroupEntity;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupRepositoryImpl implements GroupRepository {
    private static final String GET_GROUP_BY_ID = "SELECT g.id group_id, g.name name, " +
            "t.name teacher_name, s.name student_name " +
            "FROM groupOfStudent g " +
            "LEFT JOIN teacherandgroupofstudent tg ON g.id = tg.id_groupofstudent " +
            "LEFT JOIN teacher t ON t.id = tg.id_teacher " +
            "LEFT JOIN student s ON g.id = s.group_id " +
            "WHERE g.id = ?";
    private static final String INSERT_GROUP = "INSERT INTO groupofstudent (name) VALUES(?)";
    private static final String DELETE_GROUP = "DELETE FROM groupofstudent WHERE id= ?";
    private static final String UPDATE_GROUP = "UPDATE groupofstudent SET name = ? WHERE id = ?";
    private static final Logger LOG  = Logger.getLogger(GroupRepositoryImpl.class.getName());
    private final ConnectionPool connectionPool;


    public GroupRepositoryImpl() {
        this.connectionPool = new ConnectionPool();
    }

    public GroupEntity getById(Long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(GET_GROUP_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new NotFoundException("group with this id not found");
            }
            GroupEntity groupEntity = new GroupEntity();
            groupEntity.setId(resultSet.getLong("group_id"));
            groupEntity.setName(resultSet.getString("name"));
            groupEntity.getStudents().add(resultSet.getString("student_name"));
            groupEntity.getTeachers().add(resultSet.getString("teacher_name"));
            while (resultSet.next()) {
                groupEntity.getStudents().add(resultSet.getString("student_name"));
                groupEntity.getTeachers().add(resultSet.getString("teacher_name"));
            }
            return groupEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GroupEntity create(GroupEntity group) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement insertStatement = connection
                     .prepareStatement(INSERT_GROUP, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, group.getName());
            insertStatement.executeUpdate();

            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            generatedKeys.next();

            long index = generatedKeys.getLong(1);
            LOG.info(String.format("group added by index %d", index));

            return getById(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_GROUP)) {
            statement.setLong(1, id);
            int deleted = statement.executeUpdate();
            if (deleted > 0) {
                LOG.info("Deleted successfully");
            } else {
                LOG.info("Nothing deleted");
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public GroupEntity update(GroupEntity group, long id) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_GROUP);

            statement.setString(1, group.getName());
            statement.setLong(2, group.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("При изменении информации об группе возникла ошибка");
        }
        return getById(id);
    }
}
