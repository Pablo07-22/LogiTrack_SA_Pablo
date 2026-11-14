package com.Proyecto.LogiTrack.SA.Mapper;

import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import com.Proyecto.LogiTrack.SA.Dto.Response.AuditoriaDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaMapper {

    private final UsuarioMapper usuarioMapper;

    public AuditoriaMapper(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    public AuditoriaDtoResponse entityToDto(Auditoria auditoria){

        if (auditoria == null) return null;
        return new AuditoriaDtoResponse(auditoria.getId(),
                auditoria.getFecha(),
                auditoria.getTipoOperacion(),
                usuarioMapper.entityToResumenDto(auditoria.getUsuario()),
                auditoria.getEntidadAfectada(),
                auditoria.getIdRegistro(),
                auditoria.getValoresAnteriores(),
                auditoria.getValoresNuevos()
        );
    }
}