package com.jdbc.carrental.mapper;

import com.jdbc.carrental.model.Reservation;

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
            reservation.setCarId(resultSet.getInt("car_id"));
            reservation.setCustomerId(resultSet.getInt("customer_id"));
            reservation.setReservationDate(resultSet.getDate("reservation_date"));
            reservation.setExpirationDate(resultSet.getDate("expiration_date"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }
}
