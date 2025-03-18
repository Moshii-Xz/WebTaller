package com.clase.taller.repositories;

import com.clase.taller.entities.Pasajero;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.Arrays;
import java.util.List;

@DataJpaTest
@Testcontainers
@Import(TestcontainersConfiguration.class)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PasajeroRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testPostgres")
            .withUsername("testUsername")
            .withPassword("testPassword");

    static {
        postgreSQLContainer.start();
    }

    @Autowired
    PasajeroRepository pasajeroRepository;

    Pasajero pasajero1;
    Pasajero pasajero2;
    Pasajero pasajero3;

    @BeforeEach
    void setUp() {
        // Crear pasajeros
        pasajero1 = Pasajero.builder().nombre("Juan Perez").build();
        pasajero2 = Pasajero.builder().nombre("Maria Lopez").build();
        pasajero3 = Pasajero.builder().nombre("Carlos Sanchez").build();

        pasajeroRepository.saveAll(Arrays.asList(pasajero1, pasajero2, pasajero3));
    }

    @AfterEach
    void tearDown() {
        pasajeroRepository.deleteAll();
    }

    @Test
    @Order(1)
    void findByNombre() {
        List<Pasajero> pasajeros = pasajeroRepository.findByNombre("Juan Perez");
        Assertions.assertNotNull(pasajeros);
        Assertions.assertEquals(1, pasajeros.size());
        Assertions.assertEquals("Juan Perez", pasajeros.get(0).getNombre());
    }

    @Test
    @Order(2)
    void findByNombreContaining() {
        Pasajero pasajero = pasajeroRepository.findByNombreContaining("Juan");
        Assertions.assertNotNull(pasajero);
        Assertions.assertTrue(pasajero.getNombre().contains("Juan"));
    }

    @Test
    @Order(3)
    void findByNombreLike() {
        Pasajero pasajero = pasajeroRepository.findByNombreLike("%Juan%");
        Assertions.assertNotNull(pasajero);
        Assertions.assertTrue(pasajero.getNombre().contains("Juan"));
    }

    @Test
    @Order(4)
    void findByIdIn() {
        Pasajero pasajero = pasajeroRepository.findByIdIn(Arrays.asList(pasajero1.getId(), pasajero2.getId()));
        Assertions.assertNotNull(pasajero);
        Assertions.assertTrue(pasajero.getId().equals(pasajero1.getId()) || pasajero.getId().equals(pasajero2.getId()));
    }

    @Test
    @Order(5)
    void findByNombreExact() {
        Pasajero pasajero = pasajeroRepository.findByNombreExact("Juan Perez");
        Assertions.assertNotNull(pasajero);
        Assertions.assertEquals("Juan Perez", pasajero.getNombre());
    }

    @Test
    @Order(6)
    void findPasajeroById() {
        Pasajero pasajero = pasajeroRepository.findPasajeroById(pasajero1.getId());
        Assertions.assertNotNull(pasajero);
        Assertions.assertEquals(pasajero1.getId(), pasajero.getId());
    }

    @Test
    @Order(7)
    void findPasajerosByIds() {
        List<Pasajero> pasajeros = pasajeroRepository.findPasajerosByIds(Arrays.asList(pasajero1.getId(), pasajero2.getId()));
        Assertions.assertNotNull(pasajeros);
        Assertions.assertEquals(2, pasajeros.size());
    }

    @Test
    @Order(8)
    void findByNombreLikeQuery() {
        List<Pasajero> pasajeros = pasajeroRepository.findByNombreLikeQuery("%Juan%");
        Assertions.assertNotNull(pasajeros);
        Assertions.assertEquals(1, pasajeros.size());
        Assertions.assertTrue(pasajeros.get(0).getNombre().contains("Juan"));
    }

    @Test
    @Order(9)
    void findByNombreNotLike() {
        List<Pasajero> pasajeros = pasajeroRepository.findByNombreNotLike("%Juan%");
        Assertions.assertNotNull(pasajeros);
        Assertions.assertEquals(2, pasajeros.size()); // Solo Juan Perez contiene "Juan"
    }
}