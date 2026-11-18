package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import com.Proyecto.LogiTrack.SA.Service.AuditoriaService;
import com.Proyecto.LogiTrack.SA.Dto.Response.AuditoriaDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
@Tag(name = "Auditoria", description = "Consultas de registros de auditoría del sistema")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    @Operation(summary = "Listar auditorías", description = "Obtiene todos los registros de auditoria.")
    @ApiResponse(responseCode = "200", description = "Registros obtenidos correctamente")
    public ResponseEntity<List<AuditoriaDtoResponse>> listar() {
        return ResponseEntity.ok(auditoriaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar auditoría por ID", description = "Consulta un registro de auditoría mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<AuditoriaDtoResponse> buscar(@Parameter(description = "Identificador del registro de auditoria", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(auditoriaService.buscarId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar auditorias por usuario", description = "Consulta las operaciones realizadas por un usuario.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<AuditoriaDtoResponse>> buscarUsuario(@Parameter(description = "ID del usuario", example = "1") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(auditoriaService.buscarUsuario(usuarioId));
    }

    @GetMapping("/tipo")
    @Operation(summary = "Buscar auditorias por tipo", description = "Filtra los registros por tipo de operación: INSERTAR, ACTUALIZAR o BORRAR.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<AuditoriaDtoResponse>> buscarTipo(@Parameter(description = "Tipo de operación", example = "INSERTAR") @RequestParam Auditoria.TipoOperacion tipo) {
        return ResponseEntity.ok(auditoriaService.buscarTipo(tipo));
    }

    @GetMapping("/entidad")
    @Operation(summary = "Buscar auditorias por entidad", description = "Filtra los registros por el nombre de la entidad afectada.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<AuditoriaDtoResponse>> buscarEntidad(@Parameter(description = "Nombre de la entidad", example = "Producto") @RequestParam String entidad) {
        return ResponseEntity.ok(auditoriaService.buscarEntidad(entidad));
    }

    @GetMapping("/fechas")
    @Operation(summary = "Buscar auditorías por fechas", description = "Consulta registros dentro de un intervalo de fechas. Utiliza formato ISO: yyyy-MM-dd'T'HH:mm:ss.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<AuditoriaDtoResponse>> buscarFechas(@Parameter(description = "Fecha inicial", example = "2026-11-01T00:00:00") @RequestParam LocalDateTime fechaInicio, @Parameter(description = "Fecha final", example = "2026-11-30T23:59:59") @RequestParam LocalDateTime fechaFin) {
        return ResponseEntity.ok(auditoriaService.buscarFechas(fechaInicio, fechaFin));
    }
}
