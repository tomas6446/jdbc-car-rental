package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.ReservationRowMapper;
import com.jdbc.carrental.model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class ReservationRepository extends BaseRepository<Reservation> {
    private final ReservationRowMapper reservationMapper;

    public ReservationRepository(DatabaseConnection databaseConnection, ReservationRowMapper reservationMapper) {
        super(databaseConnection);
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<Reservation> getAll() {
        String query = "SELECT * FROM reservation";
        return executeQuery(query, reservationMapper::mapRow);
    }

    @Override
    public void enter() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date reservationDate;
        Date expirationDate;

        System.out.print("Enter car ID: ");
        int carId = scanner.nextInt();

        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();

        System.out.print("Enter reservation_date date (YYYY-MM-DD): ");
        String reservationDateStr = scanner.next();
        try {
            reservationDate = dateFormat.parse(reservationDateStr);
        } catch (ParseException e) {
            System.err.println("Invalid rent date format. Expected format: YYYY-MM-DD");
            return;
        }

        System.out.print("Enter expiration_date (YYYY-MM-DD): ");
        String expirationDateStr = scanner.next();
        try {
            expirationDate = dateFormat.parse(expirationDateStr);
        } catch (ParseException e) {
            System.err.println("Invalid return date format. Expected format: YYYY-MM-DD");
            return;
        }

        String query = "INSERT INTO reservation (car_id, customer_id, reservation_date, expiration_date) " +
                "VALUES (" + carId + ", " + customerId + ", '" + dateFormat.format(reservationDate) +
                "', '" + dateFormat.format(expirationDate) +"')";

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
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter reservation id: ");
        int id = Integer.parseInt(scanner.nextLine());

        executeDeleteById("reservation", "reservation_id", id);
    }
}
