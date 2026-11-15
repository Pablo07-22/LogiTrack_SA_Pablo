package com.Proyecto.LogiTrack.SA.Service;

import com.Proyecto.LogiTrack.SA.Dto.Request.InventarioBodegaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.InventarioBodegaDtoResponse;

import java.util.List;

public interface InventarioBodegaService {

    InventarioBodegaDtoResponse guardar(InventarioBodegaDtoRequest dto);

    InventarioBodegaDtoResponse actualizar(InventarioBodegaDtoRequest dto, Long id);

    void eliminar(Long id);

    List<InventarioBodegaDtoResponse> listar();

    InventarioBodegaDtoResponse buscarId(Long id);

    List<InventarioBodegaDtoResponse> buscarBodega(Long bodegaId);

    List<InventarioBodegaDtoResponse> buscarProducto(Long productoId);

    List<InventarioBodegaDtoResponse> buscarStockBajo(Integer stock);

}