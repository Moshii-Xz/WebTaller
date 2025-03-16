package com.example.taller.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

@Entity
@Data
public class Pasajero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String NID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pasaporteID", nullable = false, referencedColumnName = "id")
    private Pasaporte pasaporte;

    @OneToMany(mappedBy = "pasajero")
    private Set<Reserva> reservas;
}

