package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CustomerRowMapper;
import com.jdbc.carrental.model.Customer;

import java.util.List;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class CustomerRepository extends BaseRepository<Customer> {
    private final CustomerRowMapper customerMapper;

    public CustomerRepository(DatabaseConnection databaseConnection, CustomerRowMapper customerMapper) {
        super(databaseConnection);
        this.customerMapper = customerMapper;
    }
    @Override
    public List<Customer> getAll() {
        String query = "SELECT * FROM customer";
        return executeQuery(query, customerMapper::mapRow);
    }

    @Override
    public void enter() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();
        System.out.print("Enter customer phone number: ");
        String phone = scanner.nextLine();

        String query = "INSERT INTO customer (name, email, phone) " +
                "VALUES ('" + name + "', '" + email + "', '" + phone + "')";
        executeInsert(query);
    }

    @Override
    public void search() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
