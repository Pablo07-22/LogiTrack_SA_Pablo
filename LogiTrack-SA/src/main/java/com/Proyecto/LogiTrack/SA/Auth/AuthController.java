package com.Proyecto.LogiTrack.SA.Auth;

import com.Proyecto.LogiTrack.SA.Config.JwtService;
import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Repository.UsuarioRepository;
import com.Proyecto.LogiTrack.SA.Service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@Valid @RequestBody RegistroRequest dto) {
        usuarioService.registrarSolicitud(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Solicitud enviada. Espera la aprobación del administrador.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        List<Usuario> usuarios = usuarioRepository.findByUsername(request.username());
        if (usuarios.isEmpty()) {throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");}
        Usuario usuario = usuarios.get(0);
        if (!passwordEncoder.matches(request.password(), usuario.getPassword())) {throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");}
        if (usuario.getEstado() == Usuario.Estado.PENDIENTE) {throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tu solicitud todavía está pendiente de aprobación");}
        if (usuario.getEstado() != Usuario.Estado.ACTIVO) {throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tu cuenta no se encuentra activa");}
        String token = jwtService.generateToken(usuario.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
