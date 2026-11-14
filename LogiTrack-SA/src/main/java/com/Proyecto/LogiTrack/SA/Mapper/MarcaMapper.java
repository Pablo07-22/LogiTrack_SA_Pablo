package com.Proyecto.LogiTrack.SA.Mapper;

import com.Proyecto.LogiTrack.SA.Model.Marca;
import com.Proyecto.LogiTrack.SA.Dto.Request.MarcaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.MarcaDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class MarcaMapper {

    public MarcaDtoResponse entityToDto(Marca marca){
        if (marca == null) return null;
        return new MarcaDtoResponse(marca.getId(),
                marca.getNombreMarca()
        );
    }

    public Marca DtoToEntity(MarcaDtoRequest dto){

        if (dto == null) return null;
        Marca marca = new Marca();
        marca.setNombreMarca(dto.nombreMarca());
        return marca;
    }

    public void updateEntityFromDto(Marca marca, MarcaDtoRequest dto){

        if (dto == null || marca == null) return;
        marca.setNombreMarca(dto.nombreMarca());
    }
}