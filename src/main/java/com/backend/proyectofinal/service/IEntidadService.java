package com.backend.proyectofinal.service;

import java.util.List;
import java.util.Optional;

public interface IEntidadService<T> {

    /* Métodos */
    T save(T t);
    Optional<T> findById(Long id);
    List<T> findAll();
    T update(T t);
    void delete(Long id);
}

