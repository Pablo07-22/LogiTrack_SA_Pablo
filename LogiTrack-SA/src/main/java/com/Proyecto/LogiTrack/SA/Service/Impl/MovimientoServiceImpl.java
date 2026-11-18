package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Exception.BussinesRuleException;
import com.Proyecto.LogiTrack.SA.Mapper.DetalleMovimientoMapper;
import com.Proyecto.LogiTrack.SA.Mapper.MovimientoMapper;
import com.Proyecto.LogiTrack.SA.Model.Bodega;
import com.Proyecto.LogiTrack.SA.Model.DetalleMovimiento;
import com.Proyecto.LogiTrack.SA.Model.InventarioBodega;
import com.Proyecto.LogiTrack.SA.Model.Movimiento;
import com.Proyecto.LogiTrack.SA.Model.Producto;
import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Repository.BodegaRepository;
import com.Proyecto.LogiTrack.SA.Repository.DetalleMovimientoRepository;
import com.Proyecto.LogiTrack.SA.Repository.InventarioBodegaRepository;
import com.Proyecto.LogiTrack.SA.Repository.MovimientoRepository;
import com.Proyecto.LogiTrack.SA.Validaciones.Validaciones;
import com.Proyecto.LogiTrack.SA.Repository.ProductoRepository;
import com.Proyecto.LogiTrack.SA.Repository.UsuarioRepository;
import com.Proyecto.LogiTrack.SA.Service.MovimientoService;
import com.Proyecto.LogiTrack.SA.Dto.Request.DetalleMovimientoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Request.MovimientoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.MovimientoDtoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoMapper movimientoMapper;
    private final DetalleMovimientoMapper detalleMovimientoMapper;
    private final Validaciones validaciones;
    private final MovimientoRepository movimientoRepository;
    private final DetalleMovimientoRepository detalleMovimientoRepository;
    private final InventarioBodegaRepository inventarioBodegaRepository;
    private final ProductoRepository productoRepository;
    private final BodegaRepository bodegaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public MovimientoDtoResponse guardar(MovimientoDtoRequest dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioResponsableId()).orElseThrow(() -> new EntityNotFoundException(
                        "El usuario responsable no existe"));
        if (usuario.getEstado() != Usuario.Estado.ACTIVO) {
            throw new BussinesRuleException("El usuario responsable no se encuentra activo");
        }
        Bodega origen = null;
        Bodega destino = null;
        if (dto.bodegaOrigenId() != null) {
            origen = bodegaRepository.findById(dto.bodegaOrigenId()).orElseThrow(() -> new EntityNotFoundException("La bodega de origen no existe"));
        }
        if (dto.bodegaDestinoId() != null) {
            destino = bodegaRepository.findById(dto.bodegaDestinoId()).orElseThrow(() -> new EntityNotFoundException("La bodega de destino no existe"));
        }
        validarTipoMovimiento(dto.tipoMovimiento(), origen, destino);
        Set<Long> productosRegistrados = new HashSet<>();
        long unidadesAIngresar = 0;
        for (DetalleMovimientoDtoRequest detalle : dto.detalles()) {
            if (detalle.cantidad() == null || detalle.cantidad() <= 0) {
                throw new BussinesRuleException("La cantidad de cada producto debe ser mayor que cero");
            }
            if (!productosRegistrados.add(detalle.productoId())) {
                throw new BussinesRuleException("No se puede repetir un producto dentro del mismo movimiento");
            }
            unidadesAIngresar = Math.addExact(unidadesAIngresar, detalle.cantidad().longValue());
        }
        if (dto.tipoMovimiento() == Movimiento.TipoMovimiento.ENTRADA || dto.tipoMovimiento() == Movimiento.TipoMovimiento.TRANSFERENCIA) {
            validaciones.validarCapacidadDisponible(destino, unidadesAIngresar);
        }
        for (DetalleMovimientoDtoRequest detalle : dto.detalles()) {
            Producto producto = productoRepository.findById(detalle.productoId()).orElseThrow(() -> new EntityNotFoundException(
                            "No existe el producto con ID: " + detalle.productoId()));
            if (dto.tipoMovimiento() == Movimiento.TipoMovimiento.ENTRADA) {
                InventarioBodega inventarioDestino = obtenerOCrearInventario(destino, producto);
                inventarioDestino.setStock(inventarioDestino.getStock() + detalle.cantidad());
                inventarioBodegaRepository.save(inventarioDestino);
            }
            else if (dto.tipoMovimiento() == Movimiento.TipoMovimiento.SALIDA) {
                InventarioBodega inventarioOrigen = buscarInventario(origen, producto);
                validarStock(inventarioOrigen, detalle.cantidad());
                inventarioOrigen.setStock(inventarioOrigen.getStock() - detalle.cantidad());
                inventarioBodegaRepository.save(inventarioOrigen);
            }
            else if (dto.tipoMovimiento() == Movimiento.TipoMovimiento.TRANSFERENCIA) {
                InventarioBodega inventarioOrigen = buscarInventario(origen, producto);
                validarStock(inventarioOrigen, detalle.cantidad());
                InventarioBodega inventarioDestino = obtenerOCrearInventario(destino, producto);
                inventarioOrigen.setStock(inventarioOrigen.getStock() - detalle.cantidad());
                inventarioDestino.setStock(inventarioDestino.getStock() + detalle.cantidad());
                inventarioBodegaRepository.save(inventarioOrigen);
                inventarioBodegaRepository.save(inventarioDestino);
            }
        }
        Movimiento movimiento = movimientoMapper.DtoToEntity(dto, usuario, origen, destino);
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);
        for (DetalleMovimientoDtoRequest detalleDto : dto.detalles()) {
            Producto producto = productoRepository.findById(detalleDto.productoId()).orElseThrow(() -> new EntityNotFoundException(
                            "El producto no existe"));
            DetalleMovimiento detalle = detalleMovimientoMapper.DtoToEntity(detalleDto, movimientoGuardado, producto);
            detalleMovimientoRepository.save(detalle);
        }
        return movimientoMapper.entityToDto(movimientoGuardado);
    }

    private void validarTipoMovimiento(Movimiento.TipoMovimiento tipo, Bodega origen, Bodega destino){
        if (tipo == Movimiento.TipoMovimiento.ENTRADA) {
            if (origen != null || destino == null) {throw new BussinesRuleException("Una entrada requiere una bodega destino y no debe tener bodega origen");}
        }
        else if (tipo == Movimiento.TipoMovimiento.SALIDA) {
            if (origen == null || destino != null) {throw new BussinesRuleException("Una salida requiere una bodega origen y no debe tener bodega destino");}
        }
        else if (tipo == Movimiento.TipoMovimiento.TRANSFERENCIA) {
            if (origen == null || destino == null) {throw new BussinesRuleException("Una transferencia requiere bodega origen y destino");}
            if (origen.getId().equals(destino.getId())) {throw new BussinesRuleException("La bodega de origen debe ser diferente a la de destino");}
        }
    }

    private InventarioBodega buscarInventario(Bodega bodega, Producto producto){
        InventarioBodega inventario = inventarioBodegaRepository.findByBodega_IdAndProducto_Id(bodega.getId(), producto.getId());
        if (inventario == null) {throw new BussinesRuleException("El producto no tiene inventario registrado en la bodega de origen");}
        return inventario;
    }

    private InventarioBodega obtenerOCrearInventario(Bodega bodega, Producto producto){
        InventarioBodega inventario = inventarioBodegaRepository.findByBodega_IdAndProducto_Id(bodega.getId(), producto.getId());
        if (inventario == null) {inventario = new InventarioBodega();inventario.setBodega(bodega);inventario.setProducto(producto);inventario.setStock(0);}
        return inventario;
    }

    private void validarStock(InventarioBodega inventario, Integer cantidad){
        if (inventario.getStock() < cantidad) {throw new BussinesRuleException("Stock insuficiente. Disponible: " + inventario.getStock());}
    }

    @Override
    public void eliminar(Long id){
        movimientoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El movimiento que desea eliminar no existe"));
        throw new BussinesRuleException("No se permite eliminar un movimiento registrado. Debe realizarse un movimiento correctivo");
    }

    @Override
    public List<MovimientoDtoResponse> listar(){
        return movimientoRepository.findAll().stream().map(movimientoMapper::entityToDto).toList();
    }

    @Override
    public MovimientoDtoResponse buscarId(Long id){
        Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe un movimiento con el ID indicado"));
        return movimientoMapper.entityToDto(movimiento);
    }

    @Override
    public List<MovimientoDtoResponse> buscarTipo(Movimiento.TipoMovimiento tipo){
        return movimientoRepository.findByTipoMovimiento(tipo).stream().map(movimientoMapper::entityToDto).toList();
    }

    @Override
    public List<MovimientoDtoResponse> buscarUsuario(Long usuarioId){
        if (!usuarioRepository.existsById(usuarioId)) {throw new EntityNotFoundException("El usuario indicado no existe");}
        return movimientoRepository.findByUsuarioResponsable_Id(usuarioId).stream().map(movimientoMapper::entityToDto).toList();
    }

    @Override
    public List<MovimientoDtoResponse> buscarBodegaOrigen(Long bodegaId){
        if (!bodegaRepository.existsById(bodegaId)) {throw new EntityNotFoundException("La bodega indicada no existe");}
        return movimientoRepository.findByBodegaOrigen_Id(bodegaId).stream().map(movimientoMapper::entityToDto).toList();
    }

    @Override
    public List<MovimientoDtoResponse> buscarBodegaDestino(Long bodegaId){
        if (!bodegaRepository.existsById(bodegaId)) {throw new EntityNotFoundException("La bodega indicada no existe");}
        return movimientoRepository.findByBodegaDestino_Id(bodegaId).stream().map(movimientoMapper::entityToDto).toList();
    }

    @Override
    public List<MovimientoDtoResponse> buscarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        if (fechaInicio.isAfter(fechaFin)) {throw new BussinesRuleException("La fecha inicial no puede ser posterior a la fecha final");}
        return movimientoRepository.findByFechaBetween(fechaInicio, fechaFin).stream().map(movimientoMapper::entityToDto).toList();
    }
}