package com.snpl.planificacion.model.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtilService {

    /* Clase que genera y valÍda tokens JWT (EJEMPLOS)
    *  public String generateToken(UserDetails userDetails);
    * public boolean isTokenValid(String token, UserDetails userDetails);
    */

    // Clave secreta de al menos 256 bits para HS256 (mínimo 32 caracteres)
    private static final String SECRET_KEY = "$2a$10$wqyJmu9hCP5zEni6Kqb0PupbpnPjMUr4w0xHb3lDc1CfeK5r6R5qy";
    private static final long EXPIRATION_TIME = 7200000; // 2 horas
    private static final SecretKey SECRET_KEY_OBJ = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private static final Logger log = LoggerFactory.getLogger(JwtUtilService.class);

    // Genera el token con username y roles (desde UserDetails)
    public String generateToken(UserDetails userDetails) {

        List<String> rolesStr = userDetails.getAuthorities().stream()
                .map(auth-> auth.getAuthority())
                .toList();

        return Jwts.builder()  //  String token x return
                .setSubject(userDetails.getUsername())
                .claim("roles", rolesStr)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY_OBJ, SignatureAlgorithm.HS256)
                .compact();
    }

    // Método para extraer los datos existentes en el token (Extrae claims)
    public Claims extractClaims(String token) {

        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY_OBJ)
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
        } catch (ExpiredJwtException e){
            log.warn("Token expired: {}", e.getMessage());
            throw  new JwtException("Token expirado");

        } catch (JwtException | IllegalArgumentException e){
            log.error("Token inválido: {}", e.getMessage() );
            throw new JwtException("Token inválido");
        }
    }

    // Método para extraer roles desde los claims
    //@SuppressWarnings("unchecked") // Evita el warning de cast inseguro
    public List<String> extractRoles(String token) {
        Claims claims = extractClaims(token);
        return claims != null ? (List<String>) claims.get("roles") : List.of();
    }

    //  Validación del token (firma + usuario + expiración)
    public boolean validateToken(String token, UserDetails userDetails) {
        Claims claims = extractClaims(token);
        return claims != null &&
                userDetails.getUsername().equals(claims.getSubject()) &&
                !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Claims claims = extractClaims(token);
        return claims != null && claims.getExpiration().before(new Date());
    }
}
