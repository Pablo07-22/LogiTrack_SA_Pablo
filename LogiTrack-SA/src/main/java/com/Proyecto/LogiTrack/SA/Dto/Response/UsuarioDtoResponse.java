package com.Proyecto.LogiTrack.SA.Dto.Response;

import com.Proyecto.LogiTrack.SA.Model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "UsuarioDtoResponse", description = "Informacion de un usuario sin exponer sus credenciales.")
public record UsuarioDtoResponse(

        @Schema(description = "Identificador del usuario", example = "1")
        Long id,

        @Schema(description = "Nombre de usuario", example = "empleado01")
        String username,

        @Schema(description = "Rol asignado al usuario", example = "EMPLEADO")
        Usuario.Rol rol,

        @Schema(description = "Estado de la cuenta", example = "ACTIVO")
        Usuario.Estado estado,

        @Schema(description = "Fecha de creación del usuario", example = "2026-11-17T10:30:00")
        LocalDateTime fechaCreacion

) {}
