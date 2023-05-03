package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.RentRowMapper;
import com.jdbc.carrental.model.Rent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class RentRepository extends BaseRepository<Rent> {
    private final RentRowMapper rentMapper;

    public RentRepository(DatabaseConnection databaseConnection, RentRowMapper rentMapper) {
        super(databaseConnection);
        this.rentMapper = rentMapper;
    }
    @Override
    public List<Rent> getAll() {
        String query = "SELECT * FROM rent";
        return executeQuery(query, rentMapper::mapRow);
    }

    @Override
    public void enter() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date rentDate;
        Date returnDate;

        System.out.print("Enter car ID: ");
        int carId = scanner.nextInt();

        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();

        System.out.print("Enter rent date (YYYY-MM-DD): ");
        String rentDateStr = scanner.next();
        try {
            rentDate = dateFormat.parse(rentDateStr);
        } catch (ParseException e) {
            System.err.println("Invalid rent date format. Expected format: YYYY-MM-DD");
            return;
        }

        System.out.print("Enter return date (YYYY-MM-DD): ");
        String returnDateStr = scanner.next();
        try {
            returnDate = dateFormat.parse(returnDateStr);
        } catch (ParseException e) {
            System.err.println("Invalid return date format. Expected format: YYYY-MM-DD");
            return;
        }

        System.out.print("Enter amount paid: ");
        double amountPaid = scanner.nextDouble();

        String query = "INSERT INTO rent (car_id, customer_id, rent_date, return_date, amount_paid) " +
                "VALUES (" + carId + ", " + customerId + ", '" + dateFormat.format(rentDate) +
                "', '" + dateFormat.format(returnDate) + "', " + amountPaid + ")";

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
        System.out.print("Enter rent id: ");
        int id = Integer.parseInt(scanner.nextLine());

        executeDeleteById("rent", "rent_od", id);
    }
}
