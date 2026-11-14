package com.Proyecto.LogiTrack.SA.Repository;

import com.Proyecto.LogiTrack.SA.Model.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BodegaRepository extends JpaRepository<Bodega, Long> {

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdNot(String nombre, Long id);

    List<Bodega> findByCiudad_IdCiudad(Long ciudadId);

    List<Bodega> findByEncargado_Id(Long encargadoId);

    List<Bodega> findByCiudad_IdCiudadAndEncargado_Id(Long ciudadId, Long encargadoId);
}