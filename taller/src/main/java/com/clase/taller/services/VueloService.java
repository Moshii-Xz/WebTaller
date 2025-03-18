package com.clase.taller.services;

import com.clase.taller.entities.Vuelo;
import com.clase.taller.repositories.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VueloService {

    private final VueloRepository vueloRepository;

    @Autowired
    public VueloService(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    public Vuelo saveVuelo(Vuelo vuelo) {
        return vueloRepository.save(vuelo);
    }

    public List<Vuelo> getAllVuelos() {
        return vueloRepository.findAll();
    }

    public Optional<Vuelo> getVueloById(Long id) {
        return vueloRepository.findById(id);
    }

    public Vuelo updateVuelo(Long id, Vuelo vueloDetails) {
        Optional<Vuelo> optionalVuelo = vueloRepository.findById(id);
        if (optionalVuelo.isPresent()) {
            Vuelo vuelo = optionalVuelo.get();
            vuelo.setOrigen(vueloDetails.getOrigen());
            vuelo.setDestino(vueloDetails.getDestino());
            vuelo.setNumeroVuelo(vueloDetails.getNumeroVuelo());
            vuelo.setAereolineas(vueloDetails.getAereolineas());
            return vueloRepository.save(vuelo);
        } else {
            throw new RuntimeException("Vuelo no encontrado con ID: " + id);
        }
    }

    public void deleteVuelo(Long id) {
        vueloRepository.deleteById(id);
    }

    public Vuelo findByNumeroVuelo(UUID numeroVuelo) {
        return vueloRepository.findByNumeroVuelo(numeroVuelo);
    }

    public List<Vuelo> findByOrigen(String origen) {
        return vueloRepository.findByOrigen(origen);
    }

    public List<Vuelo> findByDestino(String destino) {
        return vueloRepository.findByDestino(destino);
    }

    public List<Vuelo> findByOrigenAndDestino(String origen, String destino) {
        return vueloRepository.findByOrigenAndDestino(origen, destino);
    }

    public List<Vuelo> findByReservasIsNotNull() {
        return vueloRepository.findByReservasIsNotNull();
    }

    public Vuelo buscarPorNumeroVuelo(UUID numeroVuelo) {
        return vueloRepository.buscarPorNumeroVuelo(numeroVuelo);
    }

    public List<Vuelo> buscarPorOrigen(String origen) {
        return vueloRepository.buscarPorOrigen(origen);
    }

    public List<Vuelo> buscarPorDestino(String destino) {
        return vueloRepository.buscarPorDestino(destino);
    }

    public List<Vuelo> buscarPorOrigenYDestino(String origen, String destino) {
        return vueloRepository.buscarPorOrigenYDestino(origen, destino);
    }

    public List<Vuelo> buscarConReservas() {
        return vueloRepository.buscarConReservas();
    }
}