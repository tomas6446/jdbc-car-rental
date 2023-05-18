package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.RentMapper;
import com.jdbc.carrental.model.Rent;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
    public List<Rent> getAll() {
        return executeQuery("SELECT rent_id, car.model, " +
                "rent_date, return_date, amount_paid FROM rent " +
                "JOIN car ON rent.car_id = car.car_id", rentMapper::map);
    }

    @Override
    public List<Rent> getAll(int currentUserId) {
        return executeQuery("SELECT rent_id, car.model, " +
                "rent_date, return_date, amount_paid FROM rent " +
                "JOIN car ON rent.car_id = car.car_id " +
                "WHERE rent.customer_id = " + currentUserId, rentMapper::map);
    }

    @Override
    public void enter(Rent rent) {
        executeInsert("INSERT INTO rent (car_id, customer_id, rent_date, return_date) " +
                "VALUES (" + rent.getCarId() + ", " + rent.getCustomerId() + ", '" + rent.getRentDate() +
                "', '" + rent.getReturnDate() + "')");
    }

    @Override
    public List<Rent> search(SearchParam searchParam) {
        return executeQuery("SELECT rent_id, car.model, " +
                "rent_date, return_date, amount_paid FROM rent " +
                "JOIN car ON rent.car_id = car.car_id " +
                "WHERE " + searchParam.column() + searchParam.like(), rentMapper::map);
    }

    @Override
    public void update(int id, Rent rent) {
        executeUpdate("UPDATE rent SET " +
                "car_id = " + rent.getCarId() +
                ", customer_id = " + rent.getCustomerId() +
                ", rent_date = '" + rent.getRentDate() + "'" +
                ", return_date = '" + rent.getReturnDate() + "'" +
                ", amount_paid = " + rent.getAmountPaid() +
                " WHERE rent_id = " + id);
    }

    @Override
    public void delete(int id) {
        executeUpdate("DELETE FROM rent WHERE rent_id = " + id);
    }

    @Override
    public Optional<Rent> askInsert(int currentUserId) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("0 to cancel");
            System.out.print("Car id: ");
            int carId = scanner.nextInt();
            if (carId == 0) {
                return Optional.empty();
            }
            DateInput dateInput = getDate(scanner);
            return Optional.of(new Rent(carId, currentUserId, dateInput.startDate(), dateInput.endDate()));
        }
    }

}
