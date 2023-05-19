package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.ReservationMapper;
import com.jdbc.carrental.model.Reservation;

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
public class ReservationRepository extends BaseRepository<Reservation> {
    private final ReservationMapper reservationMapper;

    public ReservationRepository(DatabaseConnection databaseConnection, ReservationMapper reservationMapper) {
        super(databaseConnection);
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<Reservation> getAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT reservation.reservation_id, car.model, reservation.reservation_date, reservation.expiration_date " +
                        "FROM reservation JOIN car ON reservation.car_id = car.car_id")) {
            return executeQuery(preparedStatement, reservationMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Reservation> getAll(int currentUserId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT reservation.reservation_id, car.model, reservation.reservation_date, reservation.expiration_date " +
                        "FROM reservation JOIN car ON reservation.car_id = car.car_id " +
                        "WHERE customer_id = ?")) {
            preparedStatement.setInt(1, currentUserId);
            return executeQuery(preparedStatement, reservationMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }

    @Override
    public void enter(Reservation reservation) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO reservation (car_id, customer_id, reservation_date, expiration_date) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setInt(1, reservation.getCarId());
            preparedStatement.setInt(2, reservation.getCustomerId());
            preparedStatement.setDate(3, (Date) reservation.getReservationDate());
            preparedStatement.setDate(4, (Date) reservation.getExpirationDate());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }

    @Override
    public void update(int id, Reservation reservation) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE reservation SET car_id = ?, customer_id = ?, reservation_date = ?, expiration_date = ? WHERE reservation_id = ?")) {
            preparedStatement.setInt(1, reservation.getCarId());
            preparedStatement.setInt(2, reservation.getCustomerId());
            preparedStatement.setDate(3, (Date) reservation.getReservationDate());
            preparedStatement.setDate(4, (Date) reservation.getExpirationDate());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM reservation WHERE reservation_id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }

    @Override
    public List<Reservation> search(SearchParam searchParam) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT reservation.reservation_id, car.model, reservation.reservation_date, reservation.expiration_date " +
                        "FROM reservation JOIN car ON reservation.car_id = car.car_id " +
                        "WHERE " + searchParam.column() + " LIKE ?")) {
            preparedStatement.setString(1, "%" + searchParam.like() + "%");
            return executeQuery(preparedStatement, reservationMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Reservation> askInsert(int currentUserId) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("0 to cancel");
            System.out.print("Car id: ");
            int carId = scanner.nextInt();
            if (carId == 0) {
                return Optional.empty();
            }

            DateInput date = getDate(scanner);
            return Optional.of(new Reservation(carId, currentUserId, date.startDate(), date.endDate()));
        }
    }
}
