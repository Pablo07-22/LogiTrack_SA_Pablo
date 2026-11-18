package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Exception.DatabaseRuleException;
import com.Proyecto.LogiTrack.SA.Mapper.BodegaMapper;
import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import com.Proyecto.LogiTrack.SA.Model.Bodega;
import com.Proyecto.LogiTrack.SA.Model.Ciudad;
import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Repository.BodegaRepository;
import com.Proyecto.LogiTrack.SA.Repository.CiudadRepository;
import com.Proyecto.LogiTrack.SA.Repository.InventarioBodegaRepository;
import com.Proyecto.LogiTrack.SA.Repository.MovimientoRepository;
import com.Proyecto.LogiTrack.SA.Repository.UsuarioRepository;
import com.Proyecto.LogiTrack.SA.Service.AuditoriaService;
import com.Proyecto.LogiTrack.SA.Validaciones.Validaciones;
import com.Proyecto.LogiTrack.SA.Service.BodegaService;
import com.Proyecto.LogiTrack.SA.Dto.Request.BodegaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.BodegaDtoResponse;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BodegaServiceImpl implements BodegaService {

    private final BodegaMapper bodegaMapper;
    private final BodegaRepository bodegaRepository;
    private final CiudadRepository ciudadRepository;
    private final UsuarioRepository usuarioRepository;
    private final InventarioBodegaRepository inventarioBodegaRepository;
    private final Validaciones validaciones;
    private final MovimientoRepository movimientoRepository;
    private final AuditoriaService auditoriaService;

    // REGISTRAR BODEGA
    @Override

    public BodegaDtoResponse guardar(BodegaDtoRequest dto) {
        if (bodegaRepository.existsByNombre(dto.nombre())) {throw new DatabaseRuleException("Ya existe una bodega con ese nombre");}
        Ciudad ciudad = ciudadRepository.findById(dto.ciudadId()).orElseThrow(() -> new EntityNotFoundException("La ciudad indicada no existe"));
        Usuario encargado = usuarioRepository.findById(dto.encargadoId()).orElseThrow(() -> new EntityNotFoundException("El usuario encargado no existe"));
        Bodega bodega = bodegaMapper.DtoToEntity(dto, ciudad, encargado);
        Bodega bodegaGuardada = bodegaRepository.save(bodega);
        auditoriaService.registrar("Bodega", bodegaGuardada.getId(), Auditoria.TipoOperacion.INSERTAR, null, obtenerValores(bodegaGuardada));
        return bodegaMapper.entityToDto(bodegaGuardada);
    }

    // ACTUALIZAR BODEGA
    @Override
    public BodegaDtoResponse actualizar(BodegaDtoRequest dto, Long id) {
        Bodega bodega = bodegaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La bodega que desea actualizar no existe"));
        Map<String, Object> valoresAnteriores = obtenerValores(bodega);
        if (bodegaRepository.existsByNombreAndIdNot(dto.nombre(), id)) {throw new DatabaseRuleException("Ya existe otra bodega con ese nombre");}
        Ciudad ciudad = ciudadRepository.findById(dto.ciudadId()).orElseThrow(() -> new EntityNotFoundException("La ciudad indicada no existe"));
        Usuario encargado = usuarioRepository.findById(dto.encargadoId()).orElseThrow(() -> new EntityNotFoundException("El usuario encargado no existe"));
        validaciones.validarCapacidadAlActualizarBodega(bodega, dto.capacidad());
        bodegaMapper.updateEntityFromDto(bodega, dto, ciudad, encargado);
        Bodega bodegaActualizada = bodegaRepository.save(bodega);
        auditoriaService.registrar("Bodega", bodegaActualizada.getId(), Auditoria.TipoOperacion.ACTUALIZAR, valoresAnteriores, obtenerValores(bodegaActualizada));
        return bodegaMapper.entityToDto(bodegaActualizada);
    }

    // ELIMINAR BODEGA
    @Override
    public void eliminar(Long id) {
        Bodega bodega = bodegaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La bodega que desea eliminar no existe"));
        boolean tieneInventario = !inventarioBodegaRepository.findByBodega_Id(id).isEmpty();
        boolean tieneMovimientosOrigen = !movimientoRepository.findByBodegaOrigen_Id(id).isEmpty();
        boolean tieneMovimientosDestino = !movimientoRepository.findByBodegaDestino_Id(id).isEmpty();
        if (tieneInventario || tieneMovimientosOrigen || tieneMovimientosDestino) {
            throw new DatabaseRuleException("No se puede eliminar la bodega porque tiene inventarios o movimientos asociados");
        }
        Map<String, Object> valoresAnteriores = obtenerValores(bodega);
        bodegaRepository.delete(bodega);
        auditoriaService.registrar("Bodega", bodega.getId(), Auditoria.TipoOperacion.BORRAR, valoresAnteriores, null);
    }

    // OBTENER DATOS PARA AUDITORÍA
    private Map<String, Object> obtenerValores(Bodega bodega) {
        Map<String, Object> valores = new LinkedHashMap<>();
        valores.put("nombre", bodega.getNombre());
        valores.put("ciudadId", bodega.getCiudad() != null ? bodega.getCiudad().getIdCiudad() : null);
        valores.put("ciudad", bodega.getCiudad() != null ? bodega.getCiudad().getNombreCiudad() : null);
        valores.put("encargadoId", bodega.getEncargado() != null ? bodega.getEncargado().getId() : null);
        valores.put("encargado", bodega.getEncargado() != null ? bodega.getEncargado().getUsername() : null);
        valores.put("capacidad", bodega.getCapacidad());
        valores.put("fechaCreacion", bodega.getFechaCreacion() != null ? bodega.getFechaCreacion().toString() : null);
        return valores;
    }

    // CONSULTAR BODEGAS
    @Override
    public List<BodegaDtoResponse> listar() {
        return bodegaRepository.findAll().stream().map(bodegaMapper::entityToDto).toList();
    }

    @Override
    public BodegaDtoResponse buscarId(Long id) {
        Bodega bodega = bodegaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe una bodega con el ID indicado"));
        return bodegaMapper.entityToDto(bodega);
    }

    @Override
    public List<BodegaDtoResponse> buscarCiudad(Long ciudadId) {
        if (!ciudadRepository.existsById(ciudadId)) {throw new EntityNotFoundException("La ciudad indicada no existe");}
        return bodegaRepository.findByCiudad_IdCiudad(ciudadId).stream().map(bodegaMapper::entityToDto).toList();
    }

    @Override
    public List<BodegaDtoResponse> buscarEncargado(Long encargadoId) {
        if (!usuarioRepository.existsById(encargadoId)) {throw new EntityNotFoundException("El encargado indicado no existe");}
        return bodegaRepository.findByEncargado_Id(encargadoId).stream().map(bodegaMapper::entityToDto).toList();
    }
}
