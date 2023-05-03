package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.model.Rent;
import com.jdbc.carrental.model.Reservation;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class RentRepository extends BaseRepository<Rent> {

    public RentRepository(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }
    @Override
    public List<Rent> getAll() {
        return executeQuery("SELECT * FROM rent", resultSet -> {
            Rent rent = new Rent();
            try {
                rent.setRentId(resultSet.getInt("rent_id"));
                rent.setCarId(resultSet.getInt("car_id"));
                rent.setCustomerId(resultSet.getInt("customer_id"));
                rent.setRentDate(resultSet.getDate("rent_date"));
                rent.setReturnDate(resultSet.getDate("return_date"));
                rent.setAmountPaid(resultSet.getDouble("amount_paid"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return rent;
        });
    }

    @Override
    public void enter(Rent rent) {
        String query = "INSERT INTO rent (car_id, customer_id, rent_date, return_date) " +
                "VALUES (" + rent.getCarId() + ", " + rent.getCustomerId() + ", '" + rent.getRentDate() +
                "', '" + rent.getReturnDate() + "')";

        executeInsert(query);
    }

    @Override
    public void search() {

    }

    @Override
    public void update(int id, Rent rent) {
        String query = "UPDATE Rent SET " +
                "car_id = " + rent.getCarId() +
                ", customer_id = " + rent.getCustomerId() +
                ", rent_date = '" + rent.getRentDate() + "'" +
                ", return_date = '" + rent.getReturnDate() + "'" +
                ", amount_paid = " + rent.getAmountPaid() +
                " WHERE rent_id = " + id;
        executeUpdate(query);
    }

    @Override
    public void delete(int id) {
        executeDelete("rent", "rent_id", id);
    }
}
