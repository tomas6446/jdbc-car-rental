package com.jdbc.carrental.repository;

import java.util.List;

/**
 * @author Tomas Kozakas
 */
public interface Repository<T> {
    List<T> getAll();
    void search() ;
    void enter();
    void update();
    void delete();
}
