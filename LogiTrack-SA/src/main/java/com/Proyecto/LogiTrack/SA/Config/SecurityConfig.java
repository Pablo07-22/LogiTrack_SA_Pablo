
package com.Proyecto.LogiTrack.SA.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // Rutas públicas
                        .requestMatchers("/auth/login", "/auth/registro").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Solo ADMIN
                                // Consulta limitada de usuarios activos para registrar movimientos.
// Disponible para ADMIN y EMPLEADO autenticados.
                                .requestMatchers(HttpMethod.GET, "/api/usuario/activos")
                                .hasAnyRole("ADMIN", "EMPLEADO")

// Solo ADMIN para el resto de la gestión de usuarios.
                                .requestMatchers("/api/usuario/**").hasRole("ADMIN")
                                .requestMatchers("/api/auditoria/**").hasRole("ADMIN")
                        // Registrar movimientos: ADMIN y EMPLEADO
                        .requestMatchers(HttpMethod.POST, "/api/movimiento")
                        .hasAnyRole("ADMIN", "EMPLEADO")

                        // Consultas permitidas para ambos roles
                        .requestMatchers(HttpMethod.GET,
                                "/api/ciudad/**",
                                "/api/bodega/**",
                                "/api/marca/**",
                                "/api/categoria-producto/**",
                                "/api/producto/**",
                                "/api/inventario-bodega/**",
                                "/api/movimiento/**",
                                "/api/detalle-movimiento/**"
                        ).hasAnyRole("ADMIN", "EMPLEADO")

                        // Modificaciones de datos maestros: solo ADMIN
                        .requestMatchers(HttpMethod.POST,
                                "/api/ciudad/**",
                                "/api/bodega/**",
                                "/api/marca/**",
                                "/api/categoria-producto/**",
                                "/api/producto/**",
                                "/api/inventario-bodega/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/ciudad/**",
                                "/api/bodega/**",
                                "/api/marca/**",
                                "/api/categoria-producto/**",
                                "/api/producto/**",
                                "/api/inventario-bodega/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/ciudad/**",
                                "/api/bodega/**",
                                "/api/marca/**",
                                "/api/categoria-producto/**",
                                "/api/producto/**",
                                "/api/inventario-bodega/**"
                        ).hasRole("ADMIN")

                        // Cualquier otra ruta requiere un rol válido
                        .anyRequest().hasAnyRole("ADMIN", "EMPLEADO")
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://127.0.0.1:5500",
                "http://localhost:5500"
        ));

        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
