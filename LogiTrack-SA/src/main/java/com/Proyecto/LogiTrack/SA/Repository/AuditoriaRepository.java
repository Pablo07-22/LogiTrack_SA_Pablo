package com.Proyecto.LogiTrack.SA.Repository;

import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    List<Auditoria> findByTipoOperacion(Auditoria.TipoOperacion tipoOperacion);

    List<Auditoria> findByUsuario_Id(Long usuarioId);

    List<Auditoria> findByEntidadAfectada(String entidadAfectada);

    List<Auditoria> findByIdRegistro(Long idRegistro);

    List<Auditoria> findByEntidadAfectadaAndIdRegistro(String entidadAfectada, Long idRegistro);

    List<Auditoria> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<Auditoria> findByTipoOperacionAndFechaBetween(Auditoria.TipoOperacion tipoOperacion, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}