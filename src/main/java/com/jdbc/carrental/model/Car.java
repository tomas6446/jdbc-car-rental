package com.jdbc.carrental.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tomas Kozakas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private Integer carId;
    private String manufacturer;
    private String model;
    private Integer year;
    private Double dailyRate;
}
