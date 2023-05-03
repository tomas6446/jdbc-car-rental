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

    public CustomerRepository(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }
    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public void enter() {

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
