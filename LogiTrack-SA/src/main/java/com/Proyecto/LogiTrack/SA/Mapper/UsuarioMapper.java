package com.Proyecto.LogiTrack.SA.Mapper;

import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Dto.Request.UsuarioDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.UsuarioDtoResponse;
import com.Proyecto.LogiTrack.SA.Dto.Response.UsuarioResumenDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDtoResponse entityToDto(Usuario usuario){

        if (usuario == null) return null;
        return new UsuarioDtoResponse(usuario.getId(),
                usuario.getUsername(),
                usuario.getRol(),
                usuario.getEstado(),
                usuario.getFechaCreacion()
        );
    }

    public UsuarioResumenDtoResponse entityToResumenDto(Usuario usuario){

        if (usuario == null) return null;
        return new UsuarioResumenDtoResponse(usuario.getId(),
                usuario.getUsername(),
                usuario.getRol()
        );
    }

    public Usuario DtoToEntity(UsuarioDtoRequest dto, String password){

        if (dto == null) return null;
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setPassword(password);
        usuario.setRol(dto.rol());
        usuario.setEstado(dto.estado());
        return usuario;
    }

    public void updateEntityFromDto(Usuario usuario, UsuarioDtoRequest dto, String password){

        if (dto == null || usuario == null) return;
        usuario.setUsername(dto.username());
        if (password != null && !password.isBlank()) {
            usuario.setPassword(password);
        }
        if (dto.rol() != null) {
            usuario.setRol(dto.rol());
        }
        if (dto.estado() != null) {
            usuario.setEstado(dto.estado());
        }
    }
}