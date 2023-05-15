package com.jdbc.carrental.printer;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.repository.*;

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
        this.customerRepository = new CustomerRepository(databaseConnection);
        this.rentRepository = new RentRepository(databaseConnection);
        this.reservationRepository = new ReservationRepository(databaseConnection);
        this.carRepository = new CarRepository(databaseConnection);
    }


    public void displayMenu() {
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
                case 1 -> handle("Customer", customerRepository);
                case 2 -> handle("Rent", rentRepository);
                case 3 -> handle("Reservation", reservationRepository);
                case 4 -> handle("Car", carRepository);
                case 0 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }

    private <T extends PrintableTable> void handle(String title, BaseRepository<T> repository) {
        int option;
        do {
            clearScreen();
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
                case 1 -> repository.enter(repository.ask(scanner));
                case 2 -> repository.search();
                case 3 -> repository.update(repository.getId(scanner), repository.ask(scanner));
                case 4 -> repository.delete(repository.getId(scanner));
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
