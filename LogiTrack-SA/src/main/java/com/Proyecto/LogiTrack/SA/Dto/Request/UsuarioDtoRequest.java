package com.Proyecto.LogiTrack.SA.Dto.Request;

import com.Proyecto.LogiTrack.SA.Model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "UsuarioDtoRequest", description = "Datos para registrar o actualizar un usuario del sistema.")
public record UsuarioDtoRequest(

        @Schema(description = "Nombre de usuario único", example = "empleado01", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "El username no puede estar vacío")
        @Size(max = 50, message = "El username no puede superar los 50 caracteres")
        String username,

        @Schema(description = "Contraseña del usuario. Se almacenará cifrada en el backend", example = "ClaveSegura123", minLength = 8, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
        String password,

        @Schema(description = "Rol asignado al usuario", example = "EMPLEADO", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "El rol no puede estar nulo")
        Usuario.Rol rol,

        @Schema(description = "Estado de la cuenta", example = "PENDIENTE", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "El estado no puede estar nulo")
        Usuario.Estado estado

) {}
