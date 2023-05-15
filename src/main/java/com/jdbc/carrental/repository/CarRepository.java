package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.model.Car;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class CarRepository extends BaseRepository<Car> {
    public CarRepository(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }
    @Override
    public List<Car> getAll() {
        return executeQuery("SELECT * FROM car", resultSet -> {
            Car car = new Car();
            try {
                car.setCarId(resultSet.getInt("car_id"));
                car.setManufacturer(resultSet.getString("manufacturer"));
                car.setModel(resultSet.getString("model"));
                car.setYear(resultSet.getInt("year"));
                car.setDailyRate(resultSet.getDouble("daily_rate"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return car;
        });
    }

    @Override
    public void enter(Car car) {
        String query = "INSERT INTO car (manufacturer, model, year, daily_rate) " +
                "VALUES ('" + car.getManufacturer() + "', '" + car.getModel() + "', '" + car.getYear() + "', '" + car.getDailyRate() + "')";
        executeInsert(query);
    }

    @Override
    public void search() {

    }

    @Override
    public void update(int id, Car car) {
        String query = "UPDATE car " +
                "SET manufacturer = '" + car.getManufacturer() + "', " +
                "model = '" + car.getModel() + "', " +
                "year = " + car.getYear() + ", " +
                "daily_rate = " + car.getDailyRate() + " " +
                "WHERE car_id = " + id;
        executeUpdate(query);
    }

    @Override
    public void delete(int id) {
        executeDelete("car", "car_id", id);
    }

    @Override
    public Car ask(Scanner scanner) {
        return null;
    }
}
