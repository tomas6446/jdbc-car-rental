package com.jdbc.carrental.printer;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarMapper;
import com.jdbc.carrental.mapper.CustomerMapper;
import com.jdbc.carrental.mapper.RentMapper;
import com.jdbc.carrental.mapper.ReservationMapper;
import com.jdbc.carrental.model.Customer;
import com.jdbc.carrental.repository.*;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class MenuPrinter {
    private Scanner scanner = new Scanner(System.in);
    private final CustomerRepository customerRepository;
    private final RentRepository rentRepository;
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private int currentUserId;

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
                            "1. Rent car%n" +
                            "2. Reserve car%n" +
                            "3. Car list%n" +
                            "0. Exit%n" +
                            "Enter your choice (1-5): ");
            option = scanner.nextInt();
            clearScreen();
            switch (option) {
                case 1 -> {
                    TablePrinter.printTable("Cars", carRepository.getAll());
                    handle("Rent", rentRepository);
                }
                case 2 -> {
                    TablePrinter.printTable("Cars", carRepository.getAll());
                    handle("Reservation", reservationRepository);
                }
                case 3 -> TablePrinter.printTable("Cars", carRepository.getAll());
                case 0 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }

    public void start() throws SQLException {
        int choice;
        do {
            System.out.println("Please choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Enter your choice (0-2): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 0 -> System.out.println("Exiting the system. Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
    }

    private void register() throws SQLException {
        scanner = new Scanner(System.in);
        Customer customer = customerRepository.askInsert(currentUserId);
        customerRepository.enter(customer);
        System.out.println("Successfully registered");

        findUser(customer.getEmail());
        displayMenu();
    }

    private void findUser(String customer) throws SQLException {
        customerRepository.getAll().forEach(c -> {
            if (c.getEmail().equals(customer)) {
                try {
                    currentUserId = c.getCustomerId();
                    System.out.println("Successfully connected. Your id: " + currentUserId);
                    displayMenu();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void login() throws SQLException {
        scanner = new Scanner(System.in);
        System.out.print("Customer email: ");
        String email = scanner.nextLine();

        findUser(email);
        System.out.println("Invalid user.");
    }

    private <T extends PrintableTable> void handle(String title, BaseRepository<T> repository) throws SQLException {
        int option;
        do {
            TablePrinter.printTable(title, repository.getAll());

            System.out.printf("Choose an operation:%n" +
                    "1. Add new%n" +
                    "2. Search%n" +
                    "3. Update%n" +
                    "4. Delete%n" +
                    "0. Go back%n" +
                    "Enter your choice (1-5): ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> repository.enter(repository.askInsert(currentUserId));
                case 2 -> TablePrinter.printTable(title, repository.search(repository.askSearch()));
                case 3 -> {
                    TablePrinter.printTable("Cars", carRepository.getAll());
                    TablePrinter.printTable("Your " + title, repository.getAll(currentUserId));
                    repository.update(repository.getId(), repository.askInsert(currentUserId));
                }
                case 4 -> {
                    TablePrinter.printTable("Your " + title, repository.getAll(currentUserId));
                    if (repository.getId() == currentUserId) {
                        repository.delete(currentUserId);
                    }
                }
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
