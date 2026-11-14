package com.Proyecto.LogiTrack.SA.Repository;

import com.Proyecto.LogiTrack.SA.Model.InventarioBodega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventarioBodegaRepository extends JpaRepository<InventarioBodega, Long> {

    boolean existsByBodega_IdAndProducto_Id(Long bodegaId, Long productoId);

    List<InventarioBodega> findByBodega_Id(Long bodegaId);

    List<InventarioBodega> findByProducto_Id(Long productoId);

    InventarioBodega findByBodega_IdAndProducto_Id(Long bodegaId, Long productoId);

    List<InventarioBodega> findByStock(Integer stock);

    List<InventarioBodega> findByStockLessThan(Integer stock);

    List<InventarioBodega> findByStockGreaterThan(Integer stock);
}