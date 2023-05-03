package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarStatsMapper;
import com.jdbc.carrental.model.CarStats;

import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class CarRentalStatsRepository extends BaseRepository<CarStats> {
    private final CarStatsMapper carStatsMapper;

    public CarRentalStatsRepository(DatabaseConnection databaseConnection, CarStatsMapper carStatsMapper) {
        super(databaseConnection);
        this.carStatsMapper = carStatsMapper;
    }

    @Override
    public List<CarStats> getAll() {
        String query = "SELECT * FROM car_rental_stats";
        return executeQuery(query, carStatsMapper::mapRow);
    }

    @Override
    public void search() {

    }

    @Override
    public void enter() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
