package com.Proyecto.LogiTrack.SA.Service;

import com.Proyecto.LogiTrack.SA.Dto.Request.BodegaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.BodegaDtoResponse;

import java.util.List;

public interface BodegaService {

    BodegaDtoResponse guardar(BodegaDtoRequest dto);

    BodegaDtoResponse actualizar(BodegaDtoRequest dto, Long id);

    void eliminar(Long id);

    List<BodegaDtoResponse> listar();

    BodegaDtoResponse buscarId(Long id);

    List<BodegaDtoResponse> buscarCiudad(Long ciudadId);

    List<BodegaDtoResponse> buscarEncargado(Long encargadoId);

}