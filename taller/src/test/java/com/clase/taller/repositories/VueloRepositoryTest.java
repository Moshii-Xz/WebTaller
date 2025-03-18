package com.clase.taller.repositories;

import com.clase.taller.entities.*;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@DataJpaTest
@Testcontainers
@Import(TestcontainersConfiguration.class)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VueloRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15").withDatabaseName("testPostgres").withUsername("testUsername").withPassword("testPassword");

            static {
                postgreSQLContainer.start();
            }


    Aereolinea aereolinea1;
    Aereolinea aereolinea2;
    Set<Aereolinea> aereolineas1 = new HashSet<>();
    Set<Aereolinea> aereolineas2 = new HashSet<>();
    Vuelo vuelo1;
    Vuelo vuelo2;
    Vuelo vuelo3;

    @Autowired
    AereolineaRepository aereolineaRepository;

    @Autowired
    VueloRepository vueloRepository;

    @BeforeEach
    void setUp() {


        aereolinea1 = Aereolinea.builder().nombre("Jetsmart").build();
        aereolinea2 = Aereolinea.builder().nombre("Wingo Air").build();
        aereolineas1.add(aereolinea1);
        aereolineas1.add(aereolinea2);
        aereolineas2.add(aereolinea2);
        aereolineaRepository.saveAll(aereolineas1);
        aereolineaRepository.saveAll(aereolineas2);


        vuelo1 = vueloRepository.save(Vuelo.builder().origen("Medellin").destino("Quito").numeroVuelo(UUID.fromString("7145205c-6d70-4558-a704-962c45d11c55")).aereolineas(aereolineas1).build());
        vuelo2 = vueloRepository.save(Vuelo.builder().origen("Bogotá").destino("Medellin").aereolineas(new HashSet<>()).build());
        vuelo3 = vueloRepository.save(Vuelo.builder().origen("Santa Marta").destino("Medellin").aereolineas(aereolineas2).build());
    }

    @AfterEach
    void tearDown() {
        aereolineaRepository.deleteAll();
        vueloRepository.deleteAll();
    }

    @Test
    @Order(1)
    void findByNumerousVelour() {
        Vuelo vueloTest = vueloRepository.buscarPorNumeroVuelo(UUID.fromString("7145205c-6d70-4558-a704-962c45d11c55"));
        Assertions.assertNotNull(vueloTest);
        Assertions.assertEquals(vuelo1.getNumeroVuelo(), vueloTest.getNumeroVuelo());
    }

    @Test
    @Order(2)
    void findByOrigen() {
        List<Vuelo> vuelosTest = vueloRepository.buscarPorOrigen("Medellin");
        Assertions.assertNotNull(vuelosTest);
        Assertions.assertEquals(1, vuelosTest.size());
    }

    @Test
    @Order(3)
    void findByDestino() {
        List<Vuelo> vuelosTest = vueloRepository.findByDestino("Medellin");
        Assertions.assertNotNull(vuelosTest);
        Assertions.assertEquals(2, vuelosTest.size());
    }

    @Test
    @Order(4)
    void findByOrigenAndDestino() {
        List<Vuelo> vuelosTest = vueloRepository.findByOrigenAndDestino("Santa Marta", "Medellin");
        Assertions.assertNotNull(vuelosTest);
        Assertions.assertEquals(1, vuelosTest.size());
        Assertions.assertEquals(vuelo3.getId(), vuelosTest.get(0).getId());
    }

    @Test
    @Order(5)
    void findByReservasIsNotNull() {
        List<Vuelo> vuelosTest = vueloRepository.findByReservasIsNotNull();
        Assertions.assertEquals(3, vuelosTest.size()); // Asumiendo que todas las reservas son listas vacías (no nulas)
    }

    @Test
    @Order(6)
    void buscarPorNumeroVuelo() {
        Vuelo vueloTest = vueloRepository.findByNumeroVuelo(UUID.fromString("7145205c-6d70-4558-a704-962c45d11c55"));
        Assertions.assertNotNull(vueloTest);
        Assertions.assertEquals(vuelo1.getNumeroVuelo(), vueloTest.getNumeroVuelo());
    }

    @Test
    @Order(7)
    void buscarPorOrigen() {
        List<Vuelo> vuelosTest = vueloRepository.findByOrigen("Medellin");
        Assertions.assertNotNull(vuelosTest);
        Assertions.assertEquals(1, vuelosTest.size());
    }

    @Test
    @Order(8)
    void buscarPorDestino() {
        List<Vuelo> vuelosTest = vueloRepository.buscarPorDestino("Medellin");
        Assertions.assertNotNull(vuelosTest);
        Assertions.assertEquals(2, vuelosTest.size());
    }

    @Test
    @Order(9)
    void buscarPorOrigenYDestino() {
        List<Vuelo> vuelosTest = vueloRepository.buscarPorOrigenYDestino("Santa Marta", "Medellin");
        Assertions.assertNotNull(vuelosTest);
        Assertions.assertEquals(1, vuelosTest.size());
        Assertions.assertEquals(vuelo3.getId(), vuelosTest.get(0).getId());
    }

    @Test
    @Order(10)
    void buscarConReservas() {
        List<Vuelo> vuelosTest = vueloRepository.buscarConReservas();
        Assertions.assertTrue(vuelosTest.isEmpty()); // Asumiendo que no hay reservas en los vuelos
    }
}