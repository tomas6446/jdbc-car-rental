package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.ReservationMapper;
import com.jdbc.carrental.model.Reservation;

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
        return executeQuery("SELECT reservation.reservation_id, car.model, " +
                "reservation.reservation_date, reservation.expiration_date FROM reservation " +
                "JOIN car ON reservation.car_id = car.car_id", reservationMapper::map);
    }

    @Override
    public List<Reservation> getAll(int currentUserId) {
        return executeQuery("SELECT reservation.reservation_id, car.model, " +
                "reservation.reservation_date, reservation.expiration_date FROM reservation " +
                "JOIN car ON reservation.car_id = car.car_id " +
                "WHERE customer_id = " + currentUserId, reservationMapper::map);
    }

    @Override
    public void enter(Reservation reservation) {
        executeInsert("INSERT INTO reservation (car_id, customer_id, reservation_date, expiration_date) " +
                "VALUES (" + reservation.getCarId() + ", " + reservation.getCustomerId() + ", '" + reservation.getReservationDate() +
                "', '" + reservation.getExpirationDate() + "')");
    }

    @Override
    public List<Reservation> search(SearchParam searchParam) {
        return executeQuery("SELECT reservation.reservation_id, car.model, " +
                "reservation.reservation_date, reservation.expiration_date FROM reservation " +
                "JOIN car ON reservation.car_id = car.car_id " +
                "WHERE " + searchParam.column() + searchParam.like(), reservationMapper::map);
    }


    @Override
    public void update(int id, Reservation reservation) {
        executeUpdate("UPDATE reservation SET " +
                "car_id = " + reservation.getCarId() +
                ", customer_id = " + reservation.getCustomerId() +
                ", reservation_date = '" + reservation.getReservationDate() + "'" +
                ", expiration_date = '" + reservation.getExpirationDate() + "'" +
                " WHERE reservation_id = " + id);
    }

    @Override
    public void delete(int id) {
        executeUpdate("DELETE FROM reservation WHERE reservation_id = " + id);
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
