package com.example.taller.repositories;

import com.example.taller.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface reservaRepository extends JpaRepository<Reserva, Long> {

    // Métodos basados en los campos de la entidad Reserva
    List<Reserva> findByCodigoReserva(UUID codigoReserva); // Buscar por el campo 'codigoReserva'
    List<Reserva> findByPasajero_Id(Long pasajeroId); // Buscar por el ID del pasajero

    // Consultas personalizadas usando @Query
    @Query("SELECT r FROM Reserva r WHERE r.id = ?1")
    Reserva findByIdReserva(Long idReserva);

    @Query("SELECT r FROM Reserva r WHERE r.id IN ?1")
    List<Reserva> findByIdReservaEnLista(List<Long> ids);

    @Query("SELECT r FROM Reserva r WHERE r.id NOT IN ?1")
    List<Reserva> findByIdReservaNotIn(List<Long> ids);

    @Query("SELECT r FROM Reserva r WHERE r.codigoReserva = :codigoReserva")
    List<Reserva> findReservaByCodigoReserva(@Param("codigoReserva") UUID codigoReserva);

    @Query("SELECT r FROM Reserva r WHERE r.pasajero.nombre = :nombre")
    List<Reserva> findReservaByNombrePasajero(@Param("nombre") String nombre);
}