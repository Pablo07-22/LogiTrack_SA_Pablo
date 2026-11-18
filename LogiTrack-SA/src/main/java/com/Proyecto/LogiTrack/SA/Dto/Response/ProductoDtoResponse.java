package com.Proyecto.LogiTrack.SA.Dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(name = "ProductoDtoResponse", description = "Informacion completa de un producto.")
public record ProductoDtoResponse(

        @Schema(description = "Identificador del producto", example = "1")
        Long id,

        @Schema(description = "Nombre del producto", example = "Teclado inalámbrico")
        String nombre,

        @Schema(description = "Marca asociada al producto")
        MarcaDtoResponse marca,

        @Schema(description = "Categoría del producto")
        CategoriaProductoDtoResponse categoria,

        @Schema(description = "Precio unitario del producto", example = "85000.00")
        BigDecimal precioUnitario,

        @Schema(description = "Fecha de creación del producto", example = "2026-11-17T10:30:00")
        LocalDateTime fechaCreacion

) {}
