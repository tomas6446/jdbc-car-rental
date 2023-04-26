package com.jdbc.carrental.menu;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarRowMapper;
import com.jdbc.carrental.mapper.CustomerRowMapper;
import com.jdbc.carrental.mapper.RentRowMapper;
import com.jdbc.carrental.mapper.ReservationRowMapper;
import com.jdbc.carrental.repository.CarRepository;
import com.jdbc.carrental.repository.CustomerRepository;
import com.jdbc.carrental.repository.RentRepository;
import com.jdbc.carrental.repository.ReservationRepository;

import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class Menu {
    private final DatabaseConnection databaseConnection;

    public Menu(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void displayMenu() {
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
            case 1 -> handleCustomer();
            case 2 -> handleRent();
            case 3 -> handleReservation();
            case 4 -> handleCar();
            case 5 -> {
                System.out.println("Exiting the system. Goodbye!");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid option. Please try again.");
                displayMenu();
            }
        }
    }

    private int displayOperations() {
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
            displayMenu();
        }

        return operation;
    }

    private void handleCustomer() {
        CustomerRepository customerRepository = new CustomerRepository(databaseConnection, new CustomerRowMapper());
        while (true) {
            int operation = displayOperations();
            switch (operation) {
                case 1 -> customerRepository.search();
                case 2 -> customerRepository.enter();
                case 3 -> customerRepository.update();
                case 4 -> customerRepository.delete();
                case 5 -> displayMenu();
                default -> {
                    System.out.printf("Invalid operation. Please try again.%n");
                    displayMenu();
                }
            }
        }

    }

    private void handleRent() {
        RentRepository rentRepository = new RentRepository(databaseConnection, new RentRowMapper());
        while (true) {
            int operation = displayOperations();
            switch (operation) {
                case 1 -> rentRepository.search();
                case 2 -> rentRepository.enter();
                case 3 -> rentRepository.update();
                case 4 -> rentRepository.delete();
                case 5 -> displayMenu();
                default -> {
                    System.out.printf("Invalid operation. Please try again.%n");
                    displayMenu();
                }
            }
        }
    }

    private void handleReservation() {
        var reservationRepository = new ReservationRepository(databaseConnection, new ReservationRowMapper());
        while (true) {
            int operation = displayOperations();
            switch (operation) {
                case 1 -> reservationRepository.search();
                case 2 -> reservationRepository.enter();
                case 3 -> reservationRepository.update();
                case 4 -> reservationRepository.delete();
                case 5 -> displayMenu();
                default -> {
                    System.out.printf("Invalid operation. Please try again.%n");
                    displayMenu();
                }
            }
        }
    }

    private void handleCar() {
        CarRepository carRepository = new CarRepository(databaseConnection, new CarRowMapper());
        while (true) {
            int operation = displayOperations();
            switch (operation) {
                case 1 -> carRepository.search();
                case 2 -> carRepository.enter();
                case 3 -> carRepository.update();
                case 4 -> carRepository.delete();
                case 5 -> displayMenu();
                default -> {
                    System.out.printf("Invalid operation. Please try again.%n");
                    displayMenu();
                }
            }
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}