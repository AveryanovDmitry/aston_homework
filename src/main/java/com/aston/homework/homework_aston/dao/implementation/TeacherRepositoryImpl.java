package com.aston.homework.homework_aston.dao.implementation;

import com.aston.homework.homework_aston.dao.TeacherRepository;
import com.aston.homework.homework_aston.dao.util.ConnectionManager;
import com.aston.homework.homework_aston.entity.TeacherEntity;
import com.aston.homework.homework_aston.exception.NotFoundException;
import lombok.extern.log4j.Log4j;

import java.sql.*;

@Log4j
public class TeacherRepositoryImpl implements TeacherRepository {
    private static final String INSERT_TEACHER = "INSERT INTO teacher (name) VALUES(?)";
    private static final String GET_BY_ID = "SELECT t.id teacher_id, t.name teacher_name, g.id group_id " +
            "FROM teacher as t " +
            "left join teacherandgroupofstudent tg on t.id = tg.id_teacher " +
            "left join groupofstudent g on g.id = tg.id_groupofstudent " +
            "where t.id = ? ";
    private static final String DELETE_TEACHER = "DELETE FROM teacher WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE teacher SET name = ? WHERE id = ?";

    @Override
    public TeacherEntity getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection
                     .prepareStatement(GET_BY_ID)) {
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
        try (Connection connection = ConnectionManager.open();
             PreparedStatement insertStatement = connection
                     .prepareStatement(INSERT_TEACHER, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, teacher.getName());
            insertStatement.executeUpdate();

            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            generatedKeys.next();
            long indexTeacherCreated = generatedKeys.getLong(1);
            log.info(String.format("teacher added by index %d", indexTeacherCreated));

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
                log.info(String.format("teacher relationships on groups added by index in table"));
            }

            teacher.setId(indexTeacherCreated);
            return teacher;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(DELETE_TEACHER)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TeacherEntity update(TeacherEntity teacher, long id) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, teacher.getName());
            statement.setLong(2, teacher.getId());
            statement.executeUpdate();

            if (teacher.getIdsGroups() != null && !teacher.getIdsGroups().isEmpty()) {
                PreparedStatement updateGroup = connection.prepareStatement("INSERT INTO teacherandgroupofstudent " +
                        "(id_teacher, id_groupofstudent) VALUES (?, ?)");
                for (Long idGroup: teacher.getIdsGroups()){
                    updateGroup.setLong(1, teacher.getId());
                    updateGroup.setLong(2, idGroup);
                    statement.executeUpdate();
                }
            }
        } catch (SQLException exception) {
            System.out.println("При изменении информации об учителе возникла ошибка");
        }
        return teacher;
    }
}

