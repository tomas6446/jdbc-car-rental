package com.jdbc.carrental.repository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public interface Repository<T> {
    List<T> getAll();

    List<T> getAll(int currentUserId);

    List<T> search(BaseRepository.SearchParam searchParam);

    void enter(T t);

    void update(int id, T t);

    void delete(int id);

    Optional<T> askInsert(int currentUserId, Scanner scanner);
}
