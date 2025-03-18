package com.clase.taller.services;

import com.clase.taller.entities.Reserva;
import com.clase.taller.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva saveReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> getReservaById(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva updateReserva(Long id, Reserva reservaDetails) {
        Optional<Reserva> optionalReserva = reservaRepository.findById(id);
        if (optionalReserva.isPresent()) {
            Reserva reserva = optionalReserva.get();
            reserva.setCodigoReserva(reservaDetails.getCodigoReserva());
            reserva.setPasajero(reservaDetails.getPasajero());
            reserva.setVuelo(reservaDetails.getVuelo());
            return reservaRepository.save(reserva);
        } else {
            throw new RuntimeException("Reserva no encontrada con ID: " + id);
        }
    }

    public void deleteReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    public List<Reserva> findByCodigoReserva(UUID codigoReserva) {
        return reservaRepository.findByCodigoReserva(codigoReserva);
    }

    public List<Reserva> findByPasajero_Id(Long pasajeroId) {
        return reservaRepository.findByPasajero_Id(pasajeroId);
    }

    public List<Reserva> findByVuelo_Id(Long vueloId) {
        return reservaRepository.findByVuelo_Id(vueloId);
    }

    public Reserva findByIdReserva(Long idReserva) {
        return reservaRepository.findByIdReserva(idReserva);
    }

    public List<Reserva> findByIdReservaEnLista(List<Long> ids) {
        return reservaRepository.findByIdReservaEnLista(ids);
    }

    public List<Reserva> findByIdReservaNotIn(List<Long> ids) {
        return reservaRepository.findByIdReservaNotIn(ids);
    }

    public List<Reserva> findReservaByCodigoReserva(UUID codigoReserva) {
        return reservaRepository.findReservaByCodigoReserva(codigoReserva);
    }

    public List<Reserva> findReservaByNombrePasajero(String nombre) {
        return reservaRepository.findReservaByNombrePasajero(nombre);
    }

    public List<Reserva> findByNombrePasajeroAndCodigoReserva(String nombre, UUID codigoReserva) {
        return reservaRepository.findByNombrePasajeroAndCodigoReserva(nombre, codigoReserva);
    }

    public List<Reserva> findByVueloIdAndNombrePasajero(Long vueloId, String nombre) {
        return reservaRepository.findByVueloIdAndNombrePasajero(vueloId, nombre);
    }
}