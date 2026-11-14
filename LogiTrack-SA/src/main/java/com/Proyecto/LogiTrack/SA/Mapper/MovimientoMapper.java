package com.Proyecto.LogiTrack.SA.Mapper;

import com.Proyecto.LogiTrack.SA.Model.Movimiento;
import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Model.Bodega;
import com.Proyecto.LogiTrack.SA.Dto.Request.MovimientoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.MovimientoDtoResponse;
import com.Proyecto.LogiTrack.SA.Dto.Response.MovimientoResumenDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapper {

    private final UsuarioMapper usuarioMapper;
    private final BodegaMapper bodegaMapper;

    public MovimientoMapper(UsuarioMapper usuarioMapper, BodegaMapper bodegaMapper){
        this.usuarioMapper = usuarioMapper;
        this.bodegaMapper = bodegaMapper;
    }

    public MovimientoDtoResponse entityToDto(Movimiento movimiento){

        if (movimiento == null) return null;
        return new MovimientoDtoResponse(movimiento.getId(),
                movimiento.getFecha(),
                movimiento.getTipoMovimiento(),
                usuarioMapper.entityToResumenDto(movimiento.getUsuarioResponsable()),
                bodegaMapper.entityToResumenDto(movimiento.getBodegaOrigen()),
                bodegaMapper.entityToResumenDto(movimiento.getBodegaDestino()),
                movimiento.getObservacion()
        );
    }

    public MovimientoResumenDtoResponse entityToResumenDto(Movimiento movimiento){

        if (movimiento == null) return null;
        return new MovimientoResumenDtoResponse(movimiento.getId(),
                movimiento.getFecha(),
                movimiento.getTipoMovimiento()
        );
    }

    public Movimiento DtoToEntity(MovimientoDtoRequest dto,Usuario usuarioResponsable, Bodega bodegaOrigen, Bodega bodegaDestino) {

        if (dto == null) return null;
        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento(dto.tipoMovimiento());
        movimiento.setUsuarioResponsable(usuarioResponsable);
        movimiento.setBodegaOrigen(bodegaOrigen);
        movimiento.setBodegaDestino(bodegaDestino);
        movimiento.setObservacion(dto.observacion());
        return movimiento;
    }

    public void updateEntityFromDto(Movimiento movimiento, MovimientoDtoRequest dto, Usuario usuarioResponsable, Bodega bodegaOrigen, Bodega bodegaDestino){

        if (dto == null || movimiento == null) return;
        movimiento.setTipoMovimiento(dto.tipoMovimiento());
        movimiento.setUsuarioResponsable(usuarioResponsable);
        movimiento.setBodegaOrigen(bodegaOrigen);
        movimiento.setBodegaDestino(bodegaDestino);
        movimiento.setObservacion(dto.observacion());
    }
}