package com.Proyecto.LogiTrack.SA.Service.Impl;

import com.Proyecto.LogiTrack.SA.Mapper.AuditoriaMapper;
import com.Proyecto.LogiTrack.SA.Model.Auditoria;
import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Repository.AuditoriaRepository;
import com.Proyecto.LogiTrack.SA.Repository.UsuarioRepository;
import com.Proyecto.LogiTrack.SA.Service.AuditoriaService;
import com.Proyecto.LogiTrack.SA.Dto.Response.AuditoriaDtoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Proyecto.LogiTrack.SA.Exception.BussinesRuleException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaMapper auditoriaMapper;
    private final AuditoriaRepository auditoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<AuditoriaDtoResponse> listar(){
        return auditoriaRepository.findAll().stream().map(auditoriaMapper::entityToDto).toList();
    }

    @Override
    public AuditoriaDtoResponse buscarId(Long id) {
        Auditoria auditoria = auditoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe una auditoría con el ID indicado"));
        return auditoriaMapper.entityToDto(auditoria);
    }

    @Override
    public List<AuditoriaDtoResponse> buscarUsuario(Long usuarioId){
        if (!usuarioRepository.existsById(usuarioId)) {throw new EntityNotFoundException("El usuario indicado no existe");}
        return auditoriaRepository.findByUsuario_Id(usuarioId).stream().map(auditoriaMapper::entityToDto).toList();
    }

    @Override
    public List<AuditoriaDtoResponse> buscarTipo(Auditoria.TipoOperacion tipo){
        return auditoriaRepository.findByTipoOperacion(tipo).stream().map(auditoriaMapper::entityToDto).toList();
    }

    @Override
    public List<AuditoriaDtoResponse> buscarEntidad(String entidad){
        return auditoriaRepository.findByEntidadAfectada(entidad).stream().map(auditoriaMapper::entityToDto).toList();
    }

    @Override
    public List<AuditoriaDtoResponse> buscarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        if (fechaInicio.isAfter(fechaFin)) { throw new BussinesRuleException("La fecha inicial no puede ser posterior a la fecha final");}
        return auditoriaRepository.findByFechaBetween(fechaInicio, fechaFin).stream().map(auditoriaMapper::entityToDto).toList();
    }

    @Override
    public void registrar(String entidad, Long idRegistro, Auditoria.TipoOperacion tipo, Object valoresAnteriores, Object valoresNuevos) {
        if (entidad == null || entidad.isBlank()) {throw new IllegalArgumentException("La entidad afectada es obligatoria");}
        if (idRegistro == null) {throw new IllegalArgumentException("El ID del registro es obligatorio");}
        if (tipo == null) {throw new IllegalArgumentException("El tipo de operación es obligatorio");}
        Auditoria auditoria = new Auditoria();
        auditoria.setFecha(LocalDateTime.now());
        auditoria.setTipoOperacion(tipo);
        auditoria.setEntidadAfectada(entidad);
        auditoria.setIdRegistro(idRegistro);
        auditoria.setValoresAnteriores(convertirJson(valoresAnteriores));
        auditoria.setValoresNuevos(convertirJson(valoresNuevos));
        auditoria.setUsuario(obtenerUsuarioAutenticado());
        auditoriaRepository.save(auditoria);
    }

    // IDENTIFICAR AL USUARIO AUTENTICADO
    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null || authentication.getName().equals("anonymousUser")) {
            return null;
        }
        List<Usuario> usuarios = usuarioRepository.findByUsername(authentication.getName());
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    // CONVERTIR VALORES A JSON
    private String convertirJson(Object valores) {
        if (valores == null) {return null;}
        try {
            JsonNode nodo = objectMapper.valueToTree(valores);
            protegerDatosSensibles(nodo);
            return objectMapper.writeValueAsString(nodo);
        } catch (Exception e) {
            throw new IllegalStateException("Error al convertir los valores de auditoría a JSON", e);
        }
    }

    // EVITAR GUARDAR CONTRASEÑAS Y HASHES
    private void protegerDatosSensibles(JsonNode nodo) {
        if (nodo instanceof ObjectNode objeto) {
            List<String> campos = new ArrayList<>();
            objeto.properties().forEach(entrada -> campos.add(entrada.getKey()));
            for (String campo : campos) {
                String nombre = campo.toLowerCase(Locale.ROOT);
                if (nombre.contains("password") || nombre.contains("contrasena") || nombre.contains("hash")) {
                    objeto.remove(campo);
                } else {
                    protegerDatosSensibles(objeto.get(campo));
                }
            }
        } else if (nodo != null && nodo.isArray()) {
            nodo.forEach(this::protegerDatosSensibles);
        }
    }
}