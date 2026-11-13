package com.Proyecto.LogiTrack.SA.Dto.Response;

import com.Proyecto.LogiTrack.SA.Model.Movimiento;
import java.time.LocalDateTime;

public record MovimientoResumenDtoResponse(
        Long id,
        LocalDateTime fecha,
        Movimiento.TipoMovimiento tipoMovimiento
) {}
