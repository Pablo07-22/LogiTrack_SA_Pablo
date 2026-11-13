package com.Proyecto.LogiTrack.SA.Dto.Response;

import com.Proyecto.LogiTrack.SA.Model.Usuario;

public record UsuarioResumenDtoResponse(
        Long id,
        String username,
        Usuario.Rol rol
) {}
