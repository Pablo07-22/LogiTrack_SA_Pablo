package com.Proyecto.LogiTrack.SA.Dto.Response;

import com.Proyecto.LogiTrack.SA.Model.Usuario;

import java.time.LocalDateTime;

public record UsuarioDtoResponse(
        Long id,
        String username,
        Usuario.Rol rol,
        Usuario.Estado estado,
        LocalDateTime fechaCreacion
) {}
