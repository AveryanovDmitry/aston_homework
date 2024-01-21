package com.aston.homework.repository.implementation;

import com.aston.homework.repository.StudentRepository;
import com.aston.homework.repository.util.ConnectionPool;
import com.aston.homework.exception.NotFoundException;
import com.aston.homework.entity.StudentEntity;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentRepositoryImpl implements StudentRepository {
    private static final String GET_STUDENT_BY_ID = "SELECT * FROM student WHERE id = ?";
    private static final String INSERT_STUDENT = "INSERT INTO student (name, group_id) VALUES(?, ?)";
    private static final String DELETE_STUDENT = "DELETE FROM student WHERE id=?";
    private static final String UPDATE_STUDENT = "UPDATE student SET name = ?, group_id = ? WHERE id = ?";
    private static final Logger LOG  = Logger.getLogger(StudentRepositoryImpl.class.getName());
    private final ConnectionPool connectionPool;

    public StudentRepositoryImpl() {
        this.connectionPool = new ConnectionPool();
    }

    public StudentEntity getById(Long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(GET_STUDENT_BY_ID)) {
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
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement insertStatement = connection
                     .prepareStatement(INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, student.getName());
            insertStatement.setLong(2, student.getGroupID());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            generatedKeys.next();
            long index = generatedKeys.getLong(1);
            LOG.info(String.format("student added by index %d", index));

            return getById(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public StudentEntity update(StudentEntity student, long id) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT);
            statement.setString(1, student.getName());
            statement.setLong(2, student.getId());
            statement.setLong(3, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("При изменении информации о студенте возникла ошибка");
        }
        return getById(id);
    }
}
