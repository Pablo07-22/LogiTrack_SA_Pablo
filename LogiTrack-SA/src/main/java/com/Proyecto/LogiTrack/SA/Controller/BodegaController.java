package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.BodegaService;
import com.Proyecto.LogiTrack.SA.Dto.Request.BodegaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.BodegaDtoResponse;
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
@RequestMapping("/api/bodega")
@RequiredArgsConstructor
@Tag(name = "Bodegas", description = "Operaciones para crear, consultar, actualizar y eliminar bodegas")
public class BodegaController {

    private final BodegaService bodegaService;

    @PostMapping
    @Operation(summary = "Registrar bodega", description = "Registra una bodega asociada a una ciudad y a un encargado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Bodega creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<BodegaDtoResponse> guardar(@Valid @RequestBody BodegaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bodegaService.guardar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar bodegas", description = "Obtiene todas las bodegas registradas.")
    @ApiResponse(responseCode = "200", description = "Bodegas obtenidas correctamente")
    public ResponseEntity<List<BodegaDtoResponse>> listar() {
        return ResponseEntity.ok(bodegaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar bodega por ID", description = "Obtiene una bodega mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bodega encontrada"),
            @ApiResponse(responseCode = "404", description = "Bodega no encontrada")
    })
    public ResponseEntity<BodegaDtoResponse> buscar(@Parameter(description = "ID de la bodega", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(bodegaService.buscarId(id));
    }

    @GetMapping("/ciudad/{ciudadId}")
    @Operation(summary = "Buscar bodegas por ciudad", description = "Obtiene las bodegas asociadas a una ciudad.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<BodegaDtoResponse>> buscarCiudad(@Parameter(description = "ID de la ciudad", example = "1") @PathVariable Long ciudadId) {
        return ResponseEntity.ok(bodegaService.buscarCiudad(ciudadId));
    }

    @GetMapping("/encargado/{encargadoId}")
    @Operation(summary = "Buscar bodegas por encargado", description = "Obtiene las bodegas asignadas a un usuario encargado.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<BodegaDtoResponse>> buscarEncargado(@Parameter(description = "ID del usuario encargado", example = "1") @PathVariable Long encargadoId) {
        return ResponseEntity.ok(bodegaService.buscarEncargado(encargadoId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar bodega", description = "Actualiza la información de una bodega existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bodega actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Bodega no encontrada")
    })
    public ResponseEntity<BodegaDtoResponse> actualizar(@Valid @RequestBody BodegaDtoRequest dto, @Parameter(description = "ID de la bodega que se actualizará", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(bodegaService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar bodega", description = "Elimina una bodega mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Bodega eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Bodega no encontrada")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la bodega", example = "1") @PathVariable Long id) {
        bodegaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
