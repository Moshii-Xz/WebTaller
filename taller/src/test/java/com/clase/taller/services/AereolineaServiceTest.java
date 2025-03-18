package com.clase.taller.services;

import com.clase.taller.entities.Aereolinea;
import com.clase.taller.repositories.AereolineaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AereolineaServiceTest {

    @Mock
    private AereolineaRepository aereolineaRepository;

    @InjectMocks
    private AereolineaService aerolineaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAereolinea() {
        Aereolinea aereolinea = new Aereolinea();
        aereolinea.setNombre("Avianca");

        when(aereolineaRepository.save(aereolinea)).thenReturn(aereolinea);

        Aereolinea savedAereolinea = aerolineaService.saveAereolinea(aereolinea);

        assertNotNull(savedAereolinea);
        assertEquals("Avianca", savedAereolinea.getNombre());
        verify(aereolineaRepository, times(1)).save(aereolinea);
    }

    @Test
    void getAllAereolineas() {
        Aereolinea aereolinea1 = new Aereolinea();
        aereolinea1.setNombre("Avianca");

        Aereolinea aereolinea2 = new Aereolinea();
        aereolinea2.setNombre("Latam");

        when(aereolineaRepository.findAll()).thenReturn(Arrays.asList(aereolinea1, aereolinea2));

        List<Aereolinea> aereolineas = aerolineaService.getAllAereolineas();

        assertNotNull(aereolineas);
        assertEquals(2, aereolineas.size());
        verify(aereolineaRepository, times(1)).findAll();
    }

    @Test
    void getAereolineaById() {
        Aereolinea aereolinea = new Aereolinea();
        aereolinea.setAereolineaID(1L);
        aereolinea.setNombre("Avianca");

        when(aereolineaRepository.findById(1L)).thenReturn(Optional.of(aereolinea));

        Optional<Aereolinea> foundAereolinea = aerolineaService.getAereolineaById(1L);

        assertTrue(foundAereolinea.isPresent());
        assertEquals("Avianca", foundAereolinea.get().getNombre());
        verify(aereolineaRepository, times(1)).findById(1L);
    }

    @Test
    void updateAereolinea() {
        Aereolinea existingAereolinea = new Aereolinea();
        existingAereolinea.setAereolineaID(1L);
        existingAereolinea.setNombre("Avianca");

        Aereolinea updatedAereolinea = new Aereolinea();
        updatedAereolinea.setNombre("Latam");

        when(aereolineaRepository.findById(1L)).thenReturn(Optional.of(existingAereolinea));
        when(aereolineaRepository.save(existingAereolinea)).thenReturn(existingAereolinea);

        Aereolinea result = aerolineaService.updateAereolinea(1L, updatedAereolinea);

        assertNotNull(result);
        assertEquals("Latam", result.getNombre());
        verify(aereolineaRepository, times(1)).findById(1L);
        verify(aereolineaRepository, times(1)).save(existingAereolinea);
    }

    @Test
    void deleteAereolinea() {
        doNothing().when(aereolineaRepository).deleteById(1L);

        aerolineaService.deleteAereolinea(1L);

        verify(aereolineaRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByNombre() {
        Aereolinea aereolinea = new Aereolinea();
        aereolinea.setNombre("Avianca");

        when(aereolineaRepository.findByNombre("Avianca")).thenReturn(Arrays.asList(aereolinea));

        List<Aereolinea> aereolineas = aerolineaService.findByNombre("Avianca");

        assertNotNull(aereolineas);
        assertEquals(1, aereolineas.size());
        assertEquals("Avianca", aereolineas.get(0).getNombre());
        verify(aereolineaRepository, times(1)).findByNombre("Avianca");
    }

    @Test
    void findByAereolineaID() {
        Aereolinea aereolinea = new Aereolinea();
        aereolinea.setAereolineaID(1L);

        when(aereolineaRepository.findByAereolineaID(1L)).thenReturn(aereolinea);

        Aereolinea foundAereolinea = aerolineaService.findByAereolineaID(1L);

        assertNotNull(foundAereolinea);
        assertEquals(1L, foundAereolinea.getAereolineaID());
        verify(aereolineaRepository, times(1)).findByAereolineaID(1L);
    }

    @Test
    void findByNombreContaining() {
        Aereolinea aereolinea = new Aereolinea();
        aereolinea.setNombre("Avianca");

        when(aereolineaRepository.findByNombreContaining("via")).thenReturn(Arrays.asList(aereolinea));

        List<Aereolinea> aereolineas = aerolineaService.findByNombreContaining("via");

        assertNotNull(aereolineas);
        assertEquals(1, aereolineas.size());
        assertTrue(aereolineas.get(0).getNombre().contains("via"));
        verify(aereolineaRepository, times(1)).findByNombreContaining("via");
    }

    @Test
    void findByNombreLike() {
        Aereolinea aereolinea = new Aereolinea();
        aereolinea.setNombre("Avianca");

        when(aereolineaRepository.findByNombreLike("%via%")).thenReturn(Arrays.asList(aereolinea));

        List<Aereolinea> aereolineas = aerolineaService.findByNombreLike("%via%");

        assertNotNull(aereolineas);
        assertEquals(1, aereolineas.size());
        assertTrue(aereolineas.get(0).getNombre().contains("via"));
        verify(aereolineaRepository, times(1)).findByNombreLike("%via%");
    }

    @Test
    void findByAereolineaIDIn() {
        Aereolinea aereolinea1 = new Aereolinea();
        aereolinea1.setAereolineaID(1L);

        Aereolinea aereolinea2 = new Aereolinea();
        aereolinea2.setAereolineaID(2L);

        when(aereolineaRepository.findByAereolineaIDIn(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(aereolinea1, aereolinea2));

        List<Aereolinea> aereolineas = aerolineaService.findByAereolineaIDIn(Arrays.asList(1L, 2L));

        assertNotNull(aereolineas);
        assertEquals(2, aereolineas.size());
        verify(aereolineaRepository, times(1)).findByAereolineaIDIn(Arrays.asList(1L, 2L));
    }

    @Test
    void buscarPorNombreExacto() {
        Aereolinea aereolinea = new Aereolinea();
        aereolinea.setNombre("Avianca");

        when(aereolineaRepository.buscarPorNombreExacto("Avianca")).thenReturn(aereolinea);

        Aereolinea foundAereolinea = aerolineaService.buscarPorNombreExacto("Avianca");

        assertNotNull(foundAereolinea);
        assertEquals("Avianca", foundAereolinea.getNombre());
        verify(aereolineaRepository, times(1)).buscarPorNombreExacto("Avianca");
    }

    @Test
    void buscarPorAereolineaID() {
        Aereolinea aereolinea = new Aereolinea();
        aereolinea.setAereolineaID(1L);

        when(aereolineaRepository.buscarPorAereolineaID(1L)).thenReturn(aereolinea);

        Aereolinea foundAereolinea = aerolineaService.buscarPorAereolineaID(1L);

        assertNotNull(foundAereolinea);
        assertEquals(1L, foundAereolinea.getAereolineaID());
        verify(aereolineaRepository, times(1)).buscarPorAereolineaID(1L);
    }

    @Test
    void buscarPorAereolineaIDsEnLista() {
        Aereolinea aereolinea1 = new Aereolinea();
        aereolinea1.setAereolineaID(1L);

        Aereolinea aereolinea2 = new Aereolinea();
        aereolinea2.setAereolineaID(2L);

        when(aereolineaRepository.buscarPorAereolineaIDsEnLista(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(aereolinea1, aereolinea2));

        List<Aereolinea> aereolineas = aerolineaService.buscarPorAereolineaIDsEnLista(Arrays.asList(1L, 2L));

        assertNotNull(aereolineas);
        assertEquals(2, aereolineas.size());
        verify(aereolineaRepository, times(1)).buscarPorAereolineaIDsEnLista(Arrays.asList(1L, 2L));
    }

    @Test
    void buscarPorNombreNoLike() {
        Aereolinea aereolinea1 = new Aereolinea();
        aereolinea1.setNombre("Avianca");

        Aereolinea aereolinea2 = new Aereolinea();
        aereolinea2.setNombre("Latam");

        when(aereolineaRepository.buscarPorNombreNoLike("%via%")).thenReturn(Arrays.asList(aereolinea2));

        List<Aereolinea> aereolineas = aerolineaService.buscarPorNombreNoLike("%via%");

        assertNotNull(aereolineas);
        assertEquals(1, aereolineas.size());
        assertFalse(aereolineas.get(0).getNombre().contains("via"));
        verify(aereolineaRepository, times(1)).buscarPorNombreNoLike("%via%");
    }
}