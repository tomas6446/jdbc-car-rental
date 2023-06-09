package com.jdbc.carrental.mapper;

import com.jdbc.carrental.model.Car;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tomas Kozakas
 */
public class CarMapper {
    public Car map(ResultSet resultSet) {
        Car car = new Car();
        try {
            car.setId(resultSet.getInt("car_id"));
            car.setManufacturer(resultSet.getString("manufacturer"));
            car.setModel(resultSet.getString("model"));
            car.setYear(resultSet.getInt("year"));
            car.setDailyRate(resultSet.getDouble("daily_rate"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }
}
