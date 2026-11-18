package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.InventarioBodegaService;
import com.Proyecto.LogiTrack.SA.Dto.Request.InventarioBodegaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.InventarioBodegaDtoResponse;
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

import java.util.List;

@RestController
@RequestMapping("/api/inventario-bodega")
@RequiredArgsConstructor
@Tag(name = "Inventario por bodega", description = "Gestión y consulta de existencias por bodega y producto")
@SecurityRequirement(name = "bearerAuth")
public class InventarioBodegaController {

    private final InventarioBodegaService inventarioBodegaService;

    @PostMapping
    @Operation(summary = "Registrar inventario", description = "Registra una relación de inventario entre una bodega y un producto.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inventario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<InventarioBodegaDtoResponse> guardar(@Valid @RequestBody InventarioBodegaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioBodegaService.guardar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar inventarios", description = "Obtiene todos los registros de inventario.")
    @ApiResponse(responseCode = "200", description = "Inventarios obtenidos correctamente")
    public ResponseEntity<List<InventarioBodegaDtoResponse>> listar() {
        return ResponseEntity.ok(inventarioBodegaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar inventario por ID", description = "Consulta un registro de inventario mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public ResponseEntity<InventarioBodegaDtoResponse> buscar(@Parameter(description = "ID del registro de inventario", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(inventarioBodegaService.buscarId(id));
    }

    @GetMapping("/bodega/{bodegaId}")
    @Operation(summary = "Consultar inventario por bodega", description = "Obtiene los productos y existencias de una bodega.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<InventarioBodegaDtoResponse>> buscarBodega(@Parameter(description = "ID de la bodega", example = "1") @PathVariable Long bodegaId) {
        return ResponseEntity.ok(inventarioBodegaService.buscarBodega(bodegaId));
    }

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Consultar inventario por producto", description = "Obtiene las existencias de un producto en las diferentes bodegas.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<InventarioBodegaDtoResponse>> buscarProducto(@Parameter(description = "ID del producto", example = "1") @PathVariable Long productoId) {
        return ResponseEntity.ok(inventarioBodegaService.buscarProducto(productoId));
    }

    @GetMapping("/stock-bajo")
    @Operation(summary = "Consultar stock bajo", description = "Obtiene los registros cuyo stock se encuentra por debajo del mínimo indicado.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<InventarioBodegaDtoResponse>> buscarStockBajo(@Parameter(description = "Cantidad mínima de referencia", example = "10") @RequestParam Integer stockMinimo) {
        return ResponseEntity.ok(inventarioBodegaService.buscarStockBajo(stockMinimo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar inventario", description = "Actualiza los datos de un registro de inventario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public ResponseEntity<InventarioBodegaDtoResponse> actualizar(@Valid @RequestBody InventarioBodegaDtoRequest dto, @Parameter(description = "ID del registro de inventario", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(inventarioBodegaService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inventario", description = "Elimina un registro de inventario mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inventario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del registro de inventario", example = "1") @PathVariable Long id) {
        inventarioBodegaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
