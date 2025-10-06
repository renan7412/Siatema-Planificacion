package com.snpl.planificacion.security;

import com.snpl.planificacion.model.service.JwtUtilService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtilService jwtUtil;

    @Autowired
    public JwtRequestFilter(UsuarioDetailsService userDetailsService, JwtUtilService jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /*
    * Se extrae el token de la cabecera Autorización
    * Se verifica si el token es válido o no
    * Con securityContextHolder sé auténtica al usuario
     */

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        /*final String username;
        final String token;*/

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                Claims claims = jwtUtil.extractClaims(token);

                String username = claims.getSubject();
                List<String> roles = claims.get("roles", List.class);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Convertimos roles a SimpleGrantedAuthority
                    List<GrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    // Creamos el UserDetails a mano (sin usar BD)
                    UserDetails userDetails = User
                            .withUsername(username)
                            .password("")
                            .authorities(authorities)
                            .build();

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
            } catch (JwtException e) {
                logger.warn("Token JWT invalido: {}");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (Exception e) {
                logger.error("Error inesperado procesando JWT: {}" + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}