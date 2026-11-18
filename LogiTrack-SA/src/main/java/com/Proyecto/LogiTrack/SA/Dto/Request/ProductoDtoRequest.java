package com.Proyecto.LogiTrack.SA.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(name = "ProductoDtoRequest", description = "Datos necesarios para registrar o actualizar un producto.")
public record ProductoDtoRequest(

        @Schema(description = "Nombre del producto", example = "Teclado inalámbrico", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "El nombre del producto no puede estar vacío")
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String nombre,

        @Schema(description = "Identificador de la marca. Es opcional", example = "1")
        Long marcaId,

        @Schema(description = "Identificador de la categoría del producto", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "La categoría no puede estar nula")
        @Positive(message = "El ID de la categoría debe ser positivo")
        Long categoriaId,

        @Schema(description = "Precio unitario del producto", example = "85000.00", minimum = "0.00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "El precio no puede estar nulo")
        @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
        BigDecimal precioUnitario

) {}
