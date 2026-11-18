package com.Proyecto.LogiTrack.SA.Dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "InventarioBodegaDtoResponse", description = "Informacion del inventario de un producto en una bodega.")
public record InventarioBodegaDtoResponse(

        @Schema(description = "Identificador del registro de inventario", example = "1")
        Long id,

        @Schema(description = "Información resumida de la bodega")
        BodegaResumenDtoResponse bodega,

        @Schema(description = "Información del producto almacenado")
        ProductoDtoResponse producto,

        @Schema(description = "Cantidad disponible del producto", example = "100")
        Integer stock

) {}
