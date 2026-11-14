package com.Proyecto.LogiTrack.SA.Mapper;

import com.Proyecto.LogiTrack.SA.Model.Ciudad;
import com.Proyecto.LogiTrack.SA.Dto.Request.CiudadDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.CiudadDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class CiudadMapper {

    public CiudadDtoResponse entityToDto(Ciudad ciudad){

        if (ciudad == null) return null;
        return new CiudadDtoResponse(ciudad.getIdCiudad(),
                ciudad.getNombreCiudad()
        );
    }

    public Ciudad DtoToEntity(CiudadDtoRequest dto){

        if (dto == null) return null;
        Ciudad ciudad = new Ciudad();
        ciudad.setNombreCiudad(dto.nombreCiudad());

        return ciudad;
    }

    public void updateEntityFromDto(Ciudad ciudad, CiudadDtoRequest dto){

        if (dto == null || ciudad == null) return;
        ciudad.setNombreCiudad(dto.nombreCiudad());
    }
}