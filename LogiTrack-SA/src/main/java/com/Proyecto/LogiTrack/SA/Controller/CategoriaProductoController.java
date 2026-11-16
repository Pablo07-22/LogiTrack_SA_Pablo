package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.CategoriaProductoService;
import com.Proyecto.LogiTrack.SA.Dto.Request.CategoriaProductoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.CategoriaProductoDtoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria-producto")
@RequiredArgsConstructor
public class CategoriaProductoController {

    private final CategoriaProductoService categoriaProductoService;

    @PostMapping
    public ResponseEntity<CategoriaProductoDtoResponse> guardar(@Valid @RequestBody CategoriaProductoDtoRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaProductoService.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaProductoDtoResponse>> listar(){
        return ResponseEntity.ok(categoriaProductoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProductoDtoResponse> buscar(@PathVariable Long id){
        return ResponseEntity.ok(categoriaProductoService.buscarId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProductoDtoResponse> actualizar(@Valid @RequestBody CategoriaProductoDtoRequest dto, @PathVariable Long id){
        return ResponseEntity.ok(categoriaProductoService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        categoriaProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
