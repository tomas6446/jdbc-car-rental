package com.jdbc.carrental.repository;

import java.util.List;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public interface Repository<T> {
    List<T> getAll();
    void search() ;
    void enter(T t);
    void update(int id, T t);
    void delete(int id);
    T ask(Scanner scanner);
}
