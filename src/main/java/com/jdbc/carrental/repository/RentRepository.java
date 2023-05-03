package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.RentRowMapper;
import com.jdbc.carrental.model.Rent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class RentRepository extends BaseRepository<Rent> {

    public RentRepository(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }
    @Override
    public List<Rent> getAll() {
        return null;
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
