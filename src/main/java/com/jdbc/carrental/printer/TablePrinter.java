package com.jdbc.carrental.printer;

/**
 * @author Tomas Kozakas
 */

import java.util.List;

public class TablePrinter {
    public static <T extends PrintableTable> void printTable(String title, List<T> items) {
        System.out.println(title);
        if (items.isEmpty()) {
            System.out.println("**Is empty**\n");
        } else {
            for (T item : items) {
                System.out.println(item.toTableRow());
            }
            System.out.println("\n");
        }

    }
}
