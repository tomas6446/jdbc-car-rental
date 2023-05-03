package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.ReservationRowMapper;
import com.jdbc.carrental.model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class ReservationRepository extends BaseRepository<Reservation> {

    public ReservationRepository(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public List<Reservation> getAll() {
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
