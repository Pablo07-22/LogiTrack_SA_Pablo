package com.Proyecto.LogiTrack.SA.Dto.Response;

import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import java.time.LocalDateTime;

public record AuditoriaDtoResponse(
        Long id,
        LocalDateTime fecha,
        Auditoria.TipoOperacion tipoOperacion,
        UsuarioResumenDtoResponse usuario,
        String entidadAfectada,
        Long idRegistro,
        String valoresAnteriores,
        String valoresNuevos
) {}
