package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * @author Tomas Kozakas
 */
public abstract class BaseRepository<T> implements Repository<T> {
    private Connection connection;
    protected BaseRepository(DatabaseConnection databaseConnection) {
        try {
            this.connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected List<T> executeQuery(String query, Function<ResultSet, T> resultSetMapper) {
        List<T> results = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                results.add(resultSetMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }


    protected void executeInsert(String query) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected void executeDelete(String tableName, String idName, int id) {
        String query = "DELETE FROM " + tableName + " WHERE " + idName + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void executeUpdate(String query) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId(Scanner scanner) {
        System.out.print("Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }
}