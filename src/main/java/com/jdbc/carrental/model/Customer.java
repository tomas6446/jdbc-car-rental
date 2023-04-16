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
public class Customer {
    private Integer customer_id;
    private String name;
    private String email;
    private String phone;
}
