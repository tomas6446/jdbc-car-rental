package com.jdbc.carrental.model;

import com.jdbc.carrental.printer.PrintableTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarReservation implements PrintableTable {
    private Integer customerId;
    private Integer reservationId;
    private Integer carId;
    private String model;
    private Date reservationDate;
    private Date expirationDate;

    @Override
    public String header() {
        return String.format("| %-12s  | %-12s | %-12s | %-16s | %-16s |",
                "reservation_id", "car_id", "model", "reservation_date", "expiration_date");
    }


    @Override
    public String toTableRow() {
        return String.format("| %-15d | %-12d | %-12s | %-16s | %-16s |",
                reservationId, carId, model, reservationDate, expirationDate);
    }
}
