package com.jdbc.carrental;


import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarRowMapper;
import com.jdbc.carrental.mapper.CustomerRowMapper;
import com.jdbc.carrental.mapper.RentRowMapper;
import com.jdbc.carrental.mapper.ReservationRowMapper;
import com.jdbc.carrental.model.Car;
import com.jdbc.carrental.model.Customer;
import com.jdbc.carrental.model.Rent;
import com.jdbc.carrental.model.Reservation;
import com.jdbc.carrental.repository.CarRepository;
import com.jdbc.carrental.repository.CustomerRepository;
import com.jdbc.carrental.repository.RentRepository;
import com.jdbc.carrental.repository.ReservationRepository;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String username = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");
        DatabaseConnection databaseConnection = new DatabaseConnection(url, username, password);

        CustomerRepository customerRepository = new CustomerRepository(databaseConnection, new CustomerRowMapper());
        RentRepository rentRepository = new RentRepository(databaseConnection, new RentRowMapper());
        ReservationRepository reservationRepository = new ReservationRepository(databaseConnection, new ReservationRowMapper());
        CarRepository carRepository = new CarRepository(databaseConnection, new CarRowMapper());

        // Print all customers
        List<Customer> customers = customerRepository.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer);
        }

        // Print all rents
        List<Rent> rents = rentRepository.getAllRents();
        for (Rent rent : rents) {
            System.out.println(rent);
        }

        // Print all reservations
        List<Reservation> reservations = reservationRepository.getAllReservations();
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }

        // Print all cars
        List<Car> cars = carRepository.getAllCars();
        for (Car car : cars) {
            System.out.println(car);
        }
    }
}
