package com.Proyecto.LogiTrack.SA.Mapper;

import com.Proyecto.LogiTrack.SA.Model.Producto;
import com.Proyecto.LogiTrack.SA.Model.Marca;
import com.Proyecto.LogiTrack.SA.Model.CategoriaProducto;
import com.Proyecto.LogiTrack.SA.Dto.Request.ProductoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.ProductoDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    private final MarcaMapper marcaMapper;
    private final CategoriaProductoMapper categoriaProductoMapper;

    public ProductoMapper(MarcaMapper marcaMapper, CategoriaProductoMapper categoriaProductoMapper){
        this.marcaMapper = marcaMapper;
        this.categoriaProductoMapper = categoriaProductoMapper;
    }

    public ProductoDtoResponse entityToDto(Producto producto){

        if (producto == null) return null;
        return new ProductoDtoResponse(producto.getId(),
                producto.getNombre(),
                marcaMapper.entityToDto(producto.getMarca()),
                categoriaProductoMapper.entityToDto(producto.getCategoria()),
                producto.getPrecioUnitario(),
                producto.getFechaCreacion()
        );
    }

    public Producto DtoToEntity(ProductoDtoRequest dto, Marca marca, CategoriaProducto categoria){

        if (dto == null) return null;
        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setMarca(marca);
        producto.setCategoria(categoria);
        producto.setPrecioUnitario(dto.precioUnitario());
        return producto;
    }

    public void updateEntityFromDto(Producto producto, ProductoDtoRequest dto, Marca marca, CategoriaProducto categoria){

        if (dto == null || producto == null) return;
        producto.setNombre(dto.nombre());
        producto.setMarca(marca);
        producto.setCategoria(categoria);
        producto.setPrecioUnitario(dto.precioUnitario());
    }
}