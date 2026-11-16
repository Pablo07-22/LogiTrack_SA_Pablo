package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.ProductoService;
import com.Proyecto.LogiTrack.SA.Dto.Request.ProductoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.ProductoDtoResponse;
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
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoDtoResponse> guardar(@Valid @RequestBody ProductoDtoRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductoDtoResponse>> listar(){
        return ResponseEntity.ok(productoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDtoResponse> buscar(@PathVariable Long id){
        return ResponseEntity.ok(productoService.buscarId(id));
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<ProductoDtoResponse>> buscarNombre(@RequestParam String nombre){
        return ResponseEntity.ok(productoService.buscarNombre(nombre));
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<ProductoDtoResponse>> buscarMarca(@PathVariable Long marcaId){
        return ResponseEntity.ok(productoService.buscarMarca(marcaId));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDtoResponse>> buscarCategoria(@PathVariable Long categoriaId){
        return ResponseEntity.ok(productoService.buscarCategoria(categoriaId));
    }

    @GetMapping("/precio-mayor")
    public ResponseEntity<List<ProductoDtoResponse>> buscarPrecioMayor(@RequestParam BigDecimal precio){
        return ResponseEntity.ok(productoService.buscarPrecioMayor(precio));
    }

    @GetMapping("/precio-menor")
    public ResponseEntity<List<ProductoDtoResponse>> buscarPrecioMenor(@RequestParam BigDecimal precio){
        return ResponseEntity.ok(productoService.buscarPrecioMenor(precio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDtoResponse> actualizar(@Valid @RequestBody ProductoDtoRequest dto, @PathVariable Long id){
        return ResponseEntity.ok(productoService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
