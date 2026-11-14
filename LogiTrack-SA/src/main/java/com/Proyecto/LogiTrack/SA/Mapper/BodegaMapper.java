package com.Proyecto.LogiTrack.SA.Mapper;

import com.Proyecto.LogiTrack.SA.Model.Bodega;
import com.Proyecto.LogiTrack.SA.Model.Ciudad;
import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Dto.Request.BodegaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.BodegaDtoResponse;
import com.Proyecto.LogiTrack.SA.Dto.Response.BodegaResumenDtoResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BodegaMapper {

    private final CiudadMapper ciudadMapper;
    private final UsuarioMapper usuarioMapper;

    public BodegaMapper(CiudadMapper ciudadMapper, UsuarioMapper usuarioMapper){
        this.ciudadMapper = ciudadMapper;
        this.usuarioMapper = usuarioMapper;
    }

    public BodegaDtoResponse entityToDto(Bodega bodega){

        if (bodega == null) return null;
        return new BodegaDtoResponse(bodega.getId(),
                bodega.getNombre(),
                ciudadMapper.entityToDto(bodega.getCiudad()),
                usuarioMapper.entityToResumenDto(bodega.getEncargado()),
                bodega.getCapacidad(),
                bodega.getFechaCreacion()
        );
    }

    public BodegaResumenDtoResponse entityToResumenDto(Bodega bodega){

        if (bodega == null) return null;
        return new BodegaResumenDtoResponse( bodega.getId(),
                bodega.getNombre()
        );
    }

    public Bodega DtoToEntity(BodegaDtoRequest dto, Ciudad ciudad, Usuario encargado){

        if (dto == null) return null;
        Bodega bodega = new Bodega();
        bodega.setNombre(dto.nombre());
        bodega.setCiudad(ciudad);
        bodega.setEncargado(encargado);
        bodega.setCapacidad(dto.capacidad());
        return bodega;
    }

    public void updateEntityFromDto(Bodega bodega, BodegaDtoRequest dto, Ciudad ciudad, Usuario encargado){

        if (dto == null || bodega == null) return;
        bodega.setNombre(dto.nombre());
        bodega.setCiudad(ciudad);
        bodega.setEncargado(encargado);
        bodega.setCapacidad(dto.capacidad());
    }
}