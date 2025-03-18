package com.clase.taller.repositories;

import com.clase.taller.entities.Pasajero;
import com.clase.taller.entities.Reserva;
import com.clase.taller.entities.Vuelo;
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
import java.util.UUID;

@DataJpaTest
@Testcontainers
@Import(TestcontainersConfiguration.class)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservaRepositoryTest {

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
    ReservaRepository reservaRepository;

    @Autowired
    PasajeroRepository pasajeroRepository;

    @Autowired
    VueloRepository vueloRepository;

    Reserva reserva1;
    Reserva reserva2;
    Reserva reserva3;
    Pasajero pasajero1;
    Pasajero pasajero2;
    Vuelo vuelo1;
    Vuelo vuelo2;

    @BeforeEach
    void setUp() {
        // Crear pasajeros
        pasajero1 = Pasajero.builder().nombre("Juan Perez").build();
        pasajero2 = Pasajero.builder().nombre("Maria Lopez").build();
        pasajeroRepository.save(pasajero1);
        pasajeroRepository.save(pasajero2);

        // Crear vuelos
        vuelo1 = Vuelo.builder().origen("Medellín").destino("Bogotá").build();
        vuelo2 = Vuelo.builder().origen("Bogotá").destino("Medellín").build();
        vueloRepository.save(vuelo1);
        vueloRepository.save(vuelo2);

        // Crear reservas
        reserva1 = Reserva.builder()
                .codigoReserva(UUID.randomUUID())
                .pasajero(pasajero1)
                .vuelo(vuelo1)
                .build();
        reserva2 = Reserva.builder()
                .codigoReserva(UUID.randomUUID())
                .pasajero(pasajero2)
                .vuelo(vuelo2)
                .build();
        reserva3 = Reserva.builder()
                .codigoReserva(UUID.randomUUID())
                .pasajero(pasajero1)
                .vuelo(vuelo2)
                .build();

        reservaRepository.saveAll(Arrays.asList(reserva1, reserva2, reserva3));
    }

    @AfterEach
    void tearDown() {
        reservaRepository.deleteAll();
        pasajeroRepository.deleteAll();
        vueloRepository.deleteAll();
    }

    @Test
    @Order(1)
    void findByCodigoReserva() {
        List<Reserva> reservas = reservaRepository.findByCodigoReserva(reserva1.getCodigoReserva());
        Assertions.assertNotNull(reservas);
        Assertions.assertEquals(1, reservas.size());
        Assertions.assertEquals(reserva1.getCodigoReserva(), reservas.get(0).getCodigoReserva());
    }

    @Test
    @Order(2)
    void findByPasajero_Id() {
        List<Reserva> reservas = reservaRepository.findByPasajero_Id(pasajero1.getId());
        Assertions.assertNotNull(reservas);
        Assertions.assertEquals(2, reservas.size()); // reserva1 y reserva3 pertenecen a pasajero1
    }

    @Test
    @Order(3)
    void findByVuelo_Id() {
        List<Reserva> reservas = reservaRepository.findByVuelo_Id(vuelo1.getId());
        Assertions.assertNotNull(reservas);
        Assertions.assertEquals(1, reservas.size());
        Assertions.assertEquals(vuelo1.getId(), reservas.get(0).getVuelo().getId());
    }

    @Test
    @Order(4)
    void findByIdReserva() {
        Reserva reserva = reservaRepository.findByIdReserva(reserva1.getId());
        Assertions.assertNotNull(reserva);
        Assertions.assertEquals(reserva1.getId(), reserva.getId());
    }

    @Test
    @Order(5)
    void findByIdReservaEnLista() {
        List<Reserva> reservas = reservaRepository.findByIdReservaEnLista(Arrays.asList(reserva1.getId(), reserva2.getId()));
        Assertions.assertNotNull(reservas);
        Assertions.assertEquals(2, reservas.size());
    }

    @Test
    @Order(6)
    void findByIdReservaNotIn() {
        List<Reserva> reservas = reservaRepository.findByIdReservaNotIn(Arrays.asList(reserva1.getId(), reserva2.getId()));
        Assertions.assertNotNull(reservas);
        Assertions.assertEquals(1, reservas.size()); // Solo reserva3 no está en la lista
    }

    @Test
    @Order(7)
    void findReservaByCodigoReserva() {
        List<Reserva> reservas = reservaRepository.findReservaByCodigoReserva(reserva1.getCodigoReserva());
        Assertions.assertNotNull(reservas);
        Assertions.assertEquals(1, reservas.size());
        Assertions.assertEquals(reserva1.getCodigoReserva(), reservas.get(0).getCodigoReserva());
    }

    @Test
    @Order(8)
    void findReservaByNombrePasajero() {
        List<Reserva> reservas = reservaRepository.findReservaByNombrePasajero("Juan Perez");
        Assertions.assertNotNull(reservas);
        Assertions.assertEquals(2, reservas.size()); // reserva1 y reserva3 pertenecen a "Juan Perez"
    }

    @Test
    @Order(9)
    void findByNombrePasajeroAndCodigoReserva() {
        List<Reserva> reservas = reservaRepository.findByNombrePasajeroAndCodigoReserva("Juan Perez", reserva1.getCodigoReserva());
        Assertions.assertNotNull(reservas);
        Assertions.assertEquals(1, reservas.size());
        Assertions.assertEquals("Juan Perez", reservas.get(0).getPasajero().getNombre());
        Assertions.assertEquals(reserva1.getCodigoReserva(), reservas.get(0).getCodigoReserva());
    }

    @Test
    @Order(10)
    void findByVueloIdAndNombrePasajero() {
        List<Reserva> reservas = reservaRepository.findByVueloIdAndNombrePasajero(vuelo1.getId(), "Juan Perez");
        Assertions.assertNotNull(reservas);
        Assertions.assertEquals(1, reservas.size());
        Assertions.assertEquals(vuelo1.getId(), reservas.get(0).getVuelo().getId());
        Assertions.assertEquals("Juan Perez", reservas.get(0).getPasajero().getNombre());
    }
}