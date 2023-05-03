package com.jdbc.carrental.mapper;

import com.jdbc.carrental.model.CarStats;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tomas Kozakas
 */
public class CarStatsMapper {
    public CarStats mapRow(ResultSet resultSet) {
        try {
            return new CarStats(
                    resultSet.getString("manufacturer"),
                    resultSet.getString("model"),
                    resultSet.getInt("totalRentals")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
