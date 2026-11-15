package com.Proyecto.LogiTrack.SA.Service;

import com.Proyecto.LogiTrack.SA.Model.Movimiento;
import com.Proyecto.LogiTrack.SA.Dto.Request.MovimientoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.MovimientoDtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoService {

    MovimientoDtoResponse guardar(MovimientoDtoRequest dto);

    void eliminar(Long id);

    List<MovimientoDtoResponse> listar();

    MovimientoDtoResponse buscarId(Long id);

    List<MovimientoDtoResponse> buscarTipo(Movimiento.TipoMovimiento tipo);

    List<MovimientoDtoResponse> buscarUsuario(Long usuarioId);

    List<MovimientoDtoResponse> buscarBodegaOrigen(Long bodegaId);

    List<MovimientoDtoResponse> buscarBodegaDestino(Long bodegaId);

    List<MovimientoDtoResponse> buscarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

}