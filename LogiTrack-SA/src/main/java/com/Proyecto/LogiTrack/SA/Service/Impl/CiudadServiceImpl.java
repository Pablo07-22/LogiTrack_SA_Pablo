package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Exception.DatabaseRuleException;
import com.Proyecto.LogiTrack.SA.Mapper.CiudadMapper;
import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import com.Proyecto.LogiTrack.SA.Model.Ciudad;
import com.Proyecto.LogiTrack.SA.Repository.BodegaRepository;
import com.Proyecto.LogiTrack.SA.Repository.CiudadRepository;
import com.Proyecto.LogiTrack.SA.Service.AuditoriaService;
import com.Proyecto.LogiTrack.SA.Service.CiudadService;
import com.Proyecto.LogiTrack.SA.Dto.Request.CiudadDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.CiudadDtoResponse;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CiudadServiceImpl implements CiudadService {

    private final CiudadMapper ciudadMapper;
    private final CiudadRepository ciudadRepository;
    private final BodegaRepository bodegaRepository;
    private final AuditoriaService auditoriaService;

    // INSERTAR CIUDAD
    @Override
    public CiudadDtoResponse guardar(CiudadDtoRequest dto) {
        if (ciudadRepository.existsByNombreCiudad(dto.nombreCiudad())) {throw new DatabaseRuleException("La ciudad ya se encuentra registrada");}
        Ciudad ciudad = ciudadMapper.DtoToEntity(dto);
        Ciudad ciudadGuardada = ciudadRepository.save(ciudad);
        Map<String, Object> valoresNuevos = Map.of("nombreCiudad", ciudadGuardada.getNombreCiudad());
        auditoriaService.registrar("Ciudad", ciudadGuardada.getIdCiudad(), Auditoria.TipoOperacion.INSERTAR, null, valoresNuevos);
        return ciudadMapper.entityToDto(ciudadGuardada);
    }

    // ACTUALIZAR CIUDAD
    @Override
    public CiudadDtoResponse actualizar(CiudadDtoRequest dto, Long id) {
        Ciudad ciudad = ciudadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La ciudad que desea actualizar no existe"));
        if (ciudadRepository.existsByNombreCiudadAndIdCiudadNot(dto.nombreCiudad(), id)) {
            throw new DatabaseRuleException("Ya existe otra ciudad con ese nombre");
        }
        Map<String, Object> valoresAnteriores = Map.of("nombreCiudad", ciudad.getNombreCiudad());
        ciudadMapper.updateEntityFromDto(ciudad, dto);
        Ciudad ciudadActualizada = ciudadRepository.save(ciudad);
        Map<String, Object> valoresNuevos = Map.of("nombreCiudad", ciudadActualizada.getNombreCiudad());
        auditoriaService.registrar("Ciudad", ciudadActualizada.getIdCiudad(), Auditoria.TipoOperacion.ACTUALIZAR, valoresAnteriores, valoresNuevos);
        return ciudadMapper.entityToDto(ciudadActualizada);
    }

    // ELIMINAR CIUDAD
    @Override
    public void eliminar(Long id) {
        Ciudad ciudad = ciudadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La ciudad que desea eliminar no existe"));
        if (!bodegaRepository.findByCiudad_IdCiudad(id).isEmpty()) {
            throw new DatabaseRuleException("No se puede eliminar la ciudad porque tiene bodegas asociadas");
        }
        Map<String, Object> valoresAnteriores = Map.of("nombreCiudad", ciudad.getNombreCiudad());
        ciudadRepository.delete(ciudad);
        auditoriaService.registrar("Ciudad", ciudad.getIdCiudad(), Auditoria.TipoOperacion.BORRAR, valoresAnteriores, null);
    }

    // CONSULTAR CIUDADES
    @Override
    public List<CiudadDtoResponse> listar() {
        return ciudadRepository.findAll().stream().map(ciudadMapper::entityToDto).toList();
    }

    @Override
    public CiudadDtoResponse buscarId(Long id) {
        Ciudad ciudad = ciudadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe una ciudad con el ID indicado"));
        return ciudadMapper.entityToDto(ciudad);
    }

    @Override
    public CiudadDtoResponse buscarNombre(String nombre) {
        Ciudad ciudad = ciudadRepository.findAll().stream().filter(c -> c.getNombreCiudad().equalsIgnoreCase(nombre))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("No existe una ciudad con ese nombre"));
        return ciudadMapper.entityToDto(ciudad);
    }
}
