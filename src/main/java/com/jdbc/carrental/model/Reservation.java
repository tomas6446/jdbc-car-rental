package com.jdbc.carrental.model;

import com.jdbc.carrental.printer.PrintableTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Tomas Kozakas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation implements PrintableTable {
    private Integer reservationId;
    private Integer carId;
    private Integer customerId;
    private Date reservationDate;
    private Date expirationDate;

    @Override
    public String toTableRow() {
        return String.format("| %-16d | %-6d | %-12d | %-12s | %-12s |",
                reservationId, carId, customerId, reservationDate, expirationDate);
    }
}
