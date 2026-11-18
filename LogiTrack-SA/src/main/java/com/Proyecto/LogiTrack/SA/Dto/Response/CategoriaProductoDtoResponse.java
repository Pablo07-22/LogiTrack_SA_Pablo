package com.Proyecto.LogiTrack.SA.Dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CategoriaProductoDtoResponse", description = "Informacion de una categoría de producto.")
public record CategoriaProductoDtoResponse(

        @Schema(description = "Identificador de la categoría", example = "1")
        Long id,

        @Schema(description = "Nombre de la categoría", example = "Electrónica")
        String tipoCategoria

) {}
