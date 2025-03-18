package com.clase.taller.repositories;

import com.clase.taller.entities.Pasaporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasaporteRepository extends JpaRepository<Pasaporte, Long> {

    // Métodos basados en los campos de la entidad Pasaporte
    List<Pasaporte> findByNumero(String numero); // Buscar por el campo 'numero'
    Pasaporte findByNumeroContaining(String numero); // Buscar por coincidencia parcial de 'numero'
    Pasaporte findByNumeroLike(String numero); // Buscar usando el patrón SQL LIKE

    // Consultas personalizadas usando @Query
    @Query("SELECT p FROM Pasaporte p WHERE p.numero = ?1")
    Pasaporte buscarPorNumeroExacto(String numero);

    @Query("SELECT p FROM Pasaporte p WHERE p.id = ?1")
    Pasaporte buscarPorId(Long id);

    @Query("SELECT p FROM Pasaporte p WHERE p.id IN ?1")
    List<Pasaporte> buscarPorIdsEnLista(List<Long> ids);

    @Query("SELECT p FROM Pasaporte p WHERE p.numero NOT LIKE ?1")
    List<Pasaporte> buscarPorNumeroNoLike(String numero);

    @Query("SELECT p FROM Pasaporte p WHERE p.pasajero.id = :pasajeroId")
    Pasaporte findByPasajeroId(@Param("pasajeroId") Long pasajeroId);

    @Query("SELECT p FROM Pasaporte p WHERE p.numero LIKE CONCAT(:prefijo, '%')")
    List<Pasaporte> findByNumeroStartingWith(@Param("prefijo") String prefijo);

    @Query("SELECT p FROM Pasaporte p WHERE p.numero LIKE CONCAT('%', :sufijo)")
    List<Pasaporte> findByNumeroEndingWith(@Param("sufijo") String sufijo);

    @Query("SELECT p FROM Pasaporte p WHERE p.numero LIKE CONCAT('%', :subcadena, '%')")
    List<Pasaporte> findByNumeroContainingSubstring(@Param("subcadena") String subcadena);

    @Query("SELECT p FROM Pasaporte p WHERE p.pasajero.id = :pasajeroId AND p.numero = :numero")
    Pasaporte findByPasajeroIdAndNumero(@Param("pasajeroId") Long pasajeroId, @Param("numero") String numero);
}