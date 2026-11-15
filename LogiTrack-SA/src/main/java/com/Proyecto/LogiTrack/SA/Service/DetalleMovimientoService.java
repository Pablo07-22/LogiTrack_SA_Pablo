package com.Proyecto.LogiTrack.SA.Service;

import com.Proyecto.LogiTrack.SA.Dto.Response.DetalleMovimientoDtoResponse;

import java.util.List;

public interface DetalleMovimientoService {

    void eliminar(Long id);

    List<DetalleMovimientoDtoResponse> listar();

    DetalleMovimientoDtoResponse buscarId(Long id);

    List<DetalleMovimientoDtoResponse> buscarMovimiento(Long movimientoId);

    List<DetalleMovimientoDtoResponse> buscarProducto(Long productoId);

}