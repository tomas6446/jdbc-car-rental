package com.jdbc.carrental.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tomas Kozakas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car implements PrintableTable{
    private Integer carId;
    private String manufacturer;
    private String model;
    private Integer year;
    private Double dailyRate;

    @Override
    public String toTableRow() {
        return String.format("| %-8d | %-12s | %-12s | %-6d | $%-10.2f |",
                carId, manufacturer, model, year, dailyRate);
    }
}
