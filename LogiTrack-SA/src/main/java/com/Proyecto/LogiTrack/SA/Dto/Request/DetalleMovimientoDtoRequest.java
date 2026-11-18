package com.Proyecto.LogiTrack.SA.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "DetalleMovimientoDtoRequest", description = "Datos de un producto incluido en un movimiento de inventario.")
public record DetalleMovimientoDtoRequest(

        @Schema(description = "Identificador del producto", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "El producto no puede estar nulo")
        @Positive(message = "El ID del producto debe ser positivo")
        Long productoId,

        @Schema(description = "Cantidad de unidades del producto", example = "25", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "La cantidad no puede estar nula")
        @Positive(message = "La cantidad debe ser positiva")
        Integer cantidad

) {}
