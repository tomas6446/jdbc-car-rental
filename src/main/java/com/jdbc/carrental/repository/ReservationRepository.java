package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.ReservationRowMapper;
import com.jdbc.carrental.model.Reservation;

import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class ReservationRepository extends BaseRepository<Reservation> {
    private final ReservationRowMapper reservationMapper;

    public ReservationRepository(DatabaseConnection databaseConnection, ReservationRowMapper reservationMapper) {
        super(databaseConnection);
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<Reservation> getAll() {
        String query = "SELECT * FROM reservation";
        return executeQuery(query, reservationMapper::mapRow);
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
