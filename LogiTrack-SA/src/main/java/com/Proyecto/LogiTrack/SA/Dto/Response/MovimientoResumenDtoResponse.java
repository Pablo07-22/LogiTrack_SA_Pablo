package com.Proyecto.LogiTrack.SA.Dto.Response;

import com.Proyecto.LogiTrack.SA.Model.Movimiento;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "MovimientoResumenDtoResponse", description = "Informacion resumida de un movimiento.")
public record MovimientoResumenDtoResponse(

        @Schema(description = "Identificador del movimiento", example = "1")
        Long id,

        @Schema(description = "Fecha y hora del movimiento", example = "2026-11-17T10:30:00")
        LocalDateTime fecha,

        @Schema(description = "Tipo de movimiento", example = "TRANSFERENCIA")
        Movimiento.TipoMovimiento tipoMovimiento

) {}
