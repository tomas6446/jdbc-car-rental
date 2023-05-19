package com.jdbc.carrental.model;

import com.jdbc.carrental.printer.PrintableTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


/**
 * @author Tomas Kozakas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation implements PrintableTable {
    private Integer id;
    private Integer customerId;
    private Integer carId;
    private String carModel;
    private Date reservationDate;
    private Date expirationDate;

    public Reservation(int carId, int currentUserId, Date reservationDate, Date expirationDate) {
        this.carId = carId;
        this.customerId = currentUserId;
        this.reservationDate = reservationDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public String header() {
        return String.format("| %-16s | %-12s | %-16s | %-16s |",
                "id", "model", "reservation_date", "expiration_date");
    }

    @Override
    public String toTableRow() {
        return String.format("| %-16d | %-12s | %-16s | %-16s |",
                id, carModel, reservationDate, expirationDate);
    }
}
