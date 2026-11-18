package com.Proyecto.LogiTrack.SA.Repository;

import com.Proyecto.LogiTrack.SA.Model.InventarioBodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventarioBodegaRepository extends JpaRepository<InventarioBodega, Long> {

    boolean existsByBodega_IdAndProducto_Id(Long bodegaId, Long productoId);

    @Query("SELECT COALESCE(SUM(i.stock), 0) " +
                  "FROM InventarioBodega i " +
                  "WHERE i.bodega.id = :bodegaId")
    Long sumarStockPorBodega(@Param("bodegaId") Long bodegaId);

    List<InventarioBodega> findByBodega_Id(Long bodegaId);

    List<InventarioBodega> findByProducto_Id(Long productoId);

    InventarioBodega findByBodega_IdAndProducto_Id(Long bodegaId, Long productoId);

    List<InventarioBodega> findByStock(Integer stock);

    List<InventarioBodega> findByStockLessThan(Integer stock);

    List<InventarioBodega> findByStockGreaterThan(Integer stock);
}