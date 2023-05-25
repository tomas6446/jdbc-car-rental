package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarReservationMapper;
import com.jdbc.carrental.model.CarReservation;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CarReservationRepository extends BaseRepository<CarReservation> {
    private final CarReservationMapper carReservationMapper;

    public CarReservationRepository(DatabaseConnection databaseConnection, CarReservationMapper carReservationMapper) {
        super(databaseConnection);
        this.carReservationMapper = carReservationMapper;
    }

    @Override
    public List<CarReservation> getAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT car.car_id, reservation_id, car.model, reservation_date, expiration_date " +
                        "FROM reservation JOIN car on car.car_id = reservation.car_id")) {
            return executeQuery(preparedStatement, carReservationMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }

    @Override
    public List<CarReservation> getAll(int currentUserId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT car.car_id, reservation_id, car.model, reservation_date, expiration_date " +
                        "FROM reservation JOIN car on car.car_id = reservation.car_id " +
                        "WHERE customer_id = ?")) {
            preparedStatement.setInt(1, currentUserId);
            return executeQuery(preparedStatement, carReservationMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }

    @Override
    public List<CarReservation> search(SearchParam searchParam) {
        return null;
    }

    @Override
    public void enter(CarReservation car) {

    }

    @Override
    public void update(int id, CarReservation car) {
        try (PreparedStatement reservationStatement = connection.prepareStatement(
                "UPDATE reservation SET reservation_id = ?, reservation_date = ?, expiration_date = ? " +
                        "WHERE reservation_id = ?")) {
            reservationStatement.setInt(1, car.getReservationId());
            reservationStatement.setDate(2, car.getReservationDate());
            reservationStatement.setDate(3, car.getExpirationDate());
            reservationStatement.setInt(4, car.getReservationId());

            reservationStatement.executeUpdate();
        } catch (SQLException e) {
            rollbackTransaction();
        }

        try (PreparedStatement carStatement = connection.prepareStatement(
                "UPDATE car SET model = ?" +
                        "WHERE car_id = ?")) {
            carStatement.setString(1, car.getModel());
            carStatement.setInt(2, car.getCarId());

            carStatement.executeUpdate();
        } catch (SQLException e) {
            rollbackTransaction();
            e.printStackTrace();
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Optional<CarReservation> askInsert(int currentUserId, Scanner scanner) {
        System.out.print("Enter reservation id: ");
        int reservationId = scanner.nextInt();
        System.out.print("Enter car id: ");
        int carId = scanner.nextInt();
        System.out.print("Enter model: ");
        String model = scanner.next();

        DateInput dateInput = getDate(scanner);

        CarReservation carReservation = new CarReservation(currentUserId, reservationId, carId, model, dateInput.startDate(), dateInput.endDate());
        return Optional.of(carReservation);
    }
}

