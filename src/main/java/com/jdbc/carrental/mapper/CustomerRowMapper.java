package com.jdbc.carrental.mapper;

import com.jdbc.carrental.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tomas Kozakas
 */
public class CustomerRowMapper {
    public Customer mapRow(ResultSet resultSet) {
        try {
            return new Customer(
                    resultSet.getInt("customer_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
