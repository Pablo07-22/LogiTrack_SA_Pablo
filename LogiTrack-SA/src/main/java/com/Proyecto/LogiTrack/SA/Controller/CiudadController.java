package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.CiudadService;
import com.Proyecto.LogiTrack.SA.Dto.Request.CiudadDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.CiudadDtoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudad")
@RequiredArgsConstructor
public class CiudadController {

    private final CiudadService ciudadService;

    @PostMapping
    public ResponseEntity<CiudadDtoResponse> guardar(@Valid @RequestBody CiudadDtoRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ciudadService.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<CiudadDtoResponse>> listar(){
        return ResponseEntity.ok(ciudadService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CiudadDtoResponse> buscar(@PathVariable Long id){
        return ResponseEntity.ok(ciudadService.buscarId(id));
    }

    @GetMapping("/nombre")
    public ResponseEntity<CiudadDtoResponse> buscarNombre(@RequestParam String nombre){
        return ResponseEntity.ok(ciudadService.buscarNombre(nombre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CiudadDtoResponse> actualizar(@Valid @RequestBody CiudadDtoRequest dto, @PathVariable Long id){
        return ResponseEntity.ok(ciudadService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        ciudadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
