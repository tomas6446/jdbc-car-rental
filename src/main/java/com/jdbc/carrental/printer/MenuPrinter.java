package com.jdbc.carrental.printer;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarMapper;
import com.jdbc.carrental.mapper.CustomerMapper;
import com.jdbc.carrental.mapper.RentMapper;
import com.jdbc.carrental.mapper.ReservationMapper;
import com.jdbc.carrental.model.Customer;
import com.jdbc.carrental.repository.*;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class MenuPrinter {
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

    public void start() {
        clearScreen();
        try (Scanner scanner = new Scanner(System.in)) {
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
        } catch (SQLException e) {
            System.out.println("An error occurred. Please try again later");
        }
    }

    private void register() throws SQLException {
        Optional<Customer> customer = customerRepository.askInsert(currentUserId);
        if(customer.isPresent()) {
            customerRepository.enter(customer.get());
            findUser(customer.get().getEmail());
        }
    }

    private void login() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Customer email: ");
            String email = scanner.nextLine();

            findUser(email);
        }
    }

    private void findUser(String customer) {
        try {
            customerRepository.getAll().forEach(c -> {
                if (c.getEmail().equals(customer)) {
                    try {
                        currentUserId = c.getId();
                        System.out.println("Successfully connected. Your id: " + currentUserId);
                        displayMenu();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Invalid user.");
    }


    public void displayMenu() throws SQLException {
        clearScreen();
        try (Scanner scanner = new Scanner(System.in)) {
            int option;
            do {
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

    }


    private <T extends PrintableTable> void handle(String title, BaseRepository<T> repository) {
        clearScreen();
        try (Scanner scanner = new Scanner(System.in)) {
            int option;
            do {
                System.out.printf(("Choose an operation:%%n" +
                        "1. Add new%%n" +
                        "2. Search%%n" +
                        "3. Your %s%%n" +
                        "0. Go back%%n" +
                        "Enter your choice (1-5): ")
                        .formatted(title));
                option = scanner.nextInt();
                clearScreen();

                switch (option) {
                    case 1 -> {
                        TablePrinter.printTable(title, repository.getAll());
                        Optional<T> value = repository.askInsert(currentUserId);
                        if (value.isPresent()) {
                            repository.enter(value.get());
                        }
                    }
                    case 2 -> {
                        TablePrinter.printTable(title, repository.getAll());
                        TablePrinter.printTable("Found " + title, repository.search(repository.askSearch()));
                    }
                    case 3 -> myRepository(title, repository);
                    case 0 -> displayMenu();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } while (true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public <T extends PrintableTable> void myRepository(String title, BaseRepository<T> repository) {
        clearScreen();
        try (Scanner scanner = new Scanner(System.in)) {
            int option;
            do {
                TablePrinter.printTable("Your " + title, repository.getAll(currentUserId));
                System.out.printf("Choose an operation:%n" +
                        "1. Update%n" +
                        "2. Delete%n" +
                        "0. Go back%n" +
                        "Enter your choice (1-5): ");
                option = scanner.nextInt();
                clearScreen();

                switch (option) {
                    case 1 -> {
                        TablePrinter.printTable("Cars", carRepository.getAll());
                        int id = repository.getId();
                        if (id != 0) {
                            Optional<T> value = repository.askInsert(currentUserId);
                            if(value.isPresent()) {
                                repository.update(id, value.get());
                            }

                        }
                    }
                    case 2 -> {
                        int id = repository.getId();
                        if (id != 0 && (id == currentUserId)) {
                            repository.delete(currentUserId);
                        }
                    }
                    case 0 -> handle(title, repository);
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } while (true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
