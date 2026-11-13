package com.Proyecto.LogiTrack.SA.Dto.Response;

public record InventarioBodegaDtoResponse(
        Long id,
        BodegaResumenDtoResponse bodega,
        ProductoDtoResponse producto,
        Integer stock

) {}
