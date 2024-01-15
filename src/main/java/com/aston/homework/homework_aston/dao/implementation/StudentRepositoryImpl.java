package com.aston.homework.homework_aston.dao.implementation;

import com.aston.homework.homework_aston.dao.StudentRepository;
import com.aston.homework.homework_aston.dao.util.ConnectionManager;
import com.aston.homework.homework_aston.exception.NotFoundException;
import com.aston.homework.homework_aston.entity.StudentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.*;

@Log4j
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {
    private static final String GET_BY_ID = "SELECT * FROM student WHERE id = ?";
    private static final String INSERT_STUDENT = "INSERT INTO student (name, group_id) VALUES(?, ?)";
    private static final String DELETE_STUDENT = "DELETE FROM student WHERE id=?";
    private static final String UPDATE_SQL = "UPDATE student SET name = ?, group_id = ? WHERE id = ?";

    public StudentEntity getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection
                     .prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NotFoundException("Student with this id not found");
            }
            StudentEntity student = new StudentEntity();
            student.setId(resultSet.getLong("id"));
            student.setName(resultSet.getString("name"));
            student.setGroupID(resultSet.getLong("group_id"));
            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public StudentEntity create(StudentEntity student) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement insertStatement = connection
                     .prepareStatement(INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, student.getName());
            insertStatement.setLong(2, student.getGroupID());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            generatedKeys.next();
            long index = generatedKeys.getLong(1);
            log.info(String.format("student added by index %d", index));
            student.setId(index);
            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StudentEntity update(StudentEntity student, long id) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, student.getName());
            statement.setLong(2, student.getId());
            statement.setLong(3, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("При изменении информации о студенте возникла ошибка");
        }
        return student;
    }
}
