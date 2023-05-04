package com.jdbc.carrental.printer;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.model.Customer;
import com.jdbc.carrental.repository.*;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class MenuPrinter {
    private final CustomerRepository customerRepository;
    private final RentRepository rentRepository;
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;

    public MenuPrinter(DatabaseConnection databaseConnection) {
        this.customerRepository = new CustomerRepository(databaseConnection);
        this.rentRepository = new RentRepository(databaseConnection);
        this.reservationRepository = new ReservationRepository(databaseConnection);
        this.carRepository = new CarRepository(databaseConnection);
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
                            "0. Exit%n" +
                            "Enter your choice (1-5): ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> handleCustomer();
                case 2 -> handleRent();
                case 3 -> handleReservation();
                case 4 -> handleCar();
                case 0 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }

    private <T extends PrintableTable> void printMenu(String title, BaseRepository<T> baseRepository) {
        clearScreen();
        TablePrinter.printTable(title, baseRepository.getAll());

        System.out.printf("Choose an operation:%n" +
                "1. Insert%n" +
                "2. Search%n" +
                "3. Update%n" +
                "4. Delete%n" +
                "0. Go back%n" +
                "Enter your choice (1-5): ");
    }

    private void handleCustomer() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            printMenu("Customer Table", customerRepository);
            option = scanner.nextInt();

            switch (option) {
                case 1 -> customerRepository.enter(getCustomer());
                case 2 -> customerRepository.search();
                case 3 -> customerRepository.update(getId(), getCustomer());
                case 4 -> customerRepository.delete(getId());
                case 0 -> displayMenu();
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }

    private int getId() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    @NotNull
    private Customer getCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone number: ");
        String number = scanner.nextLine();
        return new Customer(name, email, number);
    }

    private void handleRent() {

    }

    private void handleReservation() {

    }

    private void handleCar() {
    }


    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
