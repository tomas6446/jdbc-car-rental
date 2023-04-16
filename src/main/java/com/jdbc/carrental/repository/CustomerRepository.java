package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CustomerRowMapper;
import com.jdbc.carrental.model.Customer;

import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class CustomerRepository extends BaseRepository<Customer> {
    private final CustomerRowMapper customerMapper;

    public CustomerRepository(DatabaseConnection databaseConnection, CustomerRowMapper customerMapper) {
        super(databaseConnection);
        this.customerMapper = customerMapper;
    }

    public List<Customer> getAllCustomers() {
        String query = "SELECT * FROM customer";
        return executeQuery(query, customerMapper::mapRow);
    }
}
