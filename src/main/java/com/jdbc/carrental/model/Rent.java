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
public class Rent implements PrintableTable {
    private Integer id;
    private Integer carId;
    private Integer customerId;
    private String carModel;
    private Date rentDate;
    private Date returnDate;
    private Double amountPaid;

    public Rent(int carId, int currentUserId, Date rentDate, Date returnDate) {
        this.carId = carId;
        this.customerId = currentUserId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }

    @Override
    public String header() {
        return String.format("| %-8s | %-9s | %-12s | %-12s | $%-10s |",
                "id", "model" , "rent_date", "return_date", "amount_paid");
    }

    @Override
    public String toTableRow() {
        return String.format("| %-8s | %-9s | %-12s | %-12s | $%-11s |",
                id, carModel, rentDate, returnDate, amountPaid);
    }

}
