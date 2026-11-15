package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Exception.DatabaseRuleException;
import com.Proyecto.LogiTrack.SA.Mapper.InventarioBodegaMapper;
import com.Proyecto.LogiTrack.SA.Model.Bodega;
import com.Proyecto.LogiTrack.SA.Model.InventarioBodega;
import com.Proyecto.LogiTrack.SA.Model.Producto;
import com.Proyecto.LogiTrack.SA.Repository.BodegaRepository;
import com.Proyecto.LogiTrack.SA.Repository.InventarioBodegaRepository;
import com.Proyecto.LogiTrack.SA.Repository.ProductoRepository;
import com.Proyecto.LogiTrack.SA.Service.InventarioBodegaService;
import com.Proyecto.LogiTrack.SA.Dto.Request.InventarioBodegaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.InventarioBodegaDtoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InventarioBodegaServiceImpl implements InventarioBodegaService {

    private final InventarioBodegaMapper inventarioBodegaMapper;
    private final InventarioBodegaRepository inventarioBodegaRepository;
    private final BodegaRepository bodegaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public InventarioBodegaDtoResponse guardar(InventarioBodegaDtoRequest dto){
        Bodega bodega = bodegaRepository.findById(dto.bodegaId()).orElseThrow(() -> new EntityNotFoundException("La bodega indicada no existe"));
        Producto producto = productoRepository.findById(dto.productoId()).orElseThrow(() -> new EntityNotFoundException("El producto indicado no existe"));
        if (inventarioBodegaRepository.existsByBodega_IdAndProducto_Id(dto.bodegaId(), dto.productoId())) {throw new DatabaseRuleException("El producto ya tiene un registro de inventario en esta bodega");}
        InventarioBodega inventario = inventarioBodegaMapper.DtoToEntity(dto, bodega, producto);
        InventarioBodega inventarioGuardado = inventarioBodegaRepository.save(inventario);
        return inventarioBodegaMapper.entityToDto(inventarioGuardado);
    }

    @Override
    public InventarioBodegaDtoResponse actualizar(InventarioBodegaDtoRequest dto, Long id){

        InventarioBodega inventario = inventarioBodegaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El inventario que desea actualizar no existe"));
        Bodega bodega = bodegaRepository.findById(dto.bodegaId()).orElseThrow(() -> new EntityNotFoundException("La bodega indicada no existe"));
        Producto producto = productoRepository.findById(dto.productoId()).orElseThrow(() -> new EntityNotFoundException("El producto indicado no existe"));
        InventarioBodega existente = inventarioBodegaRepository.findByBodega_IdAndProducto_Id(dto.bodegaId(), dto.productoId());
        if (existente != null && !existente.getId().equals(id)) {throw new DatabaseRuleException("Ya existe un inventario para ese producto en esa bodega");}
        inventarioBodegaMapper.updateEntityFromDto(inventario, dto, bodega, producto);
        InventarioBodega inventarioActualizado = inventarioBodegaRepository.save(inventario);
        return inventarioBodegaMapper.entityToDto(inventarioActualizado);
    }

    @Override
    public void eliminar(Long id){
        InventarioBodega inventario = inventarioBodegaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El inventario que desea eliminar no existe"));
        if (inventario.getStock() > 0) {throw new DatabaseRuleException("No se puede eliminar un inventario que tiene unidades disponibles");}
        inventarioBodegaRepository.delete(inventario);
    }

    @Override
    public List<InventarioBodegaDtoResponse> listar(){
        return inventarioBodegaRepository.findAll().stream().map(inventarioBodegaMapper::entityToDto).toList();
    }

    @Override
    public InventarioBodegaDtoResponse buscarId(Long id){
        InventarioBodega inventario = inventarioBodegaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe un inventario con el ID indicado"));
        return inventarioBodegaMapper.entityToDto(inventario);
    }

    @Override
    public List<InventarioBodegaDtoResponse> buscarBodega(Long bodegaId){
        if (!bodegaRepository.existsById(bodegaId)) {throw new EntityNotFoundException("La bodega indicada no existe");}
        return inventarioBodegaRepository.findByBodega_Id(bodegaId).stream().map(inventarioBodegaMapper::entityToDto).toList();
    }

    @Override
    public List<InventarioBodegaDtoResponse> buscarProducto(Long productoId){
        if (!productoRepository.existsById(productoId)) {throw new EntityNotFoundException("El producto indicado no existe");}
        return inventarioBodegaRepository.findByProducto_Id(productoId).stream().map(inventarioBodegaMapper::entityToDto).toList();
    }

    @Override
    public List<InventarioBodegaDtoResponse> buscarStockBajo(Integer stock){
        return inventarioBodegaRepository.findByStockLessThan(stock).stream().map(inventarioBodegaMapper::entityToDto).toList();
    }
}