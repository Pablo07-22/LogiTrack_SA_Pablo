package com.Proyecto.LogiTrack.SA.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(name = "InventarioBodegaDtoRequest", description = "Datos para registrar o actualizar un inventario por bodega.")
public record InventarioBodegaDtoRequest(

        @Schema(description = "Identificador de la bodega", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "La bodega no puede estar nula")
        @Positive(message = "El ID de la bodega debe ser positivo")
        Long bodegaId,

        @Schema(description = "Identificador del producto", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "El producto no puede estar nulo")
        @Positive(message = "El ID del producto debe ser positivo")
        Long productoId,

        @Schema(description = "Cantidad disponible del producto", example = "100", minimum = "0", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "El stock no puede estar nulo")
        @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock

) {}
