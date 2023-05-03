package com.jdbc.carrental.printer;

/**
 * @author Tomas Kozakas
 */

import java.util.List;

public class TablePrinter {
    public static <T extends PrintableTable> void printTable(String title, List<T> items) {
        System.out.println(title);
        for (T item : items) {
            System.out.println(item.toTableRow());
        }
    }
}
