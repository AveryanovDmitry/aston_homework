package com.aston.homework.repository.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String PASSWORD_KEY = "2212";
    private static final String USERNAME_KEY = "postgres";
    private static final String DRIVER_NAME = "org.postgresql.Driver";
    private static final String URL_JDBC = "jdbc:postgresql://localhost:5432/postgres";
    private static final HikariDataSource hikariDataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setPassword(PASSWORD_KEY);
        config.setUsername(USERNAME_KEY);
        config.setDriverClassName(DRIVER_NAME);
        config.setJdbcUrl(URL_JDBC);

        hikariDataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }
}

