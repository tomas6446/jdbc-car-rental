package com.jdbc.carrental.repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public interface Repository<T> {
    List<T> getAll() throws SQLException;

    List<T> getAll(int currentUserId) throws SQLException;

    List<T> search(BaseRepository.SearchParam searchParam) throws SQLException;

    void enter(T t) throws SQLException;

    void update(int id, T t) throws SQLException;

    void delete(int id) throws SQLException;

    T askInsert(int currentUserId);
}
