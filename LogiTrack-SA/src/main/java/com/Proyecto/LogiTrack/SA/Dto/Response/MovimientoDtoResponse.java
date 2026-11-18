package com.Proyecto.LogiTrack.SA.Dto.Response;

import com.Proyecto.LogiTrack.SA.Model.Movimiento;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "MovimientoDtoResponse", description = "Informacion de un movimiento de entrada, salida o transferencia.")
public record MovimientoDtoResponse(

        @Schema(description = "Identificador del movimiento", example = "1")
        Long id,

        @Schema(description = "Fecha y hora del movimiento", example = "2026-11-17T10:30:00")
        LocalDateTime fecha,

        @Schema(description = "Tipo de movimiento", example = "ENTRADA")
        Movimiento.TipoMovimiento tipoMovimiento,

        @Schema(description = "Usuario responsable del movimiento")
        UsuarioResumenDtoResponse usuarioResponsable,

        @Schema(description = "Bodega de origen del movimiento")
        BodegaResumenDtoResponse bodegaOrigen,

        @Schema(description = "Bodega de destino del movimiento")
        BodegaResumenDtoResponse bodegaDestino,

        @Schema(description = "Observación del movimiento", example = "Ingreso de mercancía")
        String observacion

) {}
