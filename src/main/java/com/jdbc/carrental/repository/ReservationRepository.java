package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.ReservationMapper;
import com.jdbc.carrental.model.Reservation;

import java.sql.SQLException;
import java.util.List;

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
    public List<Reservation> getAll() throws SQLException {
        return executeQuery("SELECT reservation.reservation_id, car.model, customer.email, " +
                "reservation.reservation_date, reservation.expiration_date FROM reservation " +
                "JOIN car ON reservation.car_id = car.car_id " +
                "JOIN customer ON reservation.customer_id = customer.customer_id", reservationMapper::map);
    }

    @Override
    public List<Reservation> getAll(int currentUserId) throws SQLException {
        return executeQuery("SELECT reservation.reservation_id, car.model, customer.email, " +
                "reservation.reservation_date, reservation.expiration_date" +
                "FROM reservation " +
                "JOIN car ON reservation.car_id = car.car_id " +
                "JOIN customer ON reservation.customer_id = customer.customer_id " +
                "WHERE customer.customer_id = " + currentUserId, reservationMapper::map);
    }

    @Override
    public void enter(Reservation reservation) throws SQLException {
//        executeInsert("INSERT INTO reservation (car_id, customer_id, reservation_date, expiration_date) " +
//                "VALUES (" + reservation.getCarId() + ", " + reservation.getCustomerId() + ", '" + reservation.getReservationDate() +
//                "', '" + reservation.getExpirationDate() + "')");
    }

    @Override
    public List<Reservation> search(SearchParam searchParam) throws SQLException {
        return executeQuery("SELECT * FROM reservation WHERE " + searchParam.column() + searchParam.like(), reservationMapper::map);
    }


    @Override
    public void update(int id, Reservation reservation) throws SQLException {
//        executeUpdate("UPDATE reservation SET " +
//                "car_id = " + reservation.getCarId() +
//                ", customer_id = " + reservation.getCustomerId() +
//                ", reservation_date = '" + reservation.getReservationDate() + "'" +
//                ", expiration_date = '" + reservation.getExpirationDate() + "'" +
//                " WHERE reservation_id = " + id);
    }

    @Override
    public void delete(int id) throws SQLException {
        executeDelete("reservation", "reservation_id", id);
    }

    @Override
    public Reservation askInsert(int currentUserId) {
//        System.out.print("Car id: ");
//        int carId = scanner.nextInt();
//
//        DateInput date = getDate(scanner);
//        return new Reservation(carId, currentUserId, date.startDate(), date.endDate());
        return null;
    }
}
