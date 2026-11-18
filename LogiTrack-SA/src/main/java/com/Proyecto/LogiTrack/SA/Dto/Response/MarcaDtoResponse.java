package com.Proyecto.LogiTrack.SA.Dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MarcaDtoResponse", description = "Informacion de una marca de productos.")
public record MarcaDtoResponse(

        @Schema(description = "Identificador de la marca", example = "1")
        Long id,

        @Schema(description = "Nombre de la marca", example = "Samsung")
        String nombreMarca

) {}
