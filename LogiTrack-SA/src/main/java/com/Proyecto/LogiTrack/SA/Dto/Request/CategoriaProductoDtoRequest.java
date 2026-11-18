package com.Proyecto.LogiTrack.SA.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "CategoriaProductoDtoRequest", description = "Datos para registrar o actualizar una categoría de producto.")
public record CategoriaProductoDtoRequest(

        @Schema(description = "Nombre o tipo de categoría", example = "Electrónica", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "La categoría no puede estar vacía")
        @Size(max = 100, message = "La categoría no puede superar los 100 caracteres")
        String tipoCategoria

) {}
