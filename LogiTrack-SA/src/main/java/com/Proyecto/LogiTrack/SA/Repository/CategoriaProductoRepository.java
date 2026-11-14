package com.Proyecto.LogiTrack.SA.Repository;

import com.Proyecto.LogiTrack.SA.Model.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {

    boolean existsByTipoCategoria(String tipoCategoria);

    boolean existsByTipoCategoriaAndIdNot(String tipoCategoria, Long id);
}