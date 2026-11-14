package com.Proyecto.LogiTrack.SA.Repository;

import com.Proyecto.LogiTrack.SA.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);

    List<Usuario> findByUsername(String username);

    List<Usuario> findByRol(Usuario.Rol rol);

    List<Usuario> findByEstado(Usuario.Estado estado);

    List<Usuario> findByRolAndEstado(Usuario.Rol rol, Usuario.Estado estado);
}