package com.Proyecto.LogiTrack.SA.Repository;

import com.Proyecto.LogiTrack.SA.Model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    boolean existsByNombreCiudad(String nombreCiudad);

    boolean existsByNombreCiudadAndIdCiudadNot(String nombreCiudad, Long idCiudad);
}