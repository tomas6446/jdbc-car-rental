package com.jdbc.carrental.printer;

/**
 * @author Tomas Kozakas
 */

import java.util.List;

public class TablePrinter {
    public static <T extends PrintableTable> void printTable(List<T> items) {
        for (T item : items) {
            System.out.println(item.toTableRow());
        }
    }
    private static String createRowFormat(String[] headers) {
        StringBuilder rowFormat = new StringBuilder("|");
        for (String header : headers) {
            rowFormat.append(" %-").append(header.length()).append("s |");
        }
        rowFormat.append("%n");
        return rowFormat.toString();
    }
}
