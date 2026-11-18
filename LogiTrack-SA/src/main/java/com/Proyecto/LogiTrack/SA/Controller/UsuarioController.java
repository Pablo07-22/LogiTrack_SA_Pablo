package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Dto.Request.AdministradorDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.UsuarioResumenDtoResponse;
import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Service.UsuarioService;
import com.Proyecto.LogiTrack.SA.Dto.Request.UsuarioDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.UsuarioDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios, roles, estados y solicitudes de registro")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Registrar usuario", description = "Crea un usuario utilizando los datos recibidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<UsuarioDtoResponse> guardar(@Valid @RequestBody UsuarioDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios registrados.")
    @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente")
    public ResponseEntity<List<UsuarioDtoResponse>> listar() {
     return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID", description = "Consulta un usuario mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioDtoResponse> buscar(@Parameter(description = "ID del usuario", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarId(id));
    }

    @GetMapping("/username")
    @Operation(summary = "Buscar usuario por username", description = "Consulta un usuario utilizando su nombre de usuario.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<UsuarioDtoResponse> buscarUsername(@Parameter(description = "Nombre de usuario", example = "empleado01") @RequestParam String username) {
        return ResponseEntity.ok(usuarioService.buscarUsername(username));
    }

    @GetMapping("/rol")
    @Operation(summary = "Buscar usuarios por rol", description = "Filtra usuarios por ADMIN o EMPLEADO.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<UsuarioDtoResponse>> buscarRol(@Parameter(description = "Rol que se desea consultar", example = "EMPLEADO") @RequestParam Usuario.Rol rol) {
        return ResponseEntity.ok(usuarioService.buscarRol(rol));
    }

    @GetMapping("/estado")
    @Operation(summary = "Buscar usuarios por estado", description = "Filtra usuarios por PENDIENTE, ACTIVO o INACTIVO.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<List<UsuarioDtoResponse>> buscarEstado(@Parameter(description = "Estado que se desea consultar", example = "PENDIENTE") @RequestParam Usuario.Estado estado) {
        return ResponseEntity.ok(usuarioService.buscarEstado(estado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioDtoResponse> actualizar(@Valid @RequestBody UsuarioDtoRequest dto, @Parameter(description = "ID del usuario que se actualizará", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del usuario", example = "1") @PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/solicitudes/pendientes")
    @Operation(summary = "Listar solicitudes pendientes", description = "Obtiene los usuarios que están pendientes de aprobación.")
    @ApiResponse(responseCode = "200", description = "Solicitudes obtenidas correctamente")
    public ResponseEntity<List<UsuarioDtoResponse>> listarPendientes() {
        return ResponseEntity.ok(usuarioService.listarPendientes());
    }

    @PutMapping("/{id}/aprobar")
    @Operation(summary = "Aprobar solicitud", description = "Aprueba la solicitud de registro de un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud aprobada correctamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<UsuarioDtoResponse> aprobarSolicitud(@Parameter(description = "ID del usuario pendiente", example = "2") @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.aprobarSolicitud(id));
    }

    @DeleteMapping("/{id}/rechazar")
    @Operation(summary = "Rechazar solicitud", description = "Rechaza y elimina la solicitud de registro de un usuario pendiente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Solicitud rechazada correctamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<Void> rechazarSolicitud(@Parameter(description = "ID del usuario pendiente", example = "2") @PathVariable Long id) {
        usuarioService.rechazarSolicitud(id);
        return ResponseEntity.noContent().build();
    }

     @PostMapping("/administradores")
     @Operation(
            summary = "Crear un administrador",
            description = "Permite al administrador autenticado crear otra cuenta ADMIN activa. La contraseña se cifra en el backend."
     )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Administrador creado correctamente"),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos o username duplicado"),
            @ApiResponse(responseCode = "401",
                    description = "No hay autenticación válida"),
            @ApiResponse(responseCode = "403",
                    description = "El usuario autenticado no tiene permisos de administrador")
    })
    public ResponseEntity<UsuarioDtoResponse> crearAdministrador(@Valid @RequestBody AdministradorDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearAdministrador(dto));
    }
    @GetMapping("/activos")
    @Operation(
            summary = "Listar usuarios activos para movimientos",
            description = "Devuelve únicamente ID, username y rol de los usuarios activos."
    )
    public ResponseEntity<List<UsuarioResumenDtoResponse>> listarActivosParaMovimiento() {
        return ResponseEntity.ok(usuarioService.listarActivosParaMovimiento());
    }

}
