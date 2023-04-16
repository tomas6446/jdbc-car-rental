package com.jdbc.carrental.mapper;

import com.jdbc.carrental.model.Car;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tomas Kozakas
 */
public class CarRowMapper {
    public Car mapRow(ResultSet resultSet) {
        try {
            return new Car(
                    resultSet.getInt("car_id"),
                    resultSet.getString("manufacturer"),
                    resultSet.getString("model"),
                    resultSet.getInt("year"),
                    resultSet.getDouble("daily_rate")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
