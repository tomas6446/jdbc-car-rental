package com.jdbc.carrental.mapper;

import com.jdbc.carrental.model.Rent;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tomas Kozakas
 */
public class RentRowMapper {
    public Rent mapRow(ResultSet resultSet) {
        try {
            return new Rent(
                    resultSet.getInt("rent_id"),
                    resultSet.getInt("car_id"),
                    resultSet.getInt("customer_id"),
                    resultSet.getDate("rent_date"),
                    resultSet.getDate("return_date"),
                    resultSet.getDouble("amount_paid")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}