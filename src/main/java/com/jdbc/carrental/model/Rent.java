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
    private Integer rentId;
    private Integer carId;
    private Integer customerId;
    private Date rentDate;
    private Date returnDate;
    private Double amountPaid;

    public Rent(Integer carId, Integer customerId, Date rentDate, Date returnDate) {
        this.carId = carId;
        this.customerId = customerId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }

    @Override
    public String header() {
        return String.format("| %-8s | %-6s | %-12s | %-12s | %-12s | $%-10s |",
                "rent_id", "car_id", "customer_id", "rent_date", "return_date", "amount_paid");
    }

    @Override
    public String toTableRow() {
        return String.format("| %-8d | %-6d | %-12d | %-12s | %-12s | $%-10.2f |",
                rentId, carId, customerId, rentDate, returnDate, amountPaid);
    }

}
