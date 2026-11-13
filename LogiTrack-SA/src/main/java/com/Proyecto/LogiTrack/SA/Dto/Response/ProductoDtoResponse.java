package com.Proyecto.LogiTrack.SA.Dto.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductoDtoResponse(
        Long id,
        String nombre,
        MarcaDtoResponse marca,
        CategoriaProductoDtoResponse categoria,
        BigDecimal precioUnitario,
        LocalDateTime fechaCreacion
) {}
