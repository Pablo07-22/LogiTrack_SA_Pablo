package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Exception.DatabaseRuleException;
import com.Proyecto.LogiTrack.SA.Mapper.MarcaMapper;
import com.Proyecto.LogiTrack.SA.Model.Marca;
import com.Proyecto.LogiTrack.SA.Repository.MarcaRepository;
import com.Proyecto.LogiTrack.SA.Repository.ProductoRepository;
import com.Proyecto.LogiTrack.SA.Service.MarcaService;
import com.Proyecto.LogiTrack.SA.Dto.Request.MarcaDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.MarcaDtoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MarcaServiceImpl implements MarcaService {

    private final MarcaMapper marcaMapper;
    private final MarcaRepository marcaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public MarcaDtoResponse guardar(MarcaDtoRequest dto){
        if (marcaRepository.existsByNombreMarca(dto.nombreMarca())) {throw new DatabaseRuleException("La marca ya se encuentra registrada");}
        Marca marca = marcaMapper.DtoToEntity(dto);
        Marca marcaGuardada = marcaRepository.save(marca);
        return marcaMapper.entityToDto(marcaGuardada);
    }

    @Override
    public MarcaDtoResponse actualizar(MarcaDtoRequest dto, Long id){
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La marca que desea actualizar no existe"));
        if (marcaRepository.existsByNombreMarcaAndIdNot(dto.nombreMarca(), id)) {throw new DatabaseRuleException("Ya existe otra marca con ese nombre");}
        marcaMapper.updateEntityFromDto(marca, dto);
        Marca marcaActualizada = marcaRepository.save(marca);
        return marcaMapper.entityToDto(marcaActualizada);
    }

    @Override
    public void eliminar(Long id){
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La marca que desea eliminar no existe"));
        if (!productoRepository.findByMarca_Id(id).isEmpty()) {throw new DatabaseRuleException("No se puede eliminar la marca porque tiene productos asociados");}
        marcaRepository.delete(marca);
    }

    @Override
    public List<MarcaDtoResponse> listar(){
        return marcaRepository.findAll().stream().map(marcaMapper::entityToDto).toList();
    }

    @Override
    public MarcaDtoResponse buscarId(Long id){
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe una marca con el ID indicado"));
        return marcaMapper.entityToDto(marca);
    }
}