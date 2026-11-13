package com.Proyecto.LogiTrack.SA.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ciudad")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCiudad;

    @Column(name = "nombre_ciudad", nullable = false, unique = true, length = 100)
    private String nombreCiudad;
}
