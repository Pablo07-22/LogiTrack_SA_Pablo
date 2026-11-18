
package com.Proyecto.LogiTrack.SA.Config;

import com.Proyecto.LogiTrack.SA.Model.Usuario;
import com.Proyecto.LogiTrack.SA.Repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String username = jwtService.validateToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<Usuario> usuarios = usuarioRepository.findByUsername(username);
                if (!usuarios.isEmpty()) {
                    Usuario usuario = usuarios.get(0);
                    if (usuario.getEstado() == Usuario.Estado.ACTIVO) {
                        String rol = "ROLE_" + usuario.getRol().name();
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario.getUsername(), null, List.of(new SimpleGrantedAuthority(rol)));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
