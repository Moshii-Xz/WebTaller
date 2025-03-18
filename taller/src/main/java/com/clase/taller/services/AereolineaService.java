package com.clase.taller.services;

import com.clase.taller.entities.Aereolinea;
import com.clase.taller.repositories.AereolineaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AereolineaService {

    private final AereolineaRepository aereolineaRepository;

    @Autowired
    public AereolineaService(AereolineaRepository aereolineaRepository) {
        this.aereolineaRepository = aereolineaRepository;
    }


    public Aereolinea saveAereolinea(Aereolinea aereolinea) {
        return aereolineaRepository.save(aereolinea);
    }


    public List<Aereolinea> getAllAereolineas() {
        return aereolineaRepository.findAll();
    }


    public Optional<Aereolinea> getAereolineaById(Long id) {
        return aereolineaRepository.findById(id);
    }


    public Aereolinea updateAereolinea(Long id, Aereolinea aereolineaDetails) {
        Optional<Aereolinea> optionalAereolinea = aereolineaRepository.findById(id);
        if (optionalAereolinea.isPresent()) {
            Aereolinea aereolinea = optionalAereolinea.get();
            aereolinea.setNombre(aereolineaDetails.getNombre());
            aereolinea.setAereolineaID(aereolineaDetails.getAereolineaID());
            return aereolineaRepository.save(aereolinea);
        } else {
            throw new RuntimeException("Aerolínea no encontrada con ID: " + id);
        }
    }

    public void deleteAereolinea(Long id) {
        aereolineaRepository.deleteById(id);
    }


    public List<Aereolinea> findByNombre(String nombre) {
        return aereolineaRepository.findByNombre(nombre);
    }


    public Aereolinea findByAereolineaID(Long aereolineaID) {
        return aereolineaRepository.findByAereolineaID(aereolineaID);
    }


    public List<Aereolinea> findByNombreContaining(String nombre) {
        return aereolineaRepository.findByNombreContaining(nombre);
    }


    public List<Aereolinea> findByNombreLike(String nombre) {
        return aereolineaRepository.findByNombreLike(nombre);
    }


    public List<Aereolinea> findByAereolineaIDIn(List<Long> aereolineaIDs) {
        return aereolineaRepository.findByAereolineaIDIn(aereolineaIDs);
    }


    public Aereolinea buscarPorNombreExacto(String nombre) {
        return aereolineaRepository.buscarPorNombreExacto(nombre);
    }


    public Aereolinea buscarPorAereolineaID(Long idAereolinea) {
        return aereolineaRepository.buscarPorAereolineaID(idAereolinea);
    }


    public List<Aereolinea> buscarPorAereolineaIDsEnLista(List<Long> idAereolineas) {
        return aereolineaRepository.buscarPorAereolineaIDsEnLista(idAereolineas);
    }

    public List<Aereolinea> buscarPorNombreNoLike(String nombre) {
        return aereolineaRepository.buscarPorNombreNoLike(nombre);
    }
}