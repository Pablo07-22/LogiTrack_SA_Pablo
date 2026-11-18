package com.Proyecto.LogiTrack.SA.Dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DetalleMovimientoDtoResponse", description = "Informacion de un producto incluido en un movimiento.")
public record DetalleMovimientoDtoResponse(

        @Schema(description = "Identificador del detalle", example = "1")
        Long id,

        @Schema(description = "Información resumida del movimiento asociado")
        MovimientoResumenDtoResponse movimiento,

        @Schema(description = "Información del producto involucrado")
        ProductoDtoResponse producto,

        @Schema(description = "Cantidad de unidades del producto", example = "25")
        Integer cantidad

) {}
