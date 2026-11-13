package com.Proyecto.LogiTrack.SA.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record BodegaDtoRequest(

        @NotBlank(message = "El nombre de la bodega no puede estar vacío")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @NotNull(message = "La ciudad no puede estar nula")
        @Positive(message = "El ID de la ciudad debe ser positivo")
        Long ciudadId,

        @NotNull(message = "El encargado no puede estar nulo")
        @Positive(message = "El ID del encargado debe ser positivo")
        Long encargadoId,

        @NotNull(message = "La capacidad no puede estar nula")
        @Positive(message = "La capacidad debe ser positiva")
        BigDecimal capacidad

) {}
