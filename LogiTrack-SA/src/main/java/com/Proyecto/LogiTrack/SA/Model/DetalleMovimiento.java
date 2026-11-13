package com.Proyecto.LogiTrack.SA.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalle_movimiento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DetalleMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movimiento_fk", nullable = false)
    private Movimiento movimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_fk", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;
}
