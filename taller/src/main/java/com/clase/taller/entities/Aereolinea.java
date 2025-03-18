package com.clase.taller.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aereolinea {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long aereolineaID;
    private String nombre;

    @ManyToMany
    @JoinTable(name = "aereolineaVuelo",
            joinColumns = @JoinColumn(name = "aereolineaID"),
            inverseJoinColumns = @JoinColumn(name = "vueloID"))
    private Set<Vuelo> vuelos; // Renombrado a "vuelos" para mayor claridad
}


