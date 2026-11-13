package com.Proyecto.LogiTrack.SA.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MarcaDtoRequest(

        @NotBlank(message = "El nombre de la marca no puede estar vacío")
        @Size(max = 100, message = "El nombre de la marca no puede superar los 100 caracteres")
        String nombreMarca

) {}
