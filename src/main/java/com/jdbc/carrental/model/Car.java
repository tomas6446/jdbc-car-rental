package com.jdbc.carrental.model;


import com.jdbc.carrental.printer.PrintableTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tomas Kozakas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car implements PrintableTable {
    private Integer id;
    private String manufacturer;
    private String model;
    private Integer year;
    private Double dailyRate;

    public Car(String manufacturer, String model, Integer year, Double dailyRate) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.dailyRate = dailyRate;
    }

    @Override
    public String header() {
        return String.format("| %-8s | %-12s | %-12s | %-6s | $%-10s |",
                "id", "manufacturer", "model", "year", "daily_rate");
    }


    @Override
    public String toTableRow() {
        return String.format("| %-8d | %-12s | %-12s | %-6d | $%-10.2f |",
                id, manufacturer, model, year, dailyRate);
    }

}
