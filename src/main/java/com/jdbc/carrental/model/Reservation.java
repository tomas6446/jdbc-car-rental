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

    public Reservation(Integer carId, Integer customerId, Date reservationDate, Date expirationDate) {
        this.carId = carId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public String header() {
        return String.format("| %-16s | %-6s | %-12s | %-16s | %-16s |",
                "reservation_id", "car_id", "customer_id", "reservation_date", "expiration_date");
    }

    @Override
    public String toTableRow() {
        return String.format("| %-16d | %-6d | %-12d | %-16s | %-16s |",
                reservationId, carId, customerId, reservationDate, expirationDate);
    }

}
