package com.Proyecto.LogiTrack.SA.Mapper;

import com.Proyecto.LogiTrack.SA.Model.DetalleMovimiento;
import com.Proyecto.LogiTrack.SA.Model.Movimiento;
import com.Proyecto.LogiTrack.SA.Model.Producto;
import com.Proyecto.LogiTrack.SA.Dto.Request.DetalleMovimientoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.DetalleMovimientoDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class DetalleMovimientoMapper {

    private final MovimientoMapper movimientoMapper;
    private final ProductoMapper productoMapper;

    public DetalleMovimientoMapper(MovimientoMapper movimientoMapper, ProductoMapper productoMapper){
        this.movimientoMapper = movimientoMapper;
        this.productoMapper = productoMapper;
    }

    public DetalleMovimientoDtoResponse entityToDto(DetalleMovimiento detalle) {

        if (detalle == null) return null;
        return new DetalleMovimientoDtoResponse(detalle.getId(),
                movimientoMapper.entityToResumenDto(detalle.getMovimiento()),
                productoMapper.entityToDto(detalle.getProducto()),
                detalle.getCantidad()
        );
    }

    public DetalleMovimiento DtoToEntity(DetalleMovimientoDtoRequest dto, Movimiento movimiento, Producto producto){

        if (dto == null) return null;
        DetalleMovimiento detalle = new DetalleMovimiento();
        detalle.setMovimiento(movimiento);
        detalle.setProducto(producto);
        detalle.setCantidad(dto.cantidad());
        return detalle;
    }

    public void updateEntityFromDto(DetalleMovimiento detalle, DetalleMovimientoDtoRequest dto, Movimiento movimiento, Producto producto){

        if (dto == null || detalle == null) return;
        detalle.setMovimiento(movimiento);
        detalle.setProducto(producto);
        detalle.setCantidad(dto.cantidad());
    }
}