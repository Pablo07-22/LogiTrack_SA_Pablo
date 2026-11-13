
package com.Proyecto.LogiTrack.SA.Dto.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductoDtoRequest(

        @NotBlank(message = "El nombre del producto no puede estar vacío")
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String nombre,

        Long marcaId,

        @NotNull(message = "La categoría no puede estar nula")
        @Positive(message = "El ID de la categoría debe ser positivo")
        Long categoriaId,

        @NotNull(message = "El precio no puede estar nulo")
        @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
        BigDecimal precioUnitario

) {}
