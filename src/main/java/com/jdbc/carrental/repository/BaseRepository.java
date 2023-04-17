package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Tomas Kozakas
 */
@AllArgsConstructor
public abstract class BaseRepository<T> {
    protected DatabaseConnection databaseConnection;

    protected List<T> executeQuery(String query, Function<ResultSet, T> resultSetMapper) {
        List<T> results = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                results.add(resultSetMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public abstract List<T> getAll();

    public abstract void enter();

    public abstract void search();

    public abstract void update();

    public abstract void delete();
}