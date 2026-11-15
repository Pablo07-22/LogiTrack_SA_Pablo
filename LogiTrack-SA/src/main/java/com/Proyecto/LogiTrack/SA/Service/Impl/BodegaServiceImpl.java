package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Exception.DatabaseRuleException;
import com.Proyecto.LogiTrack.SA.Mapper.BodegaMapper;
import com.Proyecto.LogiTrack.SA.Model.Bodega;
import com.Proyecto.LogiTrack.SA.Model.Ciudad;
import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Repository.BodegaRepository;
import com.Proyecto.LogiTrack.SA.Repository.CiudadRepository;
import com.Proyecto.LogiTrack.SA.Repository.InventarioBodegaRepository;
import com.Proyecto.LogiTrack.SA.Repository.MovimientoRepository;
import com.Proyecto.LogiTrack.SA.Repository.UsuarioRepository;
import com.Proyecto.LogiTrack.SA.Service.BodegaService;
import com.Proyecto.LogiTrack.SA.Dto.Request.BodegaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.BodegaDtoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BodegaServiceImpl implements BodegaService {

    private final BodegaMapper bodegaMapper;
    private final BodegaRepository bodegaRepository;
    private final CiudadRepository ciudadRepository;
    private final UsuarioRepository usuarioRepository;
    private final InventarioBodegaRepository inventarioBodegaRepository;
    private final MovimientoRepository movimientoRepository;

    @Override
    public BodegaDtoResponse guardar(BodegaDtoRequest dto){
        if (bodegaRepository.existsByNombre(dto.nombre())){throw new DatabaseRuleException("Ya existe una bodega con ese nombre");}
        Ciudad ciudad = ciudadRepository.findById(dto.ciudadId()).orElseThrow(() -> new EntityNotFoundException("La ciudad indicada no existe"));
        Usuario encargado = usuarioRepository.findById(dto.encargadoId()).orElseThrow(() -> new EntityNotFoundException("El usuario encargado no existe"));
        Bodega bodega = bodegaMapper.DtoToEntity(dto, ciudad, encargado);
        Bodega bodegaGuardada = bodegaRepository.save(bodega);
        return bodegaMapper.entityToDto(bodegaGuardada);
    }

    @Override
    public BodegaDtoResponse actualizar(BodegaDtoRequest dto, Long id){
        Bodega bodega = bodegaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La bodega que desea actualizar no existe"));
        if (bodegaRepository.existsByNombreAndIdNot(dto.nombre(), id)) {throw new DatabaseRuleException("Ya existe otra bodega con ese nombre");}
        Ciudad ciudad = ciudadRepository.findById(dto.ciudadId()).orElseThrow(() -> new EntityNotFoundException("La ciudad indicada no existe"));
        Usuario encargado = usuarioRepository.findById(dto.encargadoId()).orElseThrow(() -> new EntityNotFoundException("El usuario encargado no existe"));
        bodegaMapper.updateEntityFromDto(bodega, dto, ciudad, encargado);
        Bodega bodegaActualizada = bodegaRepository.save(bodega);
        return bodegaMapper.entityToDto(bodegaActualizada);
    }

    @Override
    public void eliminar(Long id){
        Bodega bodega = bodegaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La bodega que desea eliminar no existe"));
        boolean tieneInventario = !inventarioBodegaRepository.findByBodega_Id(id).isEmpty();
        boolean tieneMovimientosOrigen = !movimientoRepository.findByBodegaOrigen_Id(id).isEmpty();
        boolean tieneMovimientosDestino = !movimientoRepository.findByBodegaDestino_Id(id).isEmpty();
        if (tieneInventario || tieneMovimientosOrigen || tieneMovimientosDestino) {throw new DatabaseRuleException("No se puede eliminar la bodega porque tiene inventarios o movimientos asociados");}
        bodegaRepository.delete(bodega);
    }

    @Override
    public List<BodegaDtoResponse> listar(){
        return bodegaRepository.findAll().stream().map(bodegaMapper::entityToDto).toList();
    }

    @Override
    public BodegaDtoResponse buscarId(Long id){
        Bodega bodega = bodegaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe una bodega con el ID indicado"));
        return bodegaMapper.entityToDto(bodega);
    }

    @Override
    public List<BodegaDtoResponse> buscarCiudad(Long ciudadId) {
        if (!ciudadRepository.existsById(ciudadId)) {throw new EntityNotFoundException("La ciudad indicada no existe");}
        return bodegaRepository.findByCiudad_IdCiudad(ciudadId).stream().map(bodegaMapper::entityToDto).toList();
    }

    @Override
    public List<BodegaDtoResponse> buscarEncargado(Long encargadoId){
        if (!usuarioRepository.existsById(encargadoId)) {throw new EntityNotFoundException("El encargado indicado no existe");}
        return bodegaRepository.findByEncargado_Id(encargadoId).stream().map(bodegaMapper::entityToDto).toList();
    }
}