package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.ProductoService;
import com.Proyecto.LogiTrack.SA.Dto.Request.ProductoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.ProductoDtoResponse;
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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión y consulta de productos")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    @Operation(summary = "Registrar producto", description = "Registra un producto con su marca, categoría y precio unitario.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ProductoDtoResponse> guardar(@Valid @RequestBody ProductoDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene todos los productos registrados.")
    @ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente")
    public ResponseEntity<List<ProductoDtoResponse>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por ID", description = "Consulta un producto mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoDtoResponse> buscar(@Parameter(description = "ID del producto", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarId(id));
    }

    @GetMapping("/nombre")
    @Operation(summary = "Buscar productos por nombre", description = "Busca productos que coincidan con el nombre indicado.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<ProductoDtoResponse>> buscarNombre(@Parameter(description = "Nombre o texto a buscar", example = "Teclado") @RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarNombre(nombre));
    }

    @GetMapping("/marca/{marcaId}")
    @Operation(summary = "Buscar productos por marca", description = "Obtiene los productos asociados a una marca.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<ProductoDtoResponse>> buscarMarca(@Parameter(description = "ID de la marca", example = "1") @PathVariable Long marcaId) {
        return ResponseEntity.ok(productoService.buscarMarca(marcaId));
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Buscar productos por categoría", description = "Obtiene los productos asociados a una categoría.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<ProductoDtoResponse>> buscarCategoria(@Parameter(description = "ID de la categoría", example = "1") @PathVariable Long categoriaId) {
        return ResponseEntity.ok(productoService.buscarCategoria(categoriaId));
    }

    @GetMapping("/precio-mayor")
    @Operation(summary = "Buscar productos por precio mínimo", description = "Obtiene productos cuyo precio sea mayor al valor indicado.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<ProductoDtoResponse>> buscarPrecioMayor(@Parameter(description = "Precio de referencia", example = "10000.00") @RequestParam BigDecimal precio) {
        return ResponseEntity.ok(productoService.buscarPrecioMayor(precio));
    }

    @GetMapping("/precio-menor")
    @Operation(summary = "Buscar productos por precio máximo", description = "Obtiene productos cuyo precio sea menor al valor indicado.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<ProductoDtoResponse>> buscarPrecioMenor(@Parameter(description = "Precio de referencia", example = "50000.00") @RequestParam BigDecimal precio) {
        return ResponseEntity.ok(productoService.buscarPrecioMenor(precio));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza la información de un producto existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoDtoResponse> actualizar(@Valid @RequestBody ProductoDtoRequest dto, @Parameter(description = "ID del producto que se actualizará", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(productoService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del producto", example = "1") @PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
