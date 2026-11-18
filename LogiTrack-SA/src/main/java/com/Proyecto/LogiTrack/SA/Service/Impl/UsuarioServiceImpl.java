package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Auth.RegistroRequest;
import com.Proyecto.LogiTrack.SA.Dto.Request.AdministradorDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.UsuarioResumenDtoResponse;
import com.Proyecto.LogiTrack.SA.Exception.BussinesRuleException;
import com.Proyecto.LogiTrack.SA.Exception.DatabaseRuleException;
import com.Proyecto.LogiTrack.SA.Mapper.UsuarioMapper;
import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Repository.BodegaRepository;
import com.Proyecto.LogiTrack.SA.Repository.MovimientoRepository;
import com.Proyecto.LogiTrack.SA.Repository.UsuarioRepository;
import com.Proyecto.LogiTrack.SA.Service.UsuarioService;
import com.Proyecto.LogiTrack.SA.Dto.Request.UsuarioDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.UsuarioDtoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final BodegaRepository bodegaRepository;
    private final MovimientoRepository movimientoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDtoResponse guardar(UsuarioDtoRequest dto){
        if (usuarioRepository.existsByUsername(dto.username())) {throw new DatabaseRuleException("El nombre de usuario ya se encuentra registrado");}
        String password = passwordEncoder.encode(dto.password());
        Usuario usuario = usuarioMapper.DtoToEntity(dto, password);
        if (usuario.getEstado() == null) {usuario.setEstado(Usuario.Estado.PENDIENTE);}
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.entityToDto(usuarioGuardado);
    }

    @Override
    public UsuarioDtoResponse actualizar(UsuarioDtoRequest dto, Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El usuario que desea actualizar no existe"));
        if (usuarioRepository.existsByUsernameAndIdNot(dto.username(), id)) {throw new DatabaseRuleException("El nombre de usuario ya está registrado");}
        String password = passwordEncoder.encode(dto.password());
        usuarioMapper.updateEntityFromDto(usuario, dto, password);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return usuarioMapper.entityToDto(usuarioActualizado);
    }

    @Override
    public void eliminar(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El usuario que desea eliminar no existe"));
        if (!bodegaRepository.findByEncargado_Id(id).isEmpty()) {throw new DatabaseRuleException("No se puede eliminar el usuario porque está encargado de una bodega");}
        if (!movimientoRepository.findByUsuarioResponsable_Id(id).isEmpty()) {throw new DatabaseRuleException("No se puede eliminar el usuario porque tiene movimientos registrados");}
        usuarioRepository.delete(usuario);
    }

    @Override
    public List<UsuarioDtoResponse> listar(){
        return usuarioRepository.findAll().stream().map(usuarioMapper::entityToDto).toList();
    }

    @Override
    public UsuarioDtoResponse buscarId(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe un usuario con el ID indicado"));
        return usuarioMapper.entityToDto(usuario);
    }

    @Override
    public UsuarioDtoResponse buscarUsername(String username){
        List<Usuario> usuarios = usuarioRepository.findByUsername(username);
        if (usuarios.isEmpty()) {throw new EntityNotFoundException("No existe un usuario con ese username");}
        return usuarioMapper.entityToDto(usuarios.get(0));
    }

    @Override
    public List<UsuarioDtoResponse> buscarRol(Usuario.Rol rol){
        return usuarioRepository.findByRol(rol).stream().map(usuarioMapper::entityToDto).toList();
    }

    @Override
    public List<UsuarioDtoResponse> buscarEstado(Usuario.Estado estado){
        return usuarioRepository.findByEstado(estado).stream().map(usuarioMapper::entityToDto).toList();
    }


    @Override
    public UsuarioDtoResponse registrarSolicitud(RegistroRequest dto) {
        if (usuarioRepository.existsByUsername(dto.username())) {
            throw new BussinesRuleException("Este username ya se encuentra registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        usuario.setRol(Usuario.Rol.EMPLEADO);
        usuario.setEstado(Usuario.Estado.PENDIENTE);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.entityToDto(usuarioGuardado);
    }

    @Override
    public List<UsuarioDtoResponse> listarPendientes() {
        return usuarioRepository.findByEstado(Usuario.Estado.PENDIENTE).stream().map(usuarioMapper::entityToDto).toList();
    }

    @Override
    public UsuarioDtoResponse aprobarSolicitud(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        "No existe el usuario solicitado"));
        if (usuario.getEstado() != Usuario.Estado.PENDIENTE) {
            throw new BussinesRuleException("El usuario no tiene una solicitud pendiente");
        }
        usuario.setRol(Usuario.Rol.EMPLEADO);
        usuario.setEstado(Usuario.Estado.ACTIVO);
        return usuarioMapper.entityToDto(usuarioRepository.save(usuario));
    }

    @Override
    public void rechazarSolicitud(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        "No existe el usuario solicitado"));
        if (usuario.getEstado() != Usuario.Estado.PENDIENTE) {
            throw new BussinesRuleException("Solo se pueden rechazar solicitudes pendientes");
        }
        usuarioRepository.delete(usuario);
    }


    @Override
    public UsuarioDtoResponse crearAdministrador(AdministradorDtoRequest dto) {
        if (usuarioRepository.existsByUsername(dto.username())) {
            throw new BussinesRuleException("Este username ya se encuentra registrado");
        }
        Usuario administrador = new Usuario();
        administrador.setUsername(dto.username());
        administrador.setPassword(passwordEncoder.encode(dto.password()));
        administrador.setRol(Usuario.Rol.ADMIN);
        administrador.setEstado(Usuario.Estado.ACTIVO);
        Usuario guardado = usuarioRepository.save(administrador);
        return usuarioMapper.entityToDto(guardado);
    }

    @Override
    public List<UsuarioResumenDtoResponse> listarActivosParaMovimiento() {
        return usuarioRepository.findByEstado(Usuario.Estado.ACTIVO).stream().map(usuarioMapper::entityToResumenDto).toList();
    }
}