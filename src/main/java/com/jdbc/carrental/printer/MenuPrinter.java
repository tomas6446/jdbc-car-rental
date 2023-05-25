package com.jdbc.carrental.printer;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.*;
import com.jdbc.carrental.model.Customer;
import com.jdbc.carrental.repository.*;

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
    private final CarReservationRepository carReservationRepository;
    private int currentUserId;

    public MenuPrinter(DatabaseConnection databaseConnection) {
        this.customerRepository = new CustomerRepository(databaseConnection, new CustomerMapper());
        this.rentRepository = new RentRepository(databaseConnection, new RentMapper());
        this.reservationRepository = new ReservationRepository(databaseConnection, new ReservationMapper());
        this.carRepository = new CarRepository(databaseConnection, new CarMapper());
        this.carReservationRepository = new CarReservationRepository(databaseConnection, new CarReservationMapper());
    }

    public void start() {
        clearScreen();
        try (Scanner scanner = new Scanner(System.in)) {
            int option;
            do {
                System.out.println("Please choose an option:");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("0. Exit");
                System.out.print("Enter your choice (0-2): ");
                option = scanner.nextInt();

                switch (option) {
                    case 1 -> login();
                    case 2 -> register();
                    case 0 -> System.out.println("Exiting the system. Goodbye!");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
                System.out.println();
            } while (option != 0);
        }
    }

    private void register() {
        try (Scanner scanner = new Scanner(System.in)) {
            Optional<Customer> customer = customerRepository.askInsert(currentUserId, scanner);
            if (customer.isPresent()) {
                customerRepository.enter(customer.get());
                findUser(customer.get().getEmail());
            }
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
        customerRepository.getAll().forEach(c -> {
            if (c.getEmail().equals(customer)) {
                currentUserId = c.getId();
                System.out.println("Successfully connected. Your id: " + currentUserId);
                displayMenu();
            }
        });
        System.out.println("Invalid user.");
        start();
    }

    public void displayMenu() {
        clearScreen();
        try (Scanner scanner = new Scanner(System.in)) {
            int option;
            do {
                System.out.printf(
                        "Please choose an option:%n" +
                                "1. Rent car%n" +
                                "2. Reserve car%n" +
                                "3. Car list%n" +
                                "4. Car Reservation list%n" +
                                "0. Log out%n" +
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
                    case 4 -> handle("Car Reservation list", carReservationRepository);
                    case 0 -> start();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } while (option != 0);
        }
    }

    private <T extends PrintableTable> void handle(String title, BaseRepository<T> repository) {
        clearScreen();
        try (Scanner scanner = new Scanner(System.in)) {
            int option;
            TablePrinter.printTable("Cars", carRepository.getAll());
            do {
                TablePrinter.printTable(title, repository.getAll());
                System.out.printf(
                        "Choose an operation:%n" +
                                "1. Add new%n" +
                                "2. Search%n" +
                                "3. Your %s%n" +
                                "0. Go back%n" +
                                "Enter your choice (1-5): ",
                        title
                );
                option = scanner.nextInt();
                clearScreen();

                switch (option) {
                    case 1 -> {
                        TablePrinter.printTable(title, repository.getAll());
                        TablePrinter.printTable("Cars", carRepository.getAll());
                        Optional<T> value = repository.askInsert(currentUserId, scanner);
                        value.ifPresent(repository::enter);
                        clearScreen();
                    }
                    case 2 -> {
                        TablePrinter.printTable(title, repository.getAll());
                        BaseRepository.SearchParam searchParam = repository.askSearch(scanner);
                        clearScreen();
                        TablePrinter.printTable("Found " + title, repository.search(searchParam));
                    }
                    case 3 -> myRepository(title, repository);
                    case 0 -> displayMenu();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } while (option != 0);
        }
    }

    public <T extends PrintableTable> void myRepository(String title, BaseRepository<T> repository) {
        clearScreen();
        int option;
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                TablePrinter.printTable("Your " + title, repository.getAll(currentUserId));
                System.out.printf(
                        "Choose an operation:%n" +
                                "1. Update%n" +
                                "2. Delete%n" +
                                "0. Go back%n" +
                                "Enter your choice (1-5): "
                );
                option = scanner.nextInt();
                clearScreen();

                switch (option) {
                    case 1 -> {
                        TablePrinter.printTable("Cars", carRepository.getAll());
                        TablePrinter.printTable("Your " + title, repository.getAll(currentUserId));
                        int id = repository.getId(scanner);
                        if (id != 0) {
                            Optional<T> value = repository.askInsert(currentUserId, scanner);
                            value.ifPresent(t -> repository.update(id, t));
                        }
                        clearScreen();
                    }
                    case 2 -> {
                        TablePrinter.printTable("Your " + title, repository.getAll(currentUserId));
                        int id = repository.getId(scanner);
                        if (id != 0 && (id == currentUserId)) {
                            repository.delete(currentUserId);
                        }
                        clearScreen();
                    }
                    case 0 -> handle(title, repository);
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } while (option != 0);
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
