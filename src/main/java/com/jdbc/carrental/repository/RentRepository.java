package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.RentRowMapper;
import com.jdbc.carrental.model.Rent;

import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class RentRepository extends BaseRepository<Rent> {
    private final RentRowMapper rentMapper;

    public RentRepository(DatabaseConnection databaseConnection, RentRowMapper rentMapper) {
        super(databaseConnection);
        this.rentMapper = rentMapper;
    }
    @Override
    public List<Rent> getAll() {
        String query = "SELECT * FROM rent";
        return executeQuery(query, rentMapper::mapRow);
    }

    @Override
    public void enter() {

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
