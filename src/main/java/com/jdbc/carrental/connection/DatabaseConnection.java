package com.jdbc.carrental.connection;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Tomas Kozakas
 */
@AllArgsConstructor
public class DatabaseConnection {
    private String url;
    private String username;
    private String password;
    private String schema;

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url + "?currentSchema=" + schema, username, password);
        connection.setAutoCommit(false);
        return connection;
    }
}
