package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarRowMapper;
import com.jdbc.carrental.model.Car;

import java.util.List;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class CarRepository extends BaseRepository<Car> {
    private final CarRowMapper carMapper;

    public CarRepository(DatabaseConnection databaseConnection, CarRowMapper carMapper) {
        super(databaseConnection);
        this.carMapper = carMapper;
    }
    @Override
    public List<Car> getAll() {
        String query = "SELECT * FROM car";
        return executeQuery(query, carMapper::mapRow);
    }

    @Override
    public void enter() {
        System.out.print("Enter car manufacturer: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Enter car model: ");
        String model = scanner.nextLine();
        System.out.print("Enter car year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter car daily_rate: ");
        double daily_rate = Double.parseDouble(scanner.nextLine());

        String query = "INSERT INTO car (manufacturer, model, year, daily_rate) " +
                "VALUES ('" + manufacturer + "', '" + model + "', '" + year + "', '" + daily_rate + "')";
        executeInsert(query);
    }

    @Override
    public void search() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {
        System.out.print("Enter car id: ");
        int id = Integer.parseInt(scanner.nextLine());

        executeDeleteById("reservation", "car_id", id);
        executeDeleteById("rent", "car_id", id);
        executeDeleteById("car", "car_id", id);
    }
}
