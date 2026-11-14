package com.Proyecto.LogiTrack.SA.Repository;

import com.Proyecto.LogiTrack.SA.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByMarca_Id(Long marcaId);

    List<Producto> findByCategoria_Id(Long categoriaId);

    List<Producto> findByMarca_IdAndCategoria_Id(Long marcaId, Long categoriaId);

    List<Producto> findByPrecioUnitarioGreaterThanEqual(BigDecimal precio);

    List<Producto> findByPrecioUnitarioLessThanEqual(BigDecimal precio);

    List<Producto> findByNombreContainingIgnoreCaseAndCategoria_Id(String nombre, Long categoriaId);
}