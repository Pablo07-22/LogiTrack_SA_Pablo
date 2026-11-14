package com.Proyecto.LogiTrack.SA.Mapper;

import com.Proyecto.LogiTrack.SA.Model.InventarioBodega;
import com.Proyecto.LogiTrack.SA.Model.Bodega;
import com.Proyecto.LogiTrack.SA.Model.Producto;
import com.Proyecto.LogiTrack.SA.Dto.Request.InventarioBodegaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.InventarioBodegaDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class InventarioBodegaMapper {

    private final BodegaMapper bodegaMapper;
    private final ProductoMapper productoMapper;

    public InventarioBodegaMapper(BodegaMapper bodegaMapper, ProductoMapper productoMapper){
        this.bodegaMapper = bodegaMapper;
        this.productoMapper = productoMapper;
    }

    public InventarioBodegaDtoResponse entityToDto(InventarioBodega inventario){

        if (inventario == null) return null;
        return new InventarioBodegaDtoResponse(inventario.getId(),
                bodegaMapper.entityToResumenDto(inventario.getBodega()),
                productoMapper.entityToDto(inventario.getProducto()),
                inventario.getStock()
        );
    }

    public InventarioBodega DtoToEntity(InventarioBodegaDtoRequest dto, Bodega bodega, Producto producto){

        if (dto == null) return null;
        InventarioBodega inventario = new InventarioBodega();
        inventario.setBodega(bodega);
        inventario.setProducto(producto);
        inventario.setStock(dto.stock());
        return inventario;
    }

    public void updateEntityFromDto(InventarioBodega inventario, InventarioBodegaDtoRequest dto, Bodega bodega, Producto producto){

        if (dto == null || inventario == null) return;
        inventario.setBodega(bodega);
        inventario.setProducto(producto);
        inventario.setStock(dto.stock());
    }
}