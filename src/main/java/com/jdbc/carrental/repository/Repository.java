package com.jdbc.carrental.repository;

import com.jdbc.carrental.model.Reservation;

import java.util.List;

/**
 * @author Tomas Kozakas
 */
public interface Repository<T> {
    List<T> getAll();
    void search() ;
    void enter(T t);
    void update(int id, T t);
    void delete(int id);
}
