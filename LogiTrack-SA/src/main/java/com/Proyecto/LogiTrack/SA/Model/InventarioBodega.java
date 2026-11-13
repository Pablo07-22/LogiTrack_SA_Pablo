package com.Proyecto.LogiTrack.SA.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventario_bodega")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class InventarioBodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bodega_fk", nullable = false)
    private Bodega bodega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_fk", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer stock = 0;
}
