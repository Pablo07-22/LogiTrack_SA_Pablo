package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Service.UsuarioService;
import com.Proyecto.LogiTrack.SA.Dto.Request.UsuarioDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.UsuarioDtoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDtoResponse> guardar(@Valid @RequestBody UsuarioDtoRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDtoResponse>> listar(){
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> buscar(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarId(id));
    }

    @GetMapping("/username")
    public ResponseEntity<UsuarioDtoResponse> buscarUsername(@RequestParam String username){
        return ResponseEntity.ok(usuarioService.buscarUsername(username));
    }

    @GetMapping("/rol")
    public ResponseEntity<List<UsuarioDtoResponse>> buscarRol(@RequestParam Usuario.Rol rol){
        return ResponseEntity.ok(usuarioService.buscarRol(rol));
    }

    @GetMapping("/estado")
    public ResponseEntity<List<UsuarioDtoResponse>> buscarEstado(@RequestParam Usuario.Estado estado){
        return ResponseEntity.ok(usuarioService.buscarEstado(estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> actualizar(@Valid @RequestBody UsuarioDtoRequest dto, @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
