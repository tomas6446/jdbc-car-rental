package com.jdbc.carrental.model;

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
public class Rent {
    private Integer reservationId;
    private Integer carId;
    private Integer customerId;
    private Date rentDate;
    private Date returnDate;
    private Double amountPaid;
}
