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
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Scanner;

public class CarRental {
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

        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            System.out.println("Welcome to the Car Rental System!");
            System.out.println("Please choose an option:");
            System.out.println("1. Search for a customer");
            System.out.println("2. Reserve a car");
            System.out.println("3. Update a reservation");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    searchCustomer();
                    break;
                case 2:
                    reserveCar();
                    break;
                case 3:
                    updateReservation();
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void searchCustomer() {
        System.out.println("Search customer function...");
        // Implement the search customer functionality here
    }

    private static void reserveCar() {
        System.out.println("Reserve car function...");
        // Implement the reserve car functionality here
    }

    private static void updateReservation() {
        System.out.println("Update reservation function...");
        // Implement the update reservation functionality here
    }
}
