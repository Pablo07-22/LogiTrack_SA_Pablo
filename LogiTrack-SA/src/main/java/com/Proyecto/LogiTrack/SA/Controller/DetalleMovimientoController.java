
package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.DetalleMovimientoService;
import com.Proyecto.LogiTrack.SA.Dto.Response.DetalleMovimientoDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-movimiento")
@RequiredArgsConstructor
public class DetalleMovimientoController {

    private final DetalleMovimientoService detalleMovimientoService;

    @GetMapping
    public ResponseEntity<List<DetalleMovimientoDtoResponse>> listar(){
        return ResponseEntity.ok(detalleMovimientoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleMovimientoDtoResponse> buscar(@PathVariable Long id){
        return ResponseEntity.ok(detalleMovimientoService.buscarId(id));
    }

    @GetMapping("/movimiento/{movimientoId}")
    public ResponseEntity<List<DetalleMovimientoDtoResponse>> buscarMovimiento(@PathVariable Long movimientoId){
        return ResponseEntity.ok(detalleMovimientoService.buscarMovimiento(movimientoId));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<DetalleMovimientoDtoResponse>> buscarProducto(@PathVariable Long productoId){
        return ResponseEntity.ok(detalleMovimientoService.buscarProducto(productoId));
    }
}
