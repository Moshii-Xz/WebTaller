package com.clase.taller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clase.taller.entities.Vuelo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    Vuelo findByNumeroVuelo(UUID numeroVuelo);
    List<Vuelo> findByOrigen(String origen);
    List<Vuelo> findByDestino(String destino);
    List<Vuelo> findByOrigenAndDestino(String origen, String destino);
    List<Vuelo> findByReservasIsNotNull();

    @Query("SELECT v FROM Vuelo v WHERE v.numeroVuelo = ?1")
    Vuelo buscarPorNumeroVuelo(UUID numeroVuelo);
    @Query("SELECT v FROM Vuelo v WHERE v.origen = ?1")
    List<Vuelo> buscarPorOrigen(String origen);
    @Query("SELECT v FROM Vuelo v WHERE v.destino = ?1")
    List<Vuelo> buscarPorDestino(String destino);
    @Query("SELECT v FROM Vuelo v WHERE v.origen = ?1 AND v.destino = ?2")
    List<Vuelo> buscarPorOrigenYDestino(String origen, String destino);
    @Query("SELECT v FROM Vuelo v WHERE v.reservas IS NOT EMPTY")
    List<Vuelo> buscarConReservas();
}
