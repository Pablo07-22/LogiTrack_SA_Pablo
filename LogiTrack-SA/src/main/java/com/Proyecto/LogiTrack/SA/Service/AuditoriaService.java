package com.Proyecto.LogiTrack.SA.Service;

import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import com.Proyecto.LogiTrack.SA.Dto.Response.AuditoriaDtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditoriaService {

    List<AuditoriaDtoResponse> listar();

    AuditoriaDtoResponse buscarId(Long id);

    List<AuditoriaDtoResponse> buscarUsuario(Long usuarioId);

    List<AuditoriaDtoResponse> buscarTipo(Auditoria.TipoOperacion tipo);

    List<AuditoriaDtoResponse> buscarEntidad(String entidad);

    List<AuditoriaDtoResponse> buscarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    void registrar(String entidad, Long idRegistro, Auditoria.TipoOperacion tipo, Object valoresAnteriores, Object valoresNuevos);
}