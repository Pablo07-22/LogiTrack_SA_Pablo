
package com.Proyecto.LogiTrack.SA.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categoria_producto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoriaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_categoria", nullable = false, unique = true, length = 100)
    private String tipoCategoria;
}
