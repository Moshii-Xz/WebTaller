package com.clase.taller.repositories;

import com.clase.taller.entities.Pasajero;
import com.clase.taller.entities.Pasaporte;
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
class PasaporteRepositoryTest {

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
    PasaporteRepository pasaporteRepository;

    @Autowired
    PasajeroRepository pasajeroRepository;

    Pasaporte pasaporte1;
    Pasaporte pasaporte2;
    Pasaporte pasaporte3;
    Pasajero pasajero1;
    Pasajero pasajero2;

    @BeforeEach
    void setUp() {
        // Crear pasajeros
        pasajero1 = Pasajero.builder().nombre("Juan Perez").build();
        pasajero2 = Pasajero.builder().nombre("Maria Lopez").build();
        pasajeroRepository.save(pasajero1);
        pasajeroRepository.save(pasajero2);

        // Crear pasaportes
        pasaporte1 = Pasaporte.builder().numero("A123456").pasajero(pasajero1).build();
        pasaporte2 = Pasaporte.builder().numero("B654321").pasajero(pasajero2).build();
        pasaporte3 = Pasaporte.builder().numero("C112233").pasajero(pasajero1).build();

        pasaporteRepository.saveAll(Arrays.asList(pasaporte1, pasaporte2, pasaporte3));
    }

    @AfterEach
    void tearDown() {
        pasaporteRepository.deleteAll();
        pasajeroRepository.deleteAll();
    }

    @Test
    @Order(1)
    void findByNumero() {
        List<Pasaporte> pasaportes = pasaporteRepository.findByNumero("A123456");
        Assertions.assertNotNull(pasaportes);
        Assertions.assertEquals(1, pasaportes.size());
        Assertions.assertEquals("A123456", pasaportes.get(0).getNumero());
    }

    @Test
    @Order(2)
    void findByNumeroContaining() {
        Pasaporte pasaporte = pasaporteRepository.findByNumeroContaining("123");
        Assertions.assertNotNull(pasaporte);
        Assertions.assertTrue(pasaporte.getNumero().contains("123"));
    }

    @Test
    @Order(3)
    void findByNumeroLike() {
        Pasaporte pasaporte = pasaporteRepository.findByNumeroLike("%123%");
        Assertions.assertNotNull(pasaporte);
        Assertions.assertTrue(pasaporte.getNumero().contains("123"));
    }

    @Test
    @Order(4)
    void buscarPorNumeroExacto() {
        Pasaporte pasaporte = pasaporteRepository.buscarPorNumeroExacto("A123456");
        Assertions.assertNotNull(pasaporte);
        Assertions.assertEquals("A123456", pasaporte.getNumero());
    }

    @Test
    @Order(5)
    void buscarPorId() {
        Pasaporte pasaporte = pasaporteRepository.buscarPorId(pasaporte1.getId());
        Assertions.assertNotNull(pasaporte);
        Assertions.assertEquals(pasaporte1.getId(), pasaporte.getId());
    }

    @Test
    @Order(6)
    void buscarPorIdsEnLista() {
        List<Pasaporte> pasaportes = pasaporteRepository.buscarPorIdsEnLista(Arrays.asList(pasaporte1.getId(), pasaporte2.getId()));
        Assertions.assertNotNull(pasaportes);
        Assertions.assertEquals(2, pasaportes.size());
    }

    @Test
    @Order(7)
    void buscarPorNumeroNoLike() {
        List<Pasaporte> pasaportes = pasaporteRepository.buscarPorNumeroNoLike("%123%");
        Assertions.assertNotNull(pasaportes);
        Assertions.assertEquals(2, pasaportes.size()); // Solo pasaporte1 contiene "123"
    }

    @Test
    @Order(8)
    void findByPasajeroId() {
        Pasaporte pasaporte = pasaporteRepository.findByPasajeroId(pasajero1.getId());
        Assertions.assertNotNull(pasaporte);
        Assertions.assertEquals(pasajero1.getId(), pasaporte.getPasajero().getId());
    }

    @Test
    @Order(9)
    void findByNumeroStartingWith() {
        List<Pasaporte> pasaportes = pasaporteRepository.findByNumeroStartingWith("A");
        Assertions.assertNotNull(pasaportes);
        Assertions.assertEquals(1, pasaportes.size());
        Assertions.assertTrue(pasaportes.get(0).getNumero().startsWith("A"));
    }

    @Test
    @Order(10)
    void findByNumeroEndingWith() {
        List<Pasaporte> pasaportes = pasaporteRepository.findByNumeroEndingWith("456");
        Assertions.assertNotNull(pasaportes);
        Assertions.assertEquals(1, pasaportes.size());
        Assertions.assertTrue(pasaportes.get(0).getNumero().endsWith("456"));
    }

    @Test
    @Order(11)
    void findByNumeroContainingSubstring() {
        List<Pasaporte> pasaportes = pasaporteRepository.findByNumeroContainingSubstring("123");
        Assertions.assertNotNull(pasaportes);
        Assertions.assertEquals(1, pasaportes.size());
        Assertions.assertTrue(pasaportes.get(0).getNumero().contains("123"));
    }

    @Test
    @Order(12)
    void findByPasajeroIdAndNumero() {
        Pasaporte pasaporte = pasaporteRepository.findByPasajeroIdAndNumero(pasajero1.getId(), "A123456");
        Assertions.assertNotNull(pasaporte);
        Assertions.assertEquals(pasajero1.getId(), pasaporte.getPasajero().getId());
        Assertions.assertEquals("A123456", pasaporte.getNumero());
    }
}