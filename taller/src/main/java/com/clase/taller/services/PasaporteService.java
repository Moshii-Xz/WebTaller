package com.clase.taller.services;

import com.clase.taller.entities.Pasaporte;
import com.clase.taller.repositories.PasaporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PasaporteService {

    private final PasaporteRepository pasaporteRepository;

    @Autowired
    public PasaporteService(PasaporteRepository pasaporteRepository) {
        this.pasaporteRepository = pasaporteRepository;
    }

    public Pasaporte savePasaporte(Pasaporte pasaporte) {
        return pasaporteRepository.save(pasaporte);
    }

    public List<Pasaporte> getAllPasaportes() {
        return pasaporteRepository.findAll();
    }

    public Optional<Pasaporte> getPasaporteById(Long id) {
        return pasaporteRepository.findById(id);
    }

    public Pasaporte updatePasaporte(Long id, Pasaporte pasaporteDetails) {
        Optional<Pasaporte> optionalPasaporte = pasaporteRepository.findById(id);
        if (optionalPasaporte.isPresent()) {
            Pasaporte pasaporte = optionalPasaporte.get();
            pasaporte.setNumero(pasaporteDetails.getNumero());
            return pasaporteRepository.save(pasaporte);
        } else {
            throw new RuntimeException("Pasaporte no encontrado con ID: " + id);
        }
    }

    public void deletePasaporte(Long id) {
        pasaporteRepository.deleteById(id);
    }

    public List<Pasaporte> findByNumero(String numero) {
        return pasaporteRepository.findByNumero(numero);
    }

    public Pasaporte findByNumeroContaining(String numero) {
        return pasaporteRepository.findByNumeroContaining(numero);
    }

    public Pasaporte findByNumeroLike(String numero) {
        return pasaporteRepository.findByNumeroLike(numero);
    }

    public Pasaporte buscarPorNumeroExacto(String numero) {
        return pasaporteRepository.buscarPorNumeroExacto(numero);
    }

    public Pasaporte buscarPorId(Long id) {
        return pasaporteRepository.buscarPorId(id);
    }

    public List<Pasaporte> buscarPorIdsEnLista(List<Long> ids) {
        return pasaporteRepository.buscarPorIdsEnLista(ids);
    }

    public List<Pasaporte> buscarPorNumeroNoLike(String numero) {
        return pasaporteRepository.buscarPorNumeroNoLike(numero);
    }

    public Pasaporte findByPasajeroId(Long pasajeroId) {
        return pasaporteRepository.findByPasajeroId(pasajeroId);
    }

    public List<Pasaporte> findByNumeroStartingWith(String prefijo) {
        return pasaporteRepository.findByNumeroStartingWith(prefijo);
    }

    public List<Pasaporte> findByNumeroEndingWith(String sufijo) {
        return pasaporteRepository.findByNumeroEndingWith(sufijo);
    }

    public List<Pasaporte> findByNumeroContainingSubstring(String subcadena) {
        return pasaporteRepository.findByNumeroContainingSubstring(subcadena);
    }

    public Pasaporte findByPasajeroIdAndNumero(Long pasajeroId, String numero) {
        return pasaporteRepository.findByPasajeroIdAndNumero(pasajeroId, numero);
    }
}