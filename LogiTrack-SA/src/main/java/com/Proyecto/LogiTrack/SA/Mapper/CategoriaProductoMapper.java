package com.Proyecto.LogiTrack.SA.Mapper;

import com.Proyecto.LogiTrack.SA.Model.CategoriaProducto;
import com.Proyecto.LogiTrack.SA.Dto.Request.CategoriaProductoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.CategoriaProductoDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoriaProductoMapper {

    public CategoriaProductoDtoResponse entityToDto(CategoriaProducto categoria){

        if (categoria == null) return null;
        return new CategoriaProductoDtoResponse(categoria.getId(),
                categoria.getTipoCategoria()
        );
    }

    public CategoriaProducto DtoToEntity(CategoriaProductoDtoRequest dto){

        if (dto == null) return null;
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setTipoCategoria(dto.tipoCategoria());
        return categoria;
    }

    public void updateEntityFromDto(CategoriaProducto categoria, CategoriaProductoDtoRequest dto){

        if (dto == null || categoria == null) return;
        categoria.setTipoCategoria(dto.tipoCategoria());
    }
}