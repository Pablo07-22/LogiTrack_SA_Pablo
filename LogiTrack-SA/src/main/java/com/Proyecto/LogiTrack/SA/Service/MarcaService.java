package com.Proyecto.LogiTrack.SA.Service;

import com.Proyecto.LogiTrack.SA.Dto.Request.MarcaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.MarcaDtoResponse;

import java.util.List;

public interface MarcaService {

    MarcaDtoResponse guardar(MarcaDtoRequest dto);

    MarcaDtoResponse actualizar(MarcaDtoRequest dto, Long id);

    void eliminar(Long id);

    List<MarcaDtoResponse> listar();

    MarcaDtoResponse buscarId(Long id);

}