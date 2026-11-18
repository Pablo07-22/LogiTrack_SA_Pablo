package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Exception.DatabaseRuleException;
import com.Proyecto.LogiTrack.SA.Mapper.CategoriaProductoMapper;
import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import com.Proyecto.LogiTrack.SA.Model.CategoriaProducto;
import com.Proyecto.LogiTrack.SA.Repository.CategoriaProductoRepository;
import com.Proyecto.LogiTrack.SA.Repository.ProductoRepository;
import com.Proyecto.LogiTrack.SA.Service.AuditoriaService;
import com.Proyecto.LogiTrack.SA.Service.CategoriaProductoService;
import com.Proyecto.LogiTrack.SA.Dto.Request.CategoriaProductoDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.CategoriaProductoDtoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CategoriaProductoServiceImpl implements CategoriaProductoService {

    private final CategoriaProductoMapper categoriaProductoMapper;
    private final CategoriaProductoRepository categoriaProductoRepository;
    private final ProductoRepository productoRepository;
    private final AuditoriaService auditoriaService;

    @Override
    public CategoriaProductoDtoResponse guardar(CategoriaProductoDtoRequest dto) {
        if (categoriaProductoRepository.existsByTipoCategoria(dto.tipoCategoria())) {
            throw new DatabaseRuleException("La categoría ya se encuentra registrada");
        }
        CategoriaProducto categoria = categoriaProductoMapper.DtoToEntity(dto);
        CategoriaProducto categoriaGuardada = categoriaProductoRepository.save(categoria);
        auditoriaService.registrar("CategoriaProducto", categoriaGuardada.getId(), Auditoria.TipoOperacion.INSERTAR,
                null, Map.of("tipoCategoria", categoriaGuardada.getTipoCategoria()));
        return categoriaProductoMapper.entityToDto(categoriaGuardada);
    }

    @Override
    public CategoriaProductoDtoResponse actualizar(CategoriaProductoDtoRequest dto, Long id) {
        CategoriaProducto categoria = categoriaProductoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "La categoría que desea actualizar no existe"));
        if (categoriaProductoRepository.existsByTipoCategoriaAndIdNot(dto.tipoCategoria(), id)) {
            throw new DatabaseRuleException("Ya existe otra categoría con ese nombre");
        }
        Map<String, Object> valoresAnteriores = Map.of("tipoCategoria", categoria.getTipoCategoria());
        categoriaProductoMapper.updateEntityFromDto(categoria, dto);
        CategoriaProducto categoriaActualizada = categoriaProductoRepository.save(categoria);
        Map<String, Object> valoresNuevos = Map.of("tipoCategoria", categoriaActualizada.getTipoCategoria());
        auditoriaService.registrar("CategoriaProducto", categoriaActualizada.getId(), Auditoria.TipoOperacion.ACTUALIZAR,
                valoresAnteriores, valoresNuevos);
        return categoriaProductoMapper.entityToDto(categoriaActualizada);
    }


    @Override
    public void eliminar(Long id) {
        CategoriaProducto categoria = categoriaProductoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        "La categoría que desea eliminar no existe"));
        if (!productoRepository.findByCategoria_Id(id).isEmpty()) {
            throw new DatabaseRuleException("No se puede eliminar la categoría porque tiene productos asociados");
        }
        Map<String, Object> valoresAnteriores = Map.of("tipoCategoria", categoria.getTipoCategoria());
        categoriaProductoRepository.delete(categoria);
        auditoriaService.registrar("CategoriaProducto", id, Auditoria.TipoOperacion.BORRAR, valoresAnteriores, null);
    }

    @Override
    public List<CategoriaProductoDtoResponse> listar() {
        return categoriaProductoRepository.findAll().stream().map(categoriaProductoMapper::entityToDto).toList();
    }

    @Override
    public CategoriaProductoDtoResponse buscarId(Long id) {
        CategoriaProducto categoria = categoriaProductoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        "No existe una categoría con el ID indicado"));
        return categoriaProductoMapper.entityToDto(categoria);
    }
}
