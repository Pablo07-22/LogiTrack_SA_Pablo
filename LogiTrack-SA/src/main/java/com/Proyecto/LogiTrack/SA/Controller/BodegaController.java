package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.BodegaService;
import com.Proyecto.LogiTrack.SA.Dto.Request.BodegaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.BodegaDtoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bodega")
@RequiredArgsConstructor
public class BodegaController {

    private final BodegaService bodegaService;

    @PostMapping
    public ResponseEntity<BodegaDtoResponse> guardar(@Valid @RequestBody BodegaDtoRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(bodegaService.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<BodegaDtoResponse>> listar(){
        return ResponseEntity.ok(bodegaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BodegaDtoResponse> buscar(@PathVariable Long id){
        return ResponseEntity.ok(bodegaService.buscarId(id));
    }

    @GetMapping("/ciudad/{ciudadId}")
    public ResponseEntity<List<BodegaDtoResponse>> buscarCiudad(@PathVariable Long ciudadId){
        return ResponseEntity.ok(bodegaService.buscarCiudad(ciudadId));
    }

    @GetMapping("/encargado/{encargadoId}")
    public ResponseEntity<List<BodegaDtoResponse>> buscarEncargado(@PathVariable Long encargadoId){
        return ResponseEntity.ok(bodegaService.buscarEncargado(encargadoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BodegaDtoResponse> actualizar(@Valid @RequestBody BodegaDtoRequest dto, @PathVariable Long id){
        return ResponseEntity.ok(bodegaService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        bodegaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
