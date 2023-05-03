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
public class CarStats implements PrintableTable {
    private String manufacturer;
    private String model;
    private int totalRentals;

    @Override
    public String toTableRow() {
        return String.format("| %-12s | %-12s | %-8d |",
                manufacturer, model, totalRentals);
    }
}
