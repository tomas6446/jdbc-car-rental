package com.jdbc.carrental.repository;

import com.jdbc.carrental.connection.DatabaseConnection;
import com.jdbc.carrental.mapper.CarMapper;
import com.jdbc.carrental.model.Car;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class CarRepository extends BaseRepository<Car> {
    private final CarMapper carMapper;

    public CarRepository(DatabaseConnection databaseConnection, CarMapper carMapper) {
        super(databaseConnection);
        this.carMapper = carMapper;
    }

    @Override
    public List<Car> getAll()  {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM car")) {
            return executeQuery(preparedStatement, carMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Car> getAll(int currentUserId) {
        return Collections.emptyList();
    }

    @Override
    public void enter(Car car) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO car (manufacturer, model, year, daily_rate) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, car.getManufacturer());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setDouble(4, car.getDailyRate());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }

    @Override
    public void update(int id, Car car) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE car SET manufacturer = ?, model = ?, year = ?, daily_rate = ? WHERE car_id = ?")) {
            preparedStatement.setString(1, car.getManufacturer());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setDouble(4, car.getDailyRate());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM car WHERE car_id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        }
    }


    @Override
    public List<Car> search(SearchParam searchParam) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM reservation WHERE " + searchParam.column() + " LIKE ?")) {
            preparedStatement.setString(1, "%" + searchParam.like() + "%");
            return executeQuery(preparedStatement, carMapper::map);
        } catch (SQLException e) {
            rollbackTransaction();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Car> askInsert(int currentUserId, Scanner scanner) {
        System.out.print("Manufacturer: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        System.out.print("Daily rate: ");
        double daily_rate = scanner.nextDouble();
        return Optional.of(new Car(manufacturer, model, year, daily_rate));
    }
}
