package com.example.taller.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Vuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Se cambia a UUID y se asigna un valor por defecto
    @Column(nullable = false, updatable = false)
    private UUID numeroVuelo = UUID.randomUUID();

    @Column(nullable = false)
    private String origen;

    @Column(nullable = false)
    private String destino;

    @ManyToMany(mappedBy = "vuelos")
    private Set<Aereolinea> aereolineas;

    @OneToMany(mappedBy = "vuelo")
    private Set<Reserva> reservas;
}

