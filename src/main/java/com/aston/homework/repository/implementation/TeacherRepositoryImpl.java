package com.aston.homework.repository.implementation;

import com.aston.homework.repository.TeacherRepository;
import com.aston.homework.repository.util.ConnectionPool;
import com.aston.homework.entity.TeacherEntity;
import com.aston.homework.exception.NotFoundException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeacherRepositoryImpl implements TeacherRepository {
    private static final String INSERT_TEACHER = "INSERT INTO teacher (name) VALUES(?)";
    private static final String GET_TEACHER_BY_ID = "SELECT t.id teacher_id, t.name teacher_name, g.id group_id " +
            "FROM teacher as t " +
            "left join teacherandgroupofstudent tg on t.id = tg.id_teacher " +
            "left join groupofstudent g on g.id = tg.id_groupofstudent " +
            "where t.id = ? ";
    private static final String DELETE_TEACHER = "DELETE FROM teacher WHERE id = ?";
    private static final String UPDATE_TEACHER = "UPDATE teacher SET name = ? WHERE id = ?";
    private static final Logger LOG  = Logger.getLogger(TeacherRepositoryImpl.class.getName());
    private final ConnectionPool connectionPool;

    public TeacherRepositoryImpl() {
        this.connectionPool = new ConnectionPool();
    }

    @Override
    public TeacherEntity getById(Long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(GET_TEACHER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new NotFoundException("Role with this id not found");
            }
            TeacherEntity teacher = new TeacherEntity();
            teacher.setId(resultSet.getLong("teacher_id"));
            teacher.setName(resultSet.getString("teacher_name"));
            teacher.getIdsGroups().add(resultSet.getLong("group_id"));
            while (resultSet.next()) {
                teacher.getIdsGroups().add(resultSet.getLong("group_id"));
            }
            return teacher;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TeacherEntity create(TeacherEntity teacher) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement insertStatement = connection
                     .prepareStatement(INSERT_TEACHER, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, teacher.getName());
            insertStatement.executeUpdate();

            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            generatedKeys.next();
            long indexTeacherCreated = generatedKeys.getLong(1);
            LOG.info(String.format("teacher added by index %d", indexTeacherCreated));

            try (PreparedStatement relationshipStatement = connection
                    .prepareStatement("INSERT INTO teacherandgroupofstudent (id_teacher, id_groupofstudent) VALUES(?, ?)",
                            Statement.RETURN_GENERATED_KEYS)) {

                if (null != teacher.getIdsGroups() && !teacher.getIdsGroups().isEmpty()) {
                    for (Long id : teacher.getIdsGroups()) {
                        relationshipStatement.setLong(1, indexTeacherCreated);
                        relationshipStatement.setLong(2, id);
                        relationshipStatement.executeUpdate();
                    }
                }
                LOG.info(String.format("teacher relationships on groups added by index in table"));
            }

            return getById(indexTeacherCreated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TEACHER)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public TeacherEntity update(TeacherEntity teacher, long id) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_TEACHER);
            statement.setString(1, teacher.getName());
            statement.setLong(2, teacher.getId());
            statement.executeUpdate();

            if (teacher.getIdsGroups() != null && !teacher.getIdsGroups().isEmpty()) {
                PreparedStatement updateGroup = connection.prepareStatement("INSERT INTO teacherandgroupofstudent " +
                        "(id_teacher, id_groupofstudent) VALUES (?, ?)");
                for (Long idGroup : teacher.getIdsGroups()) {
                    updateGroup.setLong(1, teacher.getId());
                    updateGroup.setLong(2, idGroup);
                    statement.executeUpdate();
                }
            }
        } catch (SQLException exception) {
            System.out.println("При изменении информации об учителе возникла ошибка");
        }
        return getById(id);
    }
}

