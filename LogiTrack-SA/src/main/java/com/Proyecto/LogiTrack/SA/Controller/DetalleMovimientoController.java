package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.DetalleMovimientoService;
import com.Proyecto.LogiTrack.SA.Dto.Response.DetalleMovimientoDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-movimiento")
@RequiredArgsConstructor
@Tag(name = "Detalles de movimientos", description = "Consultas de productos y cantidades registradas en movimientos")
public class DetalleMovimientoController {

    private final DetalleMovimientoService detalleMovimientoService;

    @GetMapping
    @Operation(summary = "Listar detalles", description = "Obtiene todos los detalles de movimientos registrados.")
    @ApiResponse(responseCode = "200", description = "Detalles obtenidos correctamente")
    public ResponseEntity<List<DetalleMovimientoDtoResponse>> listar() {
        return ResponseEntity.ok(detalleMovimientoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar detalle por ID", description = "Consulta un detalle de movimiento mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<DetalleMovimientoDtoResponse> buscar(@Parameter(description = "ID del detalle", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(detalleMovimientoService.buscarId(id));
    }

    @GetMapping("/movimiento/{movimientoId}")
    @Operation(summary = "Buscar detalles por movimiento", description = "Obtiene todos los productos que forman parte de un movimiento.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<DetalleMovimientoDtoResponse>> buscarMovimiento(@Parameter(description = "ID del movimiento", example = "1") @PathVariable Long movimientoId) {
        return ResponseEntity.ok(detalleMovimientoService.buscarMovimiento(movimientoId));
    }

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Buscar detalles por producto", description = "Consulta los detalles de movimientos en los que aparece un producto.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<DetalleMovimientoDtoResponse>> buscarProducto(@Parameter(description = "ID del producto", example = "1") @PathVariable Long productoId) {
        return ResponseEntity.ok(detalleMovimientoService.buscarProducto(productoId));
    }
}
