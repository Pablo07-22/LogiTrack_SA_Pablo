package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.MarcaService;
import com.Proyecto.LogiTrack.SA.Dto.Request.MarcaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.MarcaDtoResponse;
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
@RequestMapping("/api/marca")
@RequiredArgsConstructor
@Tag(name = "Marcas", description = "Gestión de marcas de productos")
public class MarcaController {

    private final MarcaService marcaService;

    @PostMapping
    @Operation(summary = "Registrar marca", description = "Registra una nueva marca.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Marca creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<MarcaDtoResponse> guardar(@Valid @RequestBody MarcaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.guardar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar marcas", description = "Obtiene todas las marcas registradas.")
    @ApiResponse(responseCode = "200", description = "Marcas obtenidas correctamente")
    public ResponseEntity<List<MarcaDtoResponse>> listar() {
        return ResponseEntity.ok(marcaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar marca por ID", description = "Consulta una marca mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marca encontrada"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    })
    public ResponseEntity<MarcaDtoResponse> buscar(@Parameter(description = "ID de la marca", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(marcaService.buscarId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar marca", description = "Modifica el nombre de una marca existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marca actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    })
    public ResponseEntity<MarcaDtoResponse> actualizar(@Valid @RequestBody MarcaDtoRequest dto, @Parameter(description = "ID de la marca que se actualizará", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(marcaService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar marca", description = "Elimina una marca mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Marca eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la marca", example = "1") @PathVariable Long id) {
        marcaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
