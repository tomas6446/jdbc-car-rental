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
        Scanner scanner = new Scanner(System.in);

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

    }
}
