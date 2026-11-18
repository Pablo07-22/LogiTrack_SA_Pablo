package com.Proyecto.LogiTrack.SA.Dto.Response;

import com.Proyecto.LogiTrack.SA.Model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UsuarioResumenDtoResponse", description = "Informacion resumida de un usuario.")
public record UsuarioResumenDtoResponse(

        @Schema(description = "Identificador del usuario", example = "1")
        Long id,

        @Schema(description = "Nombre de usuario", example = "admin")
        String username,

        @Schema(description = "Rol del usuario", example = "ADMIN")
        Usuario.Rol rol

) {}
