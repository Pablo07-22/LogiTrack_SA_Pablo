package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Model.Movimiento;
import com.Proyecto.LogiTrack.SA.Service.MovimientoService;
import com.Proyecto.LogiTrack.SA.Dto.Request.MovimientoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.MovimientoDtoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movimiento")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoDtoResponse> guardar(@Valid @RequestBody MovimientoDtoRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<MovimientoDtoResponse>> listar(){
        return ResponseEntity.ok(movimientoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDtoResponse> buscar(@PathVariable Long id){
        return ResponseEntity.ok(movimientoService.buscarId(id));
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<MovimientoDtoResponse>> buscarTipo(@RequestParam Movimiento.TipoMovimiento tipo){
        return ResponseEntity.ok(movimientoService.buscarTipo(tipo));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MovimientoDtoResponse>> buscarUsuario(@PathVariable Long usuarioId){
        return ResponseEntity.ok(movimientoService.buscarUsuario(usuarioId));
    }

    @GetMapping("/bodega-origen/{bodegaId}")
    public ResponseEntity<List<MovimientoDtoResponse>> buscarBodegaOrigen(@PathVariable Long bodegaId){
        return ResponseEntity.ok(movimientoService.buscarBodegaOrigen(bodegaId));
    }

    @GetMapping("/bodega-destino/{bodegaId}")
    public ResponseEntity<List<MovimientoDtoResponse>> buscarBodegaDestino(@PathVariable Long bodegaId){
        return ResponseEntity.ok(movimientoService.buscarBodegaDestino(bodegaId));
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<MovimientoDtoResponse>> buscarFechas(@RequestParam LocalDateTime fechaInicio, @RequestParam LocalDateTime fechaFin){
        return ResponseEntity.ok(movimientoService.buscarFechas(fechaInicio, fechaFin));
    }
}
