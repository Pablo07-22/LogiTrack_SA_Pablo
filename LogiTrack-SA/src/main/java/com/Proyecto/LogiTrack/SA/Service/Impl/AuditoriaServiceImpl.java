package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Mapper.AuditoriaMapper;
import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import com.Proyecto.LogiTrack.SA.Repository.AuditoriaRepository;
import com.Proyecto.LogiTrack.SA.Repository.UsuarioRepository;
import com.Proyecto.LogiTrack.SA.Service.AuditoriaService;
import com.Proyecto.LogiTrack.SA.Dto.Response.AuditoriaDtoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.Proyecto.LogiTrack.SA.Exception.BussinesRuleException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaMapper auditoriaMapper;
    private final AuditoriaRepository auditoriaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<AuditoriaDtoResponse> listar(){
        return auditoriaRepository.findAll().stream().map(auditoriaMapper::entityToDto).toList();
    }

    @Override
    public AuditoriaDtoResponse buscarId(Long id) {
        Auditoria auditoria = auditoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe una auditoría con el ID indicado"));
        return auditoriaMapper.entityToDto(auditoria);
    }

    @Override
    public List<AuditoriaDtoResponse> buscarUsuario(Long usuarioId){
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new EntityNotFoundException("El usuario indicado no existe");
        }
        return auditoriaRepository.findByUsuario_Id(usuarioId).stream().map(auditoriaMapper::entityToDto).toList();
    }

    @Override
    public List<AuditoriaDtoResponse> buscarTipo(Auditoria.TipoOperacion tipo){
        return auditoriaRepository.findByTipoOperacion(tipo).stream().map(auditoriaMapper::entityToDto).toList();
    }

    @Override
    public List<AuditoriaDtoResponse> buscarEntidad(String entidad){
        return auditoriaRepository.findByEntidadAfectada(entidad).stream().map(auditoriaMapper::entityToDto).toList();
    }

    @Override
    public List<AuditoriaDtoResponse> buscarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        if (fechaInicio.isAfter(fechaFin)) { throw new BussinesRuleException("La fecha inicial no puede ser posterior a la fecha final");}
        return auditoriaRepository.findByFechaBetween(fechaInicio, fechaFin).stream().map(auditoriaMapper::entityToDto).toList();
    }
}