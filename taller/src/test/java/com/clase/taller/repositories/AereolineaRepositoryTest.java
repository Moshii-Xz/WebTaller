package com.clase.taller.repositories;

import com.clase.taller.entities.Aereolinea;
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
class AereolineaRepositoryTest {

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
    AereolineaRepository aereolineaRepository;

    Aereolinea aereolinea1;
    Aereolinea aereolinea2;
    Aereolinea aereolinea3;

    @BeforeEach
    void setUp() {
        // Crear aerolíneas
        aereolinea1 = Aereolinea.builder().nombre("Avianca").aereolineaID(1L).build();
        aereolinea2 = Aereolinea.builder().nombre("Latam").aereolineaID(2L).build();
        aereolinea3 = Aereolinea.builder().nombre("Viva Air").aereolineaID(3L).build();

        aereolineaRepository.saveAll(Arrays.asList(aereolinea1, aereolinea2, aereolinea3));
    }

    @AfterEach
    void tearDown() {
        aereolineaRepository.deleteAll();
    }

    @Test
    @Order(1)
    void findByNombre() {
        List<Aereolinea> aereolineas = aereolineaRepository.findByNombre("Avianca");
        Assertions.assertNotNull(aereolineas);
        Assertions.assertEquals(1, aereolineas.size());
        Assertions.assertEquals("Avianca", aereolineas.get(0).getNombre());
    }

    @Test
    @Order(2)
    void findByAereolineaID() {
        Aereolinea aereolinea = aereolineaRepository.findByAereolineaID(1L);
        Assertions.assertNotNull(aereolinea);
        Assertions.assertEquals(1L, aereolinea.getAereolineaID());
    }

    @Test
    @Order(3)
    void findByNombreContaining() {
        List<Aereolinea> aereolineas = aereolineaRepository.findByNombreContaining("via");
        Assertions.assertNotNull(aereolineas);
        Assertions.assertEquals(1, aereolineas.size());
        Assertions.assertTrue(aereolineas.get(0).getNombre().contains("via"));
    }

    @Test
    @Order(4)
    void findByNombreLike() {
        List<Aereolinea> aereolineas = aereolineaRepository.findByNombreLike("%via%");
        Assertions.assertNotNull(aereolineas);
        Assertions.assertEquals(1, aereolineas.size());
        Assertions.assertTrue(aereolineas.get(0).getNombre().contains("via"));
    }

    @Test
    @Order(5)
    void findByAereolineaIDIn() {
        List<Aereolinea> aereolineas = aereolineaRepository.findByAereolineaIDIn(Arrays.asList(1L, 2L));
        Assertions.assertNotNull(aereolineas);
        Assertions.assertEquals(2, aereolineas.size());
    }

    @Test
    @Order(6)
    void buscarPorNombreExacto() {
        Aereolinea aereolinea = aereolineaRepository.buscarPorNombreExacto("Avianca");
        Assertions.assertNotNull(aereolinea);
        Assertions.assertEquals("Avianca", aereolinea.getNombre());
    }

    @Test
    @Order(7)
    void buscarPorAereolineaID() {
        Aereolinea aereolinea = aereolineaRepository.buscarPorAereolineaID(1L);
        Assertions.assertNotNull(aereolinea);
        Assertions.assertEquals(1L, aereolinea.getAereolineaID());
    }

    @Test
    @Order(8)
    void buscarPorAereolineaIDsEnLista() {
        List<Aereolinea> aereolineas = aereolineaRepository.buscarPorAereolineaIDsEnLista(Arrays.asList(1L, 2L));
        Assertions.assertNotNull(aereolineas);
        Assertions.assertEquals(2, aereolineas.size());
    }

    @Test
    @Order(9)
    void buscarPorNombreNoLike() {
        List<Aereolinea> aereolineas = aereolineaRepository.buscarPorNombreNoLike("%via%");
        Assertions.assertNotNull(aereolineas);
        Assertions.assertEquals(2, aereolineas.size()); // Solo Avianca contiene "via"
    }
}