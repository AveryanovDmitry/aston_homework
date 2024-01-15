package com.aston.homework.homework_aston.dao.implementation;

import com.aston.homework.homework_aston.exception.NotFoundException;
import com.aston.homework.homework_aston.dao.GroupRepository;
import com.aston.homework.homework_aston.dao.util.ConnectionManager;
import com.aston.homework.homework_aston.entity.GroupEntity;
import lombok.extern.log4j.Log4j;

import java.sql.*;

@Log4j
public class GroupRepositoryImpl implements GroupRepository {
    private static final String GET_BY_ID = "select g.id group_id, g.name name, " +
            "t.name teacher_name, s.name student_name " +
            "from groupOfStudent g " +
            "left join teacherandgroupofstudent tg on g.id = tg.id_groupofstudent " +
            "left join teacher t on t.id = tg.id_teacher " +
            "left join student s on g.id = s.group_id " +
            "where g.id = ?";
    private static final String INSERT_GROUP = "INSERT INTO groupofstudent (name) VALUES(?)";
    private static final String DELETE_GROUP = "DELETE FROM groupofstudent WHERE id= ?";
    private static final String UPDATE_SQL = "UPDATE groupofstudent SET name = ? WHERE id = ?";

    public GroupEntity getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection
                     .prepareStatement(GET_BY_ID)) {
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
        try (Connection connection = ConnectionManager.open();
             PreparedStatement insertStatement = connection
                     .prepareStatement(INSERT_GROUP, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, group.getName());
            insertStatement.executeUpdate();

            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            generatedKeys.next();

            long index = generatedKeys.getLong(1);
            log.info(String.format("group added by index %d", index));
            group.setId(index);

            return group;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(DELETE_GROUP)) {
            statement.setLong(1, id);
            int deleted = statement.executeUpdate();
            if(deleted > 0)
                log.info("Deleted successfully");
            else
                log.info("Nothing deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GroupEntity update(GroupEntity user, long id) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);

            statement.setString(1, user.getName());
            statement.setLong(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("При изменении информации об группе возникла ошибка");
        }
        return user;
    }
}
