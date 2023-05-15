package com.jdbc.carrental.model;

import com.jdbc.carrental.printer.PrintableTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author Tomas Kozakas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements PrintableTable {
    private Integer customerId;
    private String name;
    private String email;
    private String phone;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toTableRow() {
        return String.format("| %-12d | %-20s | %-25s | %-12s |",
                customerId, name, email, phone);
    }
}
