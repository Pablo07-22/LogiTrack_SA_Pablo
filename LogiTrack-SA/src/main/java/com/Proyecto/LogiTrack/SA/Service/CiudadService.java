package com.Proyecto.LogiTrack.SA.Service;

import com.Proyecto.LogiTrack.SA.Dto.Request.CiudadDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.CiudadDtoResponse;

import java.util.List;

public interface CiudadService {

    CiudadDtoResponse guardar(CiudadDtoRequest dto);

    CiudadDtoResponse actualizar(CiudadDtoRequest dto, Long id);

    void eliminar(Long id);

    List<CiudadDtoResponse> listar();

    CiudadDtoResponse buscarId(Long id);

    CiudadDtoResponse buscarNombre(String nombre);

}