package com.clase.taller.repositories;

import com.clase.taller.entities.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {
    // Métodos corregidos basados en el campo 'nombre' de la entidad
    List<Pasajero> findByNombre(String nombre); // Buscar por coincidencia exacta de 'nombre'
    Pasajero findByNombreContaining(String nombre); // Buscar por coincidencia parcial de 'nombre'
    Pasajero findByNombreLike(String nombre); // Buscar usando el patrón SQL LIKE

    // Método corregido para buscar por lista de IDs
    Pasajero findByIdIn(List<Long> ids); // Buscar por lista de IDs

    // Consultas personalizadas usando @Query
    @Query("SELECT p FROM Pasajero p WHERE p.nombre = ?1")
    Pasajero findByNombreExact(String nombre);

    @Query("SELECT p FROM Pasajero p WHERE p.id = ?1")
    Pasajero findPasajeroById(Long id);

    @Query("SELECT p FROM Pasajero p WHERE p.id IN ?1")
    List<Pasajero> findPasajerosByIds(List<Long> ids);

    @Query("SELECT p FROM Pasajero p WHERE p.nombre LIKE ?1")
    List<Pasajero> findByNombreLikeQuery(String nombre);

    @Query("SELECT p FROM Pasajero p WHERE p.nombre NOT LIKE ?1")
    List<Pasajero> findByNombreNotLike(String nombre);
}