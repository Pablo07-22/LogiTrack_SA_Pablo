package com.Proyecto.LogiTrack.SA.Dto.Response;

import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "AuditoriaDtoResponse", description = "Informacion de una operación registrada en la auditoria.")
public record AuditoriaDtoResponse(

        @Schema(description = "Identificador del registro de auditoría", example = "1")
        Long id,

        @Schema(description = "Fecha y hora de la operación", example = "2026-11-17T10:30:00")
        LocalDateTime fecha,

        @Schema(description = "Tipo de operación realizada", example = "INSERTAR")
        Auditoria.TipoOperacion tipoOperacion,

        @Schema(description = "Usuario que realizó la operación")
        UsuarioResumenDtoResponse usuario,

        @Schema(description = "Nombre de la entidad afectada", example = "Producto")
        String entidadAfectada,

        @Schema(description = "Identificador del registro afectado", example = "15")
        Long idRegistro,

        @Schema(description = "Valores anteriores del registro, representados como texto JSON")
        String valoresAnteriores,

        @Schema(description = "Valores nuevos del registro, representados como texto JSON")
        String valoresNuevos

) {}
