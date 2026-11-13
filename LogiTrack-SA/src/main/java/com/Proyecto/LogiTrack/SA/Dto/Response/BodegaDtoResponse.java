package com.Proyecto.LogiTrack.SA.Dto.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BodegaDtoResponse(
        Long id,
        String nombre,
        CiudadDtoResponse ciudad,
        UsuarioResumenDtoResponse encargado,
        BigDecimal capacidad,
        LocalDateTime fechaCreacion
) {}
