package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Exception.DatabaseRuleException;
import com.Proyecto.LogiTrack.SA.Mapper.ProductoMapper;
import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import com.Proyecto.LogiTrack.SA.Model.CategoriaProducto;
import com.Proyecto.LogiTrack.SA.Model.Marca;
import com.Proyecto.LogiTrack.SA.Model.Producto;
import com.Proyecto.LogiTrack.SA.Repository.CategoriaProductoRepository;
import com.Proyecto.LogiTrack.SA.Repository.DetalleMovimientoRepository;
import com.Proyecto.LogiTrack.SA.Repository.InventarioBodegaRepository;
import com.Proyecto.LogiTrack.SA.Repository.MarcaRepository;
import com.Proyecto.LogiTrack.SA.Repository.ProductoRepository;
import com.Proyecto.LogiTrack.SA.Service.AuditoriaService;
import com.Proyecto.LogiTrack.SA.Service.ProductoService;
import com.Proyecto.LogiTrack.SA.Dto.Request.ProductoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.ProductoDtoResponse;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoMapper productoMapper;
    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;
    private final CategoriaProductoRepository categoriaProductoRepository;
    private final InventarioBodegaRepository inventarioBodegaRepository;
    private final DetalleMovimientoRepository detalleMovimientoRepository;
    private final AuditoriaService auditoriaService;


    @Override
    public ProductoDtoResponse guardar(ProductoDtoRequest dto) {
        Marca marca = null;
        if (dto.marcaId() != null) {
            marca = marcaRepository.findById(dto.marcaId()).orElseThrow(() -> new EntityNotFoundException("La marca indicada no existe"));
        }
        CategoriaProducto categoria = categoriaProductoRepository.findById(dto.categoriaId())
                        .orElseThrow(() -> new EntityNotFoundException("La categoría indicada no existe"));
        Producto producto = productoMapper.DtoToEntity(dto, marca, categoria);
        Producto productoGuardado = productoRepository.save(producto);
        auditoriaService.registrar("Producto", productoGuardado.getId(), Auditoria.TipoOperacion.INSERTAR,
                null, obtenerValores(productoGuardado));
        return productoMapper.entityToDto(productoGuardado);
    }


    @Override
    public ProductoDtoResponse actualizar(ProductoDtoRequest dto, Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El producto que desea actualizar no existe"));
        Map<String, Object> valoresAnteriores = obtenerValores(producto);
        Marca marca = null;
        if (dto.marcaId() != null) {
            marca = marcaRepository.findById(dto.marcaId()).orElseThrow(() -> new EntityNotFoundException("La marca indicada no existe"));
        }
        CategoriaProducto categoria = categoriaProductoRepository.findById(dto.categoriaId())
                        .orElseThrow(() -> new EntityNotFoundException("La categoría indicada no existe"));
        productoMapper.updateEntityFromDto(producto, dto, marca, categoria);
        Producto productoActualizado = productoRepository.save(producto);
        auditoriaService.registrar("Producto", productoActualizado.getId(), Auditoria.TipoOperacion.ACTUALIZAR,
                valoresAnteriores, obtenerValores(productoActualizado));
        return productoMapper.entityToDto(productoActualizado);
    }


    @Override
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        "El producto que desea eliminar no existe"));
        boolean tieneInventario = !inventarioBodegaRepository.findByProducto_Id(id).isEmpty();
        boolean tieneMovimientos = detalleMovimientoRepository.existsByProducto_Id(id);
        if (tieneInventario || tieneMovimientos) {
            throw new DatabaseRuleException("No se puede eliminar el producto porque tiene inventarios o movimientos asociados");
        }
        Map<String, Object> valoresAnteriores = obtenerValores(producto);
        productoRepository.delete(producto);
        auditoriaService.registrar("Producto", producto.getId(), Auditoria.TipoOperacion.BORRAR, valoresAnteriores, null);
    }


    private Map<String, Object> obtenerValores(Producto producto) {
        Map<String, Object> valores = new LinkedHashMap<>();
        valores.put("nombre", producto.getNombre());
        valores.put("marcaId", producto.getMarca() != null ? producto.getMarca().getId() : null);
        valores.put("marca", producto.getMarca() != null ? producto.getMarca().getNombreMarca() : null);
        valores.put("categoriaId", producto.getCategoria().getId());
        valores.put("categoria", producto.getCategoria().getTipoCategoria());
        valores.put("precioUnitario", producto.getPrecioUnitario());
        valores.put("fechaCreacion", producto.getFechaCreacion() != null ? producto.getFechaCreacion().toString() : null);
        return valores;
    }


    @Override
    public List<ProductoDtoResponse> listar() {
        return productoRepository.findAll().stream().map(productoMapper::entityToDto).toList();
    }

    @Override
    public ProductoDtoResponse buscarId(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe un producto con el ID indicado"));
        return productoMapper.entityToDto(producto);
    }

    @Override
    public List<ProductoDtoResponse> buscarNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream().map(productoMapper::entityToDto).toList();
    }

    @Override
    public List<ProductoDtoResponse> buscarMarca(Long marcaId) {
        if (!marcaRepository.existsById(marcaId)) {
            throw new EntityNotFoundException("La marca indicada no existe");
        }
        return productoRepository.findByMarca_Id(marcaId).stream().map(productoMapper::entityToDto).toList();
    }

    @Override
    public List<ProductoDtoResponse> buscarCategoria(Long categoriaId) {
        if (!categoriaProductoRepository.existsById(categoriaId)) {
            throw new EntityNotFoundException("La categoría indicada no existe");
        }

        return productoRepository.findByCategoria_Id(categoriaId).stream().map(productoMapper::entityToDto).toList();
    }

    @Override
    public List<ProductoDtoResponse> buscarPrecioMayor(BigDecimal precio) {
        return productoRepository.findByPrecioUnitarioGreaterThanEqual(precio).stream().map(productoMapper::entityToDto).toList();
    }

    @Override
    public List<ProductoDtoResponse> buscarPrecioMenor(BigDecimal precio) {
        return productoRepository.findByPrecioUnitarioLessThanEqual(precio).stream().map(productoMapper::entityToDto).toList();
    }
}
