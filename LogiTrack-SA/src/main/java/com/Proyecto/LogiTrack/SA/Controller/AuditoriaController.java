package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import com.Proyecto.LogiTrack.SA.Service.AuditoriaService;
import com.Proyecto.LogiTrack.SA.Dto.Response.AuditoriaDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    public ResponseEntity<List<AuditoriaDtoResponse>> listar(){
        return ResponseEntity.ok(auditoriaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaDtoResponse> buscar( @PathVariable Long id){
        return ResponseEntity.ok(auditoriaService.buscarId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AuditoriaDtoResponse>> buscarUsuario(@PathVariable Long usuarioId){
        return ResponseEntity.ok(auditoriaService.buscarUsuario(usuarioId));
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<AuditoriaDtoResponse>> buscarTipo(@RequestParam Auditoria.TipoOperacion tipo){
        return ResponseEntity.ok(auditoriaService.buscarTipo(tipo));
    }

    @GetMapping("/entidad")
    public ResponseEntity<List<AuditoriaDtoResponse>> buscarEntidad(@RequestParam String entidad){
        return ResponseEntity.ok(auditoriaService.buscarEntidad(entidad));
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<AuditoriaDtoResponse>> buscarFechas(@RequestParam LocalDateTime fechaInicio, @RequestParam LocalDateTime fechaFin){
        return ResponseEntity.ok(auditoriaService.buscarFechas(fechaInicio, fechaFin));
    }
}
