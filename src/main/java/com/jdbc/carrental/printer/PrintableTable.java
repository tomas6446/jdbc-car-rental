package com.jdbc.carrental.printer;

/**
 * @author Tomas Kozakas
 */
public interface PrintableTable {
    String header();

    String toTableRow();
}
