package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Exception.BussinesRuleException;
import com.Proyecto.LogiTrack.SA.Mapper.DetalleMovimientoMapper;
import com.Proyecto.LogiTrack.SA.Model.DetalleMovimiento;
import com.Proyecto.LogiTrack.SA.Repository.DetalleMovimientoRepository;
import com.Proyecto.LogiTrack.SA.Repository.MovimientoRepository;
import com.Proyecto.LogiTrack.SA.Repository.ProductoRepository;
import com.Proyecto.LogiTrack.SA.Service.DetalleMovimientoService;
import com.Proyecto.LogiTrack.SA.Dto.Response.DetalleMovimientoDtoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DetalleMovimientoServiceImpl implements DetalleMovimientoService {

    private final DetalleMovimientoMapper detalleMovimientoMapper;
    private final DetalleMovimientoRepository detalleMovimientoRepository;
    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    @Override
    public void eliminar(Long id){
        detalleMovimientoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El detalle de movimiento no existe"));
        throw new BussinesRuleException("No se permite eliminar detalles de movimientos registrados");
    }

    @Override
    public List<DetalleMovimientoDtoResponse> listar(){
        return detalleMovimientoRepository.findAll().stream().map(detalleMovimientoMapper::entityToDto).toList();
    }

    @Override
    public DetalleMovimientoDtoResponse buscarId(Long id){
        DetalleMovimiento detalle = detalleMovimientoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe un detalle de movimiento con ese ID"));
        return detalleMovimientoMapper.entityToDto(detalle);
    }

    @Override
    public List<DetalleMovimientoDtoResponse> buscarMovimiento(Long movimientoId){
        if (!movimientoRepository.existsById(movimientoId)) {throw new EntityNotFoundException("El movimiento indicado no existe");}
        return detalleMovimientoRepository.findByMovimiento_Id(movimientoId).stream().map(detalleMovimientoMapper::entityToDto).toList();
    }

    @Override
    public List<DetalleMovimientoDtoResponse> buscarProducto(Long productoId){
        if (!productoRepository.existsById(productoId)) {throw new EntityNotFoundException("El producto indicado no existe");}
        return detalleMovimientoRepository.findByProducto_Id(productoId).stream().map(detalleMovimientoMapper::entityToDto).toList();
    }
}