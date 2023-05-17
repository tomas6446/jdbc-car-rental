package com.jdbc.carrental.model;

import com.jdbc.carrental.printer.PrintableTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Tomas Kozakas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation implements PrintableTable {
    private Integer reservationId;
    private String carModel;
    private String customerEmail;
    private Date reservationDate;
    private Date expirationDate;

    @Override
    public String header() {
        return String.format("| %-16s | %-10s | %-30s | %-16s | %-16s |",
                "reservation_id", "car_model", "customer_email", "reservation_date", "expiration_date");
    }

    @Override
    public String toTableRow() {
        return String.format("| %-16d | %-10s | %-30s | %-16s | %-16s |",
                reservationId, carModel, customerEmail, reservationDate, expirationDate);
    }
}
