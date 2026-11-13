package com.Proyecto.LogiTrack.SA.Dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record InventarioBodegaDtoRequest(

        @NotNull(message = "La bodega no puede estar nula")
        @Positive(message = "El ID de la bodega debe ser positivo")
        Long bodegaId,

        @NotNull(message = "El producto no puede estar nulo")
        @Positive(message = "El ID del producto debe ser positivo")
        Long productoId,

        @NotNull(message = "El stock no puede estar nulo")
        @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock

) {}
