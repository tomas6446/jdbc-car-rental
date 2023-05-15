package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.RentMapper;
import com.jdbc.carrental.model.Rent;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class RentRepository extends BaseRepository<Rent> {
    private final RentMapper rentMapper;

    public RentRepository(DatabaseConnection databaseConnection, RentMapper rentMapper) {
        super(databaseConnection);
        this.rentMapper = rentMapper;
    }

    @Override
    public List<Rent> getAll() throws SQLException {
        return executeQuery("SELECT * FROM rent", rentMapper::map);
    }

    @Override
    public void enter(Rent rent) throws SQLException {
        executeInsert("INSERT INTO rent (car_id, customer_id, rent_date, return_date) " +
                "VALUES (" + rent.getCarId() + ", " + rent.getCustomerId() + ", '" + rent.getRentDate() +
                "', '" + rent.getReturnDate() + "')");
    }

    @Override
    public List<Rent> search(SearchParam searchParam) throws SQLException {
        return executeQuery("SELECT * FROM rent WHERE " + searchParam.column() + searchParam.like(), rentMapper::map);
    }

    @Override
    public void update(int id, Rent rent) throws SQLException {
        executeUpdate("UPDATE rent SET " +
                "car_id = " + rent.getCarId() +
                ", customer_id = " + rent.getCustomerId() +
                ", rent_date = '" + rent.getRentDate() + "'" +
                ", return_date = '" + rent.getReturnDate() + "'" +
                ", amount_paid = " + rent.getAmountPaid() +
                " WHERE rent_id = " + id);
    }

    @Override
    public void delete(int id) throws SQLException {
        executeDelete("rent", "rent_id", id);
    }

    @Override
    public Rent askInsert() {
        System.out.print("Car id: ");
        int carId = scanner.nextInt();
        System.out.print("Customer id: ");
        int customerId = scanner.nextInt();

        DateInput dateInput = getDate(scanner);
        return new Rent(carId, customerId, dateInput.startDate(), dateInput.endDate());
    }

}
