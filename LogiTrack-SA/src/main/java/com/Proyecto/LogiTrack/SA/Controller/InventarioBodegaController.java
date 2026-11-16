package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.InventarioBodegaService;
import com.Proyecto.LogiTrack.SA.Dto.Request.InventarioBodegaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.InventarioBodegaDtoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario-bodega")
@RequiredArgsConstructor
public class InventarioBodegaController {

    private final InventarioBodegaService inventarioBodegaService;

    @PostMapping
    public ResponseEntity<InventarioBodegaDtoResponse> guardar(@Valid @RequestBody InventarioBodegaDtoRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioBodegaService.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<InventarioBodegaDtoResponse>> listar(){
        return ResponseEntity.ok(inventarioBodegaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioBodegaDtoResponse> buscar(@PathVariable Long id){
        return ResponseEntity.ok(inventarioBodegaService.buscarId(id));
    }

    @GetMapping("/bodega/{bodegaId}")
    public ResponseEntity<List<InventarioBodegaDtoResponse>> buscarBodega(@PathVariable Long bodegaId){
        return ResponseEntity.ok(inventarioBodegaService.buscarBodega(bodegaId));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<InventarioBodegaDtoResponse>> buscarProducto(@PathVariable Long productoId){
        return ResponseEntity.ok(inventarioBodegaService.buscarProducto(productoId));
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<InventarioBodegaDtoResponse>> buscarStockBajo(@RequestParam Integer stockMinimo){
        return ResponseEntity.ok(inventarioBodegaService.buscarStockBajo(stockMinimo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioBodegaDtoResponse> actualizar(@Valid @RequestBody InventarioBodegaDtoRequest dto, @PathVariable Long id){
        return ResponseEntity.ok(inventarioBodegaService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        inventarioBodegaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
