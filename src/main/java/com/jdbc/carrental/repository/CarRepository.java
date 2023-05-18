package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarMapper;
import com.jdbc.carrental.model.Car;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class CarRepository extends BaseRepository<Car> {
    private final CarMapper carMapper;

    public CarRepository(DatabaseConnection databaseConnection, CarMapper carMapper) {
        super(databaseConnection);
        this.carMapper = carMapper;
    }

    @Override
    public List<Car> getAll() {
        return executeQuery("SELECT * FROM car", carMapper::map);
    }

    @Override
    public List<Car> getAll(int currentUserId) {
        return Collections.emptyList();
    }

    @Override
    public void enter(Car car) {
        executeInsert("INSERT INTO car (manufacturer, model, year, daily_rate) " +
                "VALUES ('" + car.getManufacturer() + "', '" + car.getModel() + "', '" + car.getYear() + "', '" + car.getDailyRate() + "')");
    }

    @Override
    public List<Car> search(SearchParam searchParam) {
        return executeQuery("SELECT * FROM reservation WHERE " + searchParam.column() + searchParam.like(),
                carMapper::map);
    }

    @Override
    public void update(int id, Car car) {
        executeUpdate("UPDATE car " +
                "SET manufacturer = '" + car.getManufacturer() + "', " +
                "model = '" + car.getModel() + "', " +
                "year = " + car.getYear() + ", " +
                "daily_rate = " + car.getDailyRate() + " " +
                "WHERE car_id = " + id);
    }

    @Override
    public void delete(int id) {
        executeUpdate("DELETE FROM car WHERE car_id = " + id);
    }

    @Override
    public Optional<Car> askInsert(int currentUserId) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Manufacturer: ");
            String manufacturer = scanner.nextLine();
            System.out.print("Model: ");
            String model = scanner.nextLine();
            System.out.print("Year: ");
            int year = scanner.nextInt();
            System.out.print("Daily rate: ");
            double daily_rate = scanner.nextDouble();
            return Optional.of(new Car(manufacturer, model, year, daily_rate));
        }
    }
}
