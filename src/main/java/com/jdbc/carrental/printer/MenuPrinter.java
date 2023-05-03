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
                            "1. Customer rentals%n" +
                            "2. Rent%n" +
                            "3. Reservation%n" +
                            "4. Car%n" +
                            "5. Car rental stats%n" +
                            "0. Exit%n" +
                            "Enter your choice (1-5): ");
            option = scanner.nextInt();

            switch (option) {

                case 0 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }


    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
