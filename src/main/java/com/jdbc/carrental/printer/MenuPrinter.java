package com.jdbc.carrental.printer;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarMapper;
import com.jdbc.carrental.mapper.CustomerMapper;
import com.jdbc.carrental.mapper.RentMapper;
import com.jdbc.carrental.mapper.ReservationMapper;
import com.jdbc.carrental.repository.*;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class MenuPrinter {
    private final Scanner scanner = new Scanner(System.in);
    private final CustomerRepository customerRepository;
    private final RentRepository rentRepository;
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;

    public MenuPrinter(DatabaseConnection databaseConnection) {
        this.customerRepository = new CustomerRepository(databaseConnection, new CustomerMapper());
        this.rentRepository = new RentRepository(databaseConnection, new RentMapper());
        this.reservationRepository = new ReservationRepository(databaseConnection, new ReservationMapper());
        this.carRepository = new CarRepository(databaseConnection, new CarMapper());
    }


    public void displayMenu() throws SQLException {
        int option;
        do {
            clearScreen();
            System.out.printf(
                    "Please choose an option:%n" +
                            "1. Customer%n" +
                            "2. Rent%n" +
                            "3. Reservation%n" +
                            "4. Car%n" +
                            "0. Exit%n" +
                            "Enter your choice (1-5): ");
            option = scanner.nextInt();
            clearScreen();
            switch (option) {
                case 1 -> handle("Customer", customerRepository);
                case 2 -> {
                    TablePrinter.printTable("Car", carRepository.getAll());
                    handle("Rent", rentRepository);
                }
                case 3 -> {
                    TablePrinter.printTable("Car", carRepository.getAll());
                    handle("Reservation", reservationRepository);
                }
                case 4 -> handle("Car", carRepository);
                case 0 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }

    private <T extends PrintableTable> void handle(String title, BaseRepository<T> repository) throws SQLException {
        int option;
        do {
            TablePrinter.printTable(title, repository.getAll());

            System.out.printf("Choose an operation:%n" +
                    "1. Insert%n" +
                    "2. Search%n" +
                    "3. Update%n" +
                    "4. Delete%n" +
                    "0. Go back%n" +
                    "Enter your choice (1-5): ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> repository.enter(repository.askInsert());
                case 2 -> TablePrinter.printTable(title, repository.search(repository.askSearch()));
                case 3 -> repository.update(repository.getId(), repository.askInsert());
                case 4 -> repository.delete(repository.getId());
                case 0 -> displayMenu();
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }


    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
