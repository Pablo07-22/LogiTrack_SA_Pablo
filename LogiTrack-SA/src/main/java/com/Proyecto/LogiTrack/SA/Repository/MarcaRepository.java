package com.Proyecto.LogiTrack.SA.Repository;

import com.Proyecto.LogiTrack.SA.Model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    boolean existsByNombreMarca(String nombreMarca);

    boolean existsByNombreMarcaAndIdNot(String nombreMarca, Long id);
}