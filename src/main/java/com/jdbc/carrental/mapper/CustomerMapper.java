package com.jdbc.carrental.mapper;

import com.jdbc.carrental.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tomas Kozakas
 */
public class CustomerMapper {
    public Customer map(ResultSet resultSet) {
        Customer customer = new Customer();
        try {
            customer.setCustomerId(resultSet.getInt("customer_id"));
            customer.setName(resultSet.getString("name"));
            customer.setEmail(resultSet.getString("email"));
            customer.setPhone(resultSet.getString("phone"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }
}
