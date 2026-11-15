package com.Proyecto.LogiTrack.SA.Service;

import com.Proyecto.LogiTrack.SA.Dto.Request.ProductoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.ProductoDtoResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {

    ProductoDtoResponse guardar(ProductoDtoRequest dto);

    ProductoDtoResponse actualizar(ProductoDtoRequest dto, Long id);

    void eliminar(Long id);

    List<ProductoDtoResponse> listar();

    ProductoDtoResponse buscarId(Long id);

    List<ProductoDtoResponse> buscarNombre(String nombre);

    List<ProductoDtoResponse> buscarMarca(Long marcaId);

    List<ProductoDtoResponse> buscarCategoria(Long categoriaId);

    List<ProductoDtoResponse> buscarPrecioMayor(BigDecimal precio);

    List<ProductoDtoResponse> buscarPrecioMenor(BigDecimal precio);

}