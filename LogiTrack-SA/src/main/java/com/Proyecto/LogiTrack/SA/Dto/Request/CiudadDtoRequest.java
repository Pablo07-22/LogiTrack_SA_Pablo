package com.Proyecto.LogiTrack.SA.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "CiudadDtoRequest", description = "Datos necesarios para registrar o actualizar una ciudad.")
public record CiudadDtoRequest(

        @Schema(description = "Nombre de la ciudad", example = "Medellin", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "El nombre de la ciudad no puede estar vacío")
        @Size(max = 100, message = "El nombre de la ciudad no puede superar los 100 caracteres")
        String nombreCiudad

) {}
