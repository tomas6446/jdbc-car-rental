package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.model.Reservation;

import java.sql.SQLException;
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
        return executeQuery("SELECT * FROM reservation", resultSet -> {
            Reservation reservation = new Reservation();
            try {
                reservation.setReservationId(resultSet.getInt("reservation_id"));
                reservation.setCarId(resultSet.getInt("car_id"));
                reservation.setCustomerId(resultSet.getInt("customer_id"));
                reservation.setReservationDate(resultSet.getDate("reservation_date"));
                reservation.setExpirationDate(resultSet.getDate("expiration_date"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return reservation;
        });
    }

    @Override
    public void enter(Reservation reservation) {
        String query = "INSERT INTO reservation (car_id, customer_id, reservation_date, expiration_date) " +
                "VALUES (" + reservation.getCarId() + ", " + reservation.getReservationId() + ", '" + reservation.getReservationDate() +
                "', '" + reservation.getExpirationDate() + "')";

        executeInsert(query);
    }

    @Override
    public void search() {

    }

    @Override
    public void update(int id, Reservation reservation) {
        String query = "UPDATE Reservation SET " +
                "car_id = " + reservation.getCarId() +
                ", customer_id = " + reservation.getCustomerId() +
                ", reservation_date = '" + reservation.getReservationDate() + "'" +
                ", expiration_date = '" + reservation.getExpirationDate() + "'" +
                " WHERE reservation_id = " + id;
        executeUpdate(query);
    }

    @Override
    public void delete(int id) {
        executeDelete("reservation", "reservation_id", id);
    }
}
