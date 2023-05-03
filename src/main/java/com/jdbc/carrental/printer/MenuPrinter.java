package com.jdbc.carrental.printer;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.*;
import com.jdbc.carrental.repository.*;

import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class MenuPrinter {
    private final CustomerRepository customerRepository;
    private final RentRepository rentRepository;
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final CarRentalStatsRepository carRentalStatsRepository;
    public MenuPrinter(DatabaseConnection databaseConnection) {
        this.customerRepository = new CustomerRepository(databaseConnection, new CustomerRowMapper());
        this.rentRepository = new RentRepository(databaseConnection, new RentRowMapper());
        this.reservationRepository = new ReservationRepository(databaseConnection, new ReservationRowMapper());
        this.carRepository = new CarRepository(databaseConnection, new CarRowMapper());
        this.carRentalStatsRepository = new CarRentalStatsRepository(databaseConnection, new CarStatsMapper());
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            clearScreen();
            System.out.printf(
                    "Please choose an option:%n" +
                            "1. Customer%n" +
                            "2. Rent%n" +
                            "3. Reservation%n" +
                            "4. Car%n" +
                            "5. Car rental stats%n" +
                            "6. Exit%n" +
                            "Enter your choice (1-5): ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> handleRepository(customerRepository);
                case 2 -> {
                    TablePrinter.printTable("Car Table", carRepository.getAll());
                    handleRepository(rentRepository);
                }
                case 3 -> {
                    TablePrinter.printTable("Car Table", carRepository.getAll());
                    handleRepository(reservationRepository);
                }
                case 4 -> handleRepository(carRepository);
                case 5 -> printStats();
                case 6 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }

    private void printStats() {
        Scanner scanner = new Scanner(System.in);
        int operation;

        TablePrinter.printTable("Car Stats Table", carRentalStatsRepository.getAll());
        System.out.printf("Choose an operation:%n" +
                "0. Go back%n" +
                "Enter your choice: ");
        operation = scanner.nextInt();
        do {
            if (operation == 0) {
                displayMenu();
            }
        } while (true);
    }

    private int displayOperations() {
        Scanner scanner = new Scanner(System.in);
        int operation;

        System.out.printf("Choose an operation:%n" +
                "1. Data search%n" +
                "2. Data entry%n" +
                "3. Data update%n" +
                "4. Data deletion%n" +
                "5. Go back%n" +
                "Enter your choice (1-5): ");
        operation = scanner.nextInt();
        if (operation == 5) {
            displayMenu();
        }
        return operation;
    }

    private <T extends PrintableTable> void handleRepository(BaseRepository<T> repository) {
        do {
            clearScreen();
            TablePrinter.printTable("Table", repository.getAll());
            int operation = displayOperations();
            switch (operation) {
                case 1 -> repository.search();
                case 2 -> repository.enter();
                case 3 -> repository.update();
                case 4 -> repository.delete();
                case 5 -> displayMenu();
                default -> {
                    System.out.printf("Invalid operation. Please try again.%n");
                    displayMenu();
                }
            }
        } while (true);
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
