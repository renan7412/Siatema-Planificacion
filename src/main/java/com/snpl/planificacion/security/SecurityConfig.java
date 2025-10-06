package com.snpl.planificacion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Activa a @PreAutorize
public class SecurityConfig {
    /* Clase en que se debe configurar el filtro JWT y las rutas permitidas
    * AuthenticationManager: Verifica credenciales.
    * PasswordEncoder: encriptación con BCrypt.
    * JwtAuthenticationFilter: intercepta cada petición y valída el token.(Filtro personalizado que:
    * Extrae el token del header, Usa JwtService para validarlo y Auténtica al usuario si el token es válido)
    */

    /* Configuración de seguridad principal para la aplicación
    * Desactivamos csrf(falsificación de solicitudes entre sitios) vulnerabilidad que obliga al usuario
    * a realizar acciones no deseadas, porque se utiliza autenticación basada en Token JWT, y no sesiones de navegador.
    * Se permite el acceso sin autenticación a todas las rutas bajo "/auth/**", (/auth/login)
    * Con el rol "admin" se puede acceder a cualquier ruta "/admin/**".
    * Usa sesionesPolicySTATELESS, ya que JWT no maneja sesión HTTP.
    * Se agrega un filtro personalizado (JwtRequestFilter) para validar el token antes del filtro de
    * autenticación por usuario/contraseña
     */

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean  // Utilizamos el bean por la versión de cors y and
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8080")); // Ajuste según frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF en API sin sesión; (.csrf().disable() cambiado x versión)
            .authorizeHttpRequests(auth -> auth     // "/", "/index.html","/dashboard.html","/static/**", "/css/**", "/js/**", "/images/**"

                    // Reglas para ruta que no requierern autenticación
                    .requestMatchers( "/", "/index.html","/dashboard.html","/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                    .requestMatchers("/login/**", "/auth/register", "/auth/login/**", "/favicon.ico").permitAll()  // Acceso libre a login, registro,etc

                    // Reglas para rutas que requieren roles específicos
                    .requestMatchers("/admin/**").hasRole("ADMIN") // Solo para ADMIN
                    .requestMatchers("/user/**").hasRole("USER")
                    //.requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                    //.requestMatchers("/api/metas/**").hasRole("ADMIN")
                    //.requestMatchers("/reportes/**").hasAuthority("VER_REPORTES")
                    .anyRequest().authenticated()  // Reglas, lo demás requiere autenticación
            )
            /*.formLogin(form -> form
                    .loginPage("/auth/login") // tu vista personalizada
                    .defaultSuccessUrl("/redirectByRole", true) // redirección dinámica
                    .permitAll()
            )
            .logout(logout -> logout
                     .logoutSuccessUrl("/auth/login?logout")
                     .permitAll()
            );*/
                // STATELESS ideal para APIs con jwt
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
