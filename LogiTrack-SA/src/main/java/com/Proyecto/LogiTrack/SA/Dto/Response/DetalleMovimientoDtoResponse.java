package com.Proyecto.LogiTrack.SA.Dto.Response;

public record DetalleMovimientoDtoResponse(
        Long id,
        MovimientoResumenDtoResponse movimiento,
        ProductoDtoResponse producto,
        Integer cantidad
) {}
