package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Model.Movimiento;
import com.Proyecto.LogiTrack.SA.Service.MovimientoService;
import com.Proyecto.LogiTrack.SA.Dto.Request.MovimientoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.MovimientoDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movimiento")
@RequiredArgsConstructor
@Tag(name = "Movimientos", description = "Registro y consulta de movimientos de inventario")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    @Operation(summary = "Registrar movimiento", description = "Registra un movimiento con sus detalles de productos y cantidades.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Movimiento registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o reglas de negocio incumplidas")
    })
    public ResponseEntity<MovimientoDtoResponse> guardar(@Valid @RequestBody MovimientoDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.guardar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar movimientos", description = "Obtiene todos los movimientos registrados.")
    @ApiResponse(responseCode = "200", description = "Movimientos obtenidos correctamente")
    public ResponseEntity<List<MovimientoDtoResponse>> listar() {
        return ResponseEntity.ok(movimientoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar movimiento por ID", description = "Consulta un movimiento mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    public ResponseEntity<MovimientoDtoResponse> buscar(@Parameter(description = "ID del movimiento", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.buscarId(id));
    }

    @GetMapping("/tipo")
    @Operation(summary = "Buscar movimientos por tipo", description = "Filtra los movimientos por ENTRADA, SALIDA o TRANSFERENCIA.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<MovimientoDtoResponse>> buscarTipo(@Parameter(description = "Tipo de movimiento", example = "ENTRADA") @RequestParam Movimiento.TipoMovimiento tipo) {
        return ResponseEntity.ok(movimientoService.buscarTipo(tipo));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar movimientos por usuario", description = "Obtiene los movimientos asociados a un usuario responsable.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<MovimientoDtoResponse>> buscarUsuario(@Parameter(description = "ID del usuario responsable", example = "1") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(movimientoService.buscarUsuario(usuarioId));
    }

    @GetMapping("/bodega-origen/{bodegaId}")
    @Operation(summary = "Buscar movimientos por bodega de origen", description = "Obtiene los movimientos que tienen la bodega indicada como origen.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<MovimientoDtoResponse>> buscarBodegaOrigen(@Parameter(description = "ID de la bodega de origen", example = "1") @PathVariable Long bodegaId) {
        return ResponseEntity.ok(movimientoService.buscarBodegaOrigen(bodegaId));
    }

    @GetMapping("/bodega-destino/{bodegaId}")
    @Operation(summary = "Buscar movimientos por bodega de destino", description = "Obtiene los movimientos que tienen la bodega indicada como destino.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<MovimientoDtoResponse>> buscarBodegaDestino(@Parameter(description = "ID de la bodega de destino", example = "2") @PathVariable Long bodegaId) {
        return ResponseEntity.ok(movimientoService.buscarBodegaDestino(bodegaId));
    }

    @GetMapping("/fechas")
    @Operation(summary = "Buscar movimientos por fechas", description = "Consulta movimientos dentro de un intervalo de fechas. Utiliza formato ISO: yyyy-MM-dd'T'HH:mm:ss.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<MovimientoDtoResponse>> buscarFechas(@Parameter(description = "Fecha inicial", example = "2026-11-01T00:00:00") @RequestParam LocalDateTime fechaInicio, @Parameter(description = "Fecha final", example = "2026-11-30T23:59:59") @RequestParam LocalDateTime fechaFin) {
        return ResponseEntity.ok(movimientoService.buscarFechas(fechaInicio, fechaFin));
    }
}
