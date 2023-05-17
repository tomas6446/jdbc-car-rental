package com.jdbc.carrental.mapper;

import com.jdbc.carrental.model.Reservation;
import com.jdbc.carrental.printer.PrintableTable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tomas Kozakas
 */
public class ReservationMapper {
    public Reservation map(ResultSet resultSet) {
        Reservation reservation = new Reservation();
        try {
            reservation.setReservationId(resultSet.getInt("reservation_id"));
            reservation.setCarModel(resultSet.getString("model"));
            reservation.setCustomerEmail(resultSet.getString("email"));
            reservation.setReservationDate(resultSet.getDate("reservation_date"));
            reservation.setExpirationDate(resultSet.getDate("expiration_date"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }
}
