package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.MarcaService;
import com.Proyecto.LogiTrack.SA.Dto.Request.MarcaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.MarcaDtoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marca")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @PostMapping
    public ResponseEntity<MarcaDtoResponse> guardar(@Valid @RequestBody MarcaDtoRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<MarcaDtoResponse>> listar(){
        return ResponseEntity.ok(marcaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDtoResponse> buscar(@PathVariable Long id){
        return ResponseEntity.ok(marcaService.buscarId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaDtoResponse> actualizar(@Valid @RequestBody MarcaDtoRequest dto, @PathVariable Long id){
        return ResponseEntity.ok(marcaService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        marcaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
