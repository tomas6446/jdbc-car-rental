package com.jdbc.carrental;


import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.printer.MenuPrinter;
import io.github.cdimascio.dotenv.Dotenv;

public class CarRental {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String username = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");
        DatabaseConnection databaseConnection = new DatabaseConnection(url, username, password);

        MenuPrinter menuPrinter = new MenuPrinter(databaseConnection);
        menuPrinter.displayMenu();
    }
}