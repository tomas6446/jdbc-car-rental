package com.jdbc.carrental.model;

import com.jdbc.carrental.printer.PrintableTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Override
    public String toTableRow() {
        return String.format("| %-12d | %-20s | %-25s | %-12s |",
                customerId, name, email, phone);
    }
}
