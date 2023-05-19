package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.RentMapper;
import com.jdbc.carrental.model.Rent;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT rent_id, car.model, rent_date, return_date, amount_paid " +
                        "FROM rent JOIN car ON rent.car_id = car.car_id")) {
            return executeQuery(preparedStatement, rentMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Rent> getAll(int currentUserId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT rent_id, car.model, rent_date, return_date, amount_paid " +
                        "FROM rent JOIN car ON rent.car_id = car.car_id " +
                        "WHERE rent.customer_id = ?")) {
            preparedStatement.setInt(1, currentUserId);
            return executeQuery(preparedStatement, rentMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }

    @Override
    public void enter(Rent rent) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO rent (car_id, customer_id, rent_date, return_date) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setInt(1, rent.getCarId());
            preparedStatement.setInt(2, rent.getCustomerId());
            preparedStatement.setDate(3, (Date) rent.getRentDate());
            preparedStatement.setDate(4, (Date) rent.getReturnDate());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }

    @Override
    public void update(int id, Rent rent) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE rent SET car_id = ?, customer_id = ?, rent_date = ?, return_date = ?, amount_paid = ? WHERE rent_id = ?")) {
            preparedStatement.setInt(1, rent.getCarId());
            preparedStatement.setInt(2, rent.getCustomerId());
            preparedStatement.setDate(3, (Date) rent.getRentDate());
            preparedStatement.setDate(4, (Date) rent.getReturnDate());
            preparedStatement.setDouble(5, rent.getAmountPaid());
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }


    @Override
    public List<Rent> search(SearchParam searchParam) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT rent_id, car.model, rent_date, return_date, amount_paid " +
                        "FROM rent JOIN car ON rent.car_id = car.car_id " +
                        "WHERE " + searchParam.column() + " LIKE ?")) {
            preparedStatement.setString(1, "%" + searchParam.like() + "%");
            return executeQuery(preparedStatement, rentMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }


    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM rent WHERE rent_id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
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
