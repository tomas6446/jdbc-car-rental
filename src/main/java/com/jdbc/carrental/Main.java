package com.jdbc.carrental;


import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarRowMapper;
import com.jdbc.carrental.mapper.CustomerRowMapper;
import com.jdbc.carrental.mapper.RentRowMapper;
import com.jdbc.carrental.mapper.ReservationRowMapper;
import com.jdbc.carrental.repository.CarRepository;
import com.jdbc.carrental.repository.CustomerRepository;
import com.jdbc.carrental.repository.RentRepository;
import com.jdbc.carrental.repository.ReservationRepository;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5433/postgres";
        String username = "postgres";
        String password = "password";

        DatabaseConnection databaseConnection = new DatabaseConnection(url, username, password);

        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        RentRowMapper rentRowMapper = new RentRowMapper();
        ReservationRowMapper reservationRowMapper = new ReservationRowMapper();
        CarRowMapper carRowMapper = new CarRowMapper();

        CustomerRepository customerRepository = new CustomerRepository(databaseConnection, customerRowMapper);
        RentRepository rentRepository = new RentRepository(databaseConnection, rentRowMapper);
        ReservationRepository reservationRepository = new ReservationRepository(databaseConnection, reservationRowMapper);
        CarRepository carRepository = new CarRepository(databaseConnection, carRowMapper);


    }
}
