package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarRowMapper;
import com.jdbc.carrental.model.Car;

import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class CarRepository extends BaseRepository<Car> {
    private final CarRowMapper carMapper;

    public CarRepository(DatabaseConnection databaseConnection, CarRowMapper carMapper) {
        super(databaseConnection);
        this.carMapper = carMapper;
    }

    public List<Car> getAllCars() {
        String query = "SELECT * FROM car";
        return executeQuery(query, carMapper::mapRow);
    }
}
