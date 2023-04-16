package com.jdbc.carrental.mapper;

import com.jdbc.carrental.model.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tomas Kozakas
 */
public class ReservationRowMapper {
    public Reservation mapRow(ResultSet resultSet) {
        try {
            return new Reservation(
                    resultSet.getInt("reservation_id"),
                    resultSet.getInt("car_id"),
                    resultSet.getInt("customer_id"),
                    resultSet.getDate("reservation_date"),
                    resultSet.getDate("expiration_date")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
