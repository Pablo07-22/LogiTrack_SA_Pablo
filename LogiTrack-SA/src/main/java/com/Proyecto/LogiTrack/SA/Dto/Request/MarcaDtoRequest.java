package com.Proyecto.LogiTrack.SA.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "MarcaDtoRequest", description = "Datos necesarios para registrar o actualizar una marca.")
public record MarcaDtoRequest(

        @Schema(description = "Nombre de la marca", example = "Samsung", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "El nombre de la marca no puede estar vacío")
        @Size(max = 100, message = "El nombre de la marca no puede superar los 100 caracteres")
        String nombreMarca

) {}
