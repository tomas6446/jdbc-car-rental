package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CustomerMapper;
import com.jdbc.carrental.model.Customer;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class CustomerRepository extends BaseRepository<Customer> {
    private final CustomerMapper customerMapper;

    public CustomerRepository(DatabaseConnection databaseConnection, CustomerMapper customerMapper) {
        super(databaseConnection);
        this.customerMapper = customerMapper;
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        return executeQuery("SELECT * FROM customer", customerMapper::map);
    }

    @Override
    public void enter(Customer customer) throws SQLException {
        executeInsert("INSERT INTO customer (name, email, phone) " +
                "VALUES ('" + customer.getName() + "', '" + customer.getEmail() + "', '" + customer.getPhone() + "')");
    }

    @Override
    public List<Customer> search(SearchParam searchParam) throws SQLException {
        return executeQuery("SELECT * FROM customer WHERE " + searchParam.column() + searchParam.like(), customerMapper::map);
    }

    @Override
    public void update(int id, Customer customer) throws SQLException {
        executeUpdate("UPDATE customer " +
                "SET name = '" + customer.getName() + "', " +
                "email = '" + customer.getEmail() + "', " +
                "phone = '" + customer.getPhone() + "' " +
                "WHERE customer_id = " + id);
    }

    @Override
    public void delete(int id) throws SQLException {
        executeDelete("customer", "customer_id", id);
    }

    @Override
    public Customer askInsert() {

        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone number: ");
        String number = scanner.nextLine();
        return new Customer(name, email, number);
    }
}
