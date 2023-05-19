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
    protected Connection connection;

    protected BaseRepository(DatabaseConnection databaseConnection) {
        try {
            this.connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected List<T> executeQuery(PreparedStatement preparedStatement, Function<ResultSet, T> resultSetMapper) {
        List<T> results = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                results.add(resultSetMapper.apply(resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return results;
    }

    protected void rollbackTransaction() {
        if (connection != null) {
            try {
                System.err.print("Transaction is being rolled back");
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
    }

    public int getId() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Id: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            return id;
        }
    }

    protected DateInput getDate(Scanner scanner) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date reservationDate = askDate("Start date (format [yyyy-mm-dd]): ", scanner, formatter);
        Date expirationDate = askDate("End date (format [yyyy-mm-dd]): ", scanner, formatter);
        return new DateInput(reservationDate, expirationDate);
    }

    private static Date askDate(String s, Scanner scanner, DateFormat formatter) {
        Date date;
        String dateRegex = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
        while (true) {
            System.out.print(s);
            String dateString = scanner.next();
            if (dateString.matches(dateRegex)) {
                try {
                    date = formatter.parse(dateString);
                    break;
                } catch (ParseException e) {
                    System.out.println("Wrong date format. Try again");
                }
            } else {
                System.out.println("Wrong date format. Try again");
            }
        }
        return date;
    }

    public SearchParam askSearch() {
        try (Scanner scanner = new Scanner(System.in)) {
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

    public record SearchParam(String column, String like) {

    }
}