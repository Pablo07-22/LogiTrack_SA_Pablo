package com.Proyecto.LogiTrack.SA.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(name = "BodegaDtoRequest", description = "Datos necesarios para registrar o actualizar una bodega.")
public record BodegaDtoRequest(

        @Schema(description = "Nombre de la bodega", example = "Bodega Principal", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "El nombre de la bodega no puede estar vacío")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @Schema(description = "Identificador de la ciudad donde se encuentra la bodega", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "La ciudad no puede estar nula")
        @Positive(message = "El ID de la ciudad debe ser positivo")
        Long ciudadId,

        @Schema(description = "Identificador del usuario encargado de la bodega", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "El encargado no puede estar nulo")
        @Positive(message = "El ID del encargado debe ser positivo")
        Long encargadoId,

        @Schema(description = "Capacidad de almacenamiento de la bodega", example = "1500.00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "La capacidad no puede estar nula")
        @Positive(message = "La capacidad debe ser positiva")
        BigDecimal capacidad

) {}
