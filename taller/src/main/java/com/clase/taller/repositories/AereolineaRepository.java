package com.clase.taller.repositories;

import com.clase.taller.entities.Aereolinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AereolineaRepository extends JpaRepository<Aereolinea, Long> {

    // Métodos corregidos basados en los campos de la entidad
    List<Aereolinea> findByNombre(String nombre); // Buscar por el campo 'nombre'
    Aereolinea findByAereolineaID(Long aereolineaID); // Buscar por el campo 'aereolineaID'
    List<Aereolinea> findByNombreContaining(String nombre); // Buscar por coincidencia parcial de 'nombre'
    List<Aereolinea> findByNombreLike(String nombre); // Buscar usando el patrón SQL LIKE

    // Método para buscar por lista de IDs
    List<Aereolinea> findByAereolineaIDIn(List<Long> aereolineaIDs); // Buscar por lista de 'aereolineaID'

    // Consultas personalizadas usando @Query
    @Query("SELECT a FROM Aereolinea a WHERE a.nombre = ?1")
    Aereolinea buscarPorNombreExacto(String nombre);

    @Query("SELECT a FROM Aereolinea a WHERE a.aereolineaID = ?1")
    Aereolinea buscarPorAereolineaID(Long idAereolinea);

    @Query("SELECT a FROM Aereolinea a WHERE a.aereolineaID IN ?1")
    List<Aereolinea> buscarPorAereolineaIDsEnLista(List<Long> idAereolineas);

    @Query("SELECT a FROM Aereolinea a WHERE a.nombre NOT LIKE ?1")
    List<Aereolinea> buscarPorNombreNoLike(String nombre);
}