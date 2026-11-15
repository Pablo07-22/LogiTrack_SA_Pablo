package com.Proyecto.LogiTrack.SA.Service;

import com.Proyecto.LogiTrack.SA.Dto.Request.UsuarioDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.UsuarioDtoResponse;
import com.Proyecto.LogiTrack.SA.Model.Usuario;

import java.util.List;

public interface UsuarioService {

    UsuarioDtoResponse guardar(UsuarioDtoRequest dto);

    UsuarioDtoResponse actualizar(UsuarioDtoRequest dto, Long id);

    void eliminar(Long id);

    List<UsuarioDtoResponse> listar();

    UsuarioDtoResponse buscarId(Long id);

    UsuarioDtoResponse buscarUsername(String username);

    List<UsuarioDtoResponse> buscarRol(Usuario.Rol rol);

    List<UsuarioDtoResponse> buscarEstado(Usuario.Estado estado);

}