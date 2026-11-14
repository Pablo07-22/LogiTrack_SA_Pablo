package com.Proyecto.LogiTrack.SA.Repository;

import com.Proyecto.LogiTrack.SA.Model.DetalleMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleMovimientoRepository extends JpaRepository<DetalleMovimiento, Long> {

    boolean existsByMovimiento_Id(Long movimientoId);

    List<DetalleMovimiento> findByMovimiento_Id(Long movimientoId);

    boolean existsByProducto_Id(Long productoId);

    List<DetalleMovimiento> findByProducto_Id(Long productoId);

    boolean existsByMovimiento_IdAndProducto_Id(Long movimientoId, Long productoId);
}