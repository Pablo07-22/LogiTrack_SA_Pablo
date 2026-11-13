package com.Proyecto.LogiTrack.SA.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import javax.xml.validation.Schema;

public record CiudadDtoRequest(
        @NotBlank(message = "El nombre de la ciudad no puede estar vacío")
        @Size(max = 100, message = "El nombre de la ciudad no puede superar los 100 caracteres")
        String nombreCiudad

) {}
