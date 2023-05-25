package com.jdbc.carrental.mapper;

import com.jdbc.carrental.model.CarReservation;
import com.jdbc.carrental.model.Rent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarReservationMapper {
    public CarReservation map(ResultSet resultSet) {
        CarReservation carReservation = new CarReservation();
        try {
            //carReservation.setCustomerId(resultSet.getInt("customer_id"));
            carReservation.setCarId(resultSet.getInt("car_id"));
            carReservation.setReservationId(resultSet.getInt("reservation_id"));
            carReservation.setModel(resultSet.getString("model"));
            carReservation.setReservationDate(resultSet.getDate("reservation_date"));
            carReservation.setExpirationDate(resultSet.getDate("expiration_date"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carReservation;
    }
}
