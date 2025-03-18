package com.clase.taller.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Entity
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Se cambia a UUID y se asigna un valor por defecto
    @Column(nullable = false, updatable = false)
    private UUID codigoReserva = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "pasajero_id", nullable = false)
    private Pasajero pasajero;

    @ManyToOne
    @JoinColumn(name = "vueloID", nullable = false)
    private Vuelo vuelo;
}

