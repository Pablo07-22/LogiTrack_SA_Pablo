package com.Proyecto.LogiTrack.SA.Dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "BodegaResumenDtoResponse", description = "Identificador y nombre de una bodega.")
public record BodegaResumenDtoResponse(

        @Schema(description = "Identificador de la bodega", example = "1")
        Long id,

        @Schema(description = "Nombre de la bodega", example = "Bodega Principal")
        String nombre

) {}
