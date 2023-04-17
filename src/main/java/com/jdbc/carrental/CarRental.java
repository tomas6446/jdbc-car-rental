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

@SuppressWarnings("ALL")
public class CarRental {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String username = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");
        DatabaseConnection databaseConnection = new DatabaseConnection(url, username, password);

//        CustomerRepository customerRepository = new CustomerRepository(databaseConnection, new CustomerRowMapper());
//        RentRepository rentRepository = new RentRepository(databaseConnection, new RentRowMapper());
//        ReservationRepository reservationRepository = new ReservationRepository(databaseConnection, new ReservationRowMapper());
//        CarRepository carRepository = new CarRepository(databaseConnection, new CarRowMapper());

        displayMenu(databaseConnection);
    }

    private static void displayMenu(DatabaseConnection databaseConnection) {
        Scanner scanner = new Scanner(System.in);
        int option;

        clearScreen();
        System.out.printf("Welcome to the Car Rental System!%n" +
                "Please choose an option:%n" +
                "1. Customer%n" +
                "2. Rent%n" +
                "3. Reservation%n" +
                "4. Car%n" +
                "5. Exit%n" +
                "Enter your choice (1-5): ");
        option = scanner.nextInt();

        switch (option) {
            case 1 -> handleCustomer(databaseConnection);
            case 2 -> handleRent(databaseConnection);
            case 3 -> handleReservation(databaseConnection);
            case 4 -> handleCar(databaseConnection);
            case 5 -> {
                System.out.println("Exiting the system. Goodbye!");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid option. Please try again.");
                displayMenu(databaseConnection);
            }
        }
    }

    private static int displayOperations(DatabaseConnection databaseConnection) {
        Scanner scanner = new Scanner(System.in);
        int operation;

        clearScreen();
        System.out.printf("Choose an operation:%n" +
                "1. Data search%n" +
                "2. Data entry%n" +
                "3. Data update (modification)%n" +
                "4. Data deletion (removal)%n" +
                "5. Go back%n" +
                "Enter your choice (1-5): ");
        operation = scanner.nextInt();
        if (operation == 5) {
            displayMenu(databaseConnection);
        }

        return operation;
    }

    private static void handleCustomer(DatabaseConnection databaseConnection) {
        CustomerRepository customerRepository = new CustomerRepository(databaseConnection, new CustomerRowMapper());
        while (true) {
            int operation = displayOperations(databaseConnection);
            switch (operation) {
                case 1 -> customerRepository.search();
                case 2 -> customerRepository.enter();
                case 3 -> customerRepository.update();
                case 4 -> customerRepository.delete();
                case 5 -> displayMenu(databaseConnection);
                default -> {
                    System.out.printf("Invalid operation. Please try again.%n");
                    displayMenu(databaseConnection);
                }
            }
        }

    }

    private static void handleRent(DatabaseConnection databaseConnection) {
        RentRepository rentRepository = new RentRepository(databaseConnection, new RentRowMapper());
        while (true) {
            int operation = displayOperations(databaseConnection);
            switch (operation) {
                case 1 -> rentRepository.search();
                case 2 -> rentRepository.enter();
                case 3 -> rentRepository.update();
                case 4 -> rentRepository.delete();
                case 5 -> displayMenu(databaseConnection);
                default -> {
                    System.out.printf("Invalid operation. Please try again.%n");
                    displayMenu(databaseConnection);
                }
            }
        }
    }

    private static void handleReservation(DatabaseConnection databaseConnection) {
        var reservationRepository = new ReservationRepository(databaseConnection, new ReservationRowMapper());
        while (true) {
            int operation = displayOperations(databaseConnection);
            switch (operation) {
                case 1 -> reservationRepository.search();
                case 2 -> reservationRepository.enter();
                case 3 -> reservationRepository.update();
                case 4 -> reservationRepository.delete();
                case 5 -> displayMenu(databaseConnection);
                default -> {
                    System.out.printf("Invalid operation. Please try again.%n");
                    displayMenu(databaseConnection);
                }
            }
        }
    }

    private static void handleCar(DatabaseConnection databaseConnection) {
        CarRepository carRepository = new CarRepository(databaseConnection, new CarRowMapper());
        while (true) {
            int operation = displayOperations(databaseConnection);
            switch (operation) {
                case 1 -> carRepository.search();
                case 2 -> carRepository.enter();
                case 3 -> carRepository.update();
                case 4 -> carRepository.delete();
                case 5 -> displayMenu(databaseConnection);
                default -> {
                    System.out.printf("Invalid operation. Please try again.%n");
                    displayMenu(databaseConnection);
                }
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}