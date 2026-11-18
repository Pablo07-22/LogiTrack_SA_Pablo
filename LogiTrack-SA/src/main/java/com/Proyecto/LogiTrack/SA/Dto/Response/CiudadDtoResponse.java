package com.Proyecto.LogiTrack.SA.Dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CiudadDtoResponse", description = "Informacion de una ciudad.")
public record CiudadDtoResponse(

        @Schema(description = "Identificador de la ciudad", example = "1")
        Long idCiudad,

        @Schema(description = "Nombre de la ciudad", example = "Medellín")
        String nombreCiudad

) {}
