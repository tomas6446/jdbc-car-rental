package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * @author Tomas Kozakas
 */
public abstract class BaseRepository<T> implements Repository<T> {
    protected final Scanner scanner = new Scanner(System.in);
    private Connection connection;
    protected BaseRepository(DatabaseConnection databaseConnection) {
        try {
            this.connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected List<T> executeQuery(String query, Function<ResultSet, T> resultSetMapper) throws SQLException{
        List<T> results = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                results.add(resultSetMapper.apply(resultSet));
            }
        }
        return results;
    }


    protected void executeInsert(String query) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
    }

    protected void executeDelete(String table, String column, int id) throws SQLException {
        String query = "DELETE FROM " + table + " WHERE " + column + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    protected void executeUpdate(String query) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
    }


    public int getId() {
        System.out.print("Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    protected DateInput getDate(Scanner scanner) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateRegex = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
        Date reservationDate;
        while (true) {
            System.out.print("Start date (format [yyyy-mm-dd]): ");
            String dateString = scanner.next();
            if (dateString.matches(dateRegex)) {
                try {
                    reservationDate = formatter.parse(dateString);
                    break;
                } catch (ParseException e) {
                    System.out.println("Wrong date format. Try again");
                }
            } else {
                System.out.println("Wrong date format. Try again");
            }
        }
        Date expirationDate;
        while (true) {
            System.out.print("End date (format [yyyy-mm-dd]): ");
            String dateString = scanner.next();
            if (dateString.matches(dateRegex)) {
                try {
                    expirationDate = formatter.parse(dateString);
                    break;
                } catch (ParseException e) {
                    System.out.println("Wrong date format. Try again");
                }
            } else {
                System.out.println("Wrong date format. Try again");
            }
        }
        return new DateInput(reservationDate, expirationDate);
    }

    public SearchParam askSearch() {
        System.out.print("Column: ");
        String column = scanner.next();
        System.out.print("Like: ");
        String like = scanner.next();

        if (isValueInteger(like)) {
            like = " = " + like;
        } else if (isValueDate(like)) {
            like = " = '" + like + "'";
        } else if (isValueDouble(like)) {
            like = " = " + like;
        } else {
            like = " LIKE '%" + like + "%'";
        }

        return new SearchParam(column, like);
    }

    protected boolean isValueInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean isValueDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean isValueDate(String value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        try {
            sdf.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    protected record DateInput(Date startDate, Date endDate) {
    }

    protected record SearchParam(String column, String like) {

    }
}