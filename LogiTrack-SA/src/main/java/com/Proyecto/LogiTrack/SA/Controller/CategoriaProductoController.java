package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.CategoriaProductoService;
import com.Proyecto.LogiTrack.SA.Dto.Request.CategoriaProductoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.CategoriaProductoDtoResponse;
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
@RequestMapping("/api/categoria-producto")
@RequiredArgsConstructor
@Tag(name = "Categorías de productos", description = "Gestión de categorías de productos")
public class CategoriaProductoController {

    private final CategoriaProductoService categoriaProductoService;

    @PostMapping
    @Operation(summary = "Registrar categoría", description = "Crea una nueva categoría de producto.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<CategoriaProductoDtoResponse> guardar(@Valid @RequestBody CategoriaProductoDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaProductoService.guardar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar categorías", description = "Obtiene todas las categorías registradas.")
    @ApiResponse(responseCode = "200", description = "Categorías obtenidas correctamente")
    public ResponseEntity<List<CategoriaProductoDtoResponse>> listar() {
        return ResponseEntity.ok(categoriaProductoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoría por ID", description = "Consulta una categoría mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<CategoriaProductoDtoResponse> buscar(@Parameter(description = "ID de la categoría", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(categoriaProductoService.buscarId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría", description = "Modifica el nombre de una categoría existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<CategoriaProductoDtoResponse> actualizar(@Valid @RequestBody CategoriaProductoDtoRequest dto, @Parameter(description = "ID de la categoría que se actualizara", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(categoriaProductoService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la categoría", example = "1") @PathVariable Long id) {
        categoriaProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
