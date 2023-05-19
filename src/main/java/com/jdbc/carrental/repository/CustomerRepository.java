package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CustomerMapper;
import com.jdbc.carrental.model.Customer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
    public List<Customer> getAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer")) {
            return executeQuery(preparedStatement, customerMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Customer> getAll(int currentUserId) {
        return Collections.emptyList();
    }

    @Override
    public void enter(Customer customer) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO customer (name, email, phone) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }

    @Override
    public void update(int id, Customer customer) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE customer SET name = ?, email = ?, phone = ? WHERE customer_id = ?")) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM customer WHERE customer_id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }

    @Override
    public List<Customer> search(SearchParam searchParam) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM customer WHERE " + searchParam.column() + " LIKE ?")) {
            preparedStatement.setString(1, "%" + searchParam.like() + "%");
            return executeQuery(preparedStatement, customerMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }


    @Override
    public Optional<Customer> askInsert(int currentUserId, Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone number: ");
        String number = scanner.nextLine();
        return Optional.of(new Customer(name, email, number));
    }
}
