package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.model.Customer;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class CustomerRepository extends BaseRepository<Customer> {

    public CustomerRepository(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public List<Customer> getAll() {
        return executeQuery("SELECT * FROM customer", resultSet -> {
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
        });
    }

    @Override
    public void enter(Customer customer) {
        String query = "INSERT INTO customer (name, email, phone) " +
                "VALUES ('" + customer.getName() + "', '" + customer.getEmail() + "', '" + customer.getPhone() + "')";
        executeInsert(query);
    }

    @Override
    public void search() {

    }

    @Override
    public void update(int id, Customer customer) {
        String query = "UPDATE Customer " +
                "SET name = '" + customer.getName() + "', " +
                "email = '" + customer.getEmail() + "', " +
                "phone = '" + customer.getPhone() + "' " +
                "WHERE customer_id = " + id;
        executeUpdate(query);
    }

    @Override
    public void delete(int id) {
        executeDelete("customer", "customer_id", id);
    }
}
