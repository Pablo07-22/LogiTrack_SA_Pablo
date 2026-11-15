package com.Proyecto.LogiTrack.SA.Service;

import com.Proyecto.LogiTrack.SA.Dto.Request.CategoriaProductoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.CategoriaProductoDtoResponse;

import java.util.List;

public interface CategoriaProductoService {

    CategoriaProductoDtoResponse guardar(CategoriaProductoDtoRequest dto);

    CategoriaProductoDtoResponse actualizar(CategoriaProductoDtoRequest dto, Long id);

    void eliminar(Long id);

    List<CategoriaProductoDtoResponse> listar();

    CategoriaProductoDtoResponse buscarId(Long id);

}