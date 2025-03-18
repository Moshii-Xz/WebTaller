package com.clase.taller.services;

import com.clase.taller.entities.Pasajero;
import com.clase.taller.repositories.PasajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PasajeroService {

    private final PasajeroRepository pasajeroRepository;

    @Autowired
    public PasajeroService(PasajeroRepository pasajeroRepository) {
        this.pasajeroRepository = pasajeroRepository;
    }

    public Pasajero savePasajero(Pasajero pasajero) {
        return pasajeroRepository.save(pasajero);
    }

    public List<Pasajero> getAllPasajeros() {
        return pasajeroRepository.findAll();
    }

    public Optional<Pasajero> getPasajeroById(Long id) {
        return pasajeroRepository.findById(id);
    }

    public Pasajero updatePasajero(Long id, Pasajero pasajeroDetails) {
        Optional<Pasajero> optionalPasajero = pasajeroRepository.findById(id);
        if (optionalPasajero.isPresent()) {
            Pasajero pasajero = optionalPasajero.get();
            pasajero.setNombre(pasajeroDetails.getNombre());
            return pasajeroRepository.save(pasajero);
        } else {
            throw new RuntimeException("Pasajero no encontrado con ID: " + id);
        }
    }

    public void deletePasajero(Long id) {
        pasajeroRepository.deleteById(id);
    }

    public List<Pasajero> findByNombre(String nombre) {
        return pasajeroRepository.findByNombre(nombre);
    }

    public Pasajero findByNombreContaining(String nombre) {
        return pasajeroRepository.findByNombreContaining(nombre);
    }

    public Pasajero findByNombreLike(String nombre) {
        return pasajeroRepository.findByNombreLike(nombre);
    }

    public Pasajero findByIdIn(List<Long> ids) {
        return pasajeroRepository.findByIdIn(ids);
    }

    public Pasajero findByNombreExact(String nombre) {
        return pasajeroRepository.findByNombreExact(nombre);
    }

    public Pasajero findPasajeroById(Long id) {
        return pasajeroRepository.findPasajeroById(id);
    }

    public List<Pasajero> findPasajerosByIds(List<Long> ids) {
        return pasajeroRepository.findPasajerosByIds(ids);
    }

    public List<Pasajero> findByNombreLikeQuery(String nombre) {
        return pasajeroRepository.findByNombreLikeQuery(nombre);
    }

    public List<Pasajero> findByNombreNotLike(String nombre) {
        return pasajeroRepository.findByNombreNotLike(nombre);
    }
}