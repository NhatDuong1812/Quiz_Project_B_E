package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGeneralService<T> {
    Optional<T> findById(Long id);
    Page<T> findAll(Pageable pageable);
    Iterable<T> findAll();
    void remove(Long id);
    T save(T t);
}
