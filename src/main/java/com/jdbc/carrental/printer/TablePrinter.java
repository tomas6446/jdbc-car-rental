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
            System.out.println(items.get(0).header());
            for (T item : items) {
                System.out.println(item.toTableRow());
            }
        }
    }
}
