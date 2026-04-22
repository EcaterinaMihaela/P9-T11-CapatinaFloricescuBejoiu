package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    default List<T> findAllSafe() {
        return findAll();
    }

    default Optional<T> findByIdSafe(ID id) {
        return findById(id);
    }

    default T saveSafe(T entity) {
        return save(entity);
    }

    default void deleteSafe(ID id) {
        deleteById(id);
    }
}