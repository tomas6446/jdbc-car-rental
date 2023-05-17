package com.jdbc.carrental.mapper;

import com.jdbc.carrental.model.Rent;
import com.jdbc.carrental.printer.PrintableTable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tomas Kozakas
 */
public class RentMapper {
    public Rent map(ResultSet resultSet) {
        Rent rent = new Rent();
        try {
            rent.setRentId(resultSet.getInt("rent_id"));
            rent.setCarId(resultSet.getInt("car_id"));
            rent.setCustomerId(resultSet.getInt("customer_id"));
            rent.setRentDate(resultSet.getDate("rent_date"));
            rent.setReturnDate(resultSet.getDate("return_date"));
            rent.setAmountPaid(resultSet.getDouble("amount_paid"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rent;
    }
}
