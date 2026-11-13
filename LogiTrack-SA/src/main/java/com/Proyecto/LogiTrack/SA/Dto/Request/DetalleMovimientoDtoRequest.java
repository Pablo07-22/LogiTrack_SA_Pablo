package com.Proyecto.LogiTrack.SA.Dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DetalleMovimientoDtoRequest(

        @NotNull(message = "El producto no puede estar nulo")
        @Positive(message = "El ID del producto debe ser positivo")
        Long productoId,

        @NotNull(message = "La cantidad no puede estar nula")
        @Positive(message = "La cantidad debe ser positiva")
        Integer cantidad

) {}
