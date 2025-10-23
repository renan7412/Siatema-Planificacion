package com.snpl.planificacion.controller;

import com.snpl.planificacion.model.dto.JwtResponse;
import com.snpl.planificacion.model.dto.LoginRequest;
import com.snpl.planificacion.model.dto.UsuarioDTO;
import com.snpl.planificacion.model.service.AuthService;
import com.snpl.planificacion.model.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    /*
    * Clase de puntos de acceso como /login, /register
    * @PostMapping("/login")
    * public ResponseEntity<?> login(@RequestBody AuthRequest request)
    */
    @Autowired
    public JwtUtilService jwtUtilService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsuarioDTO usuarioDTO){
        String token = authService.register(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authentication(@RequestBody final LoginRequest Request) {
        String token = authService.login(Request.getUsername(), Request.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUsuariosActual(Authentication auth){
        return ResponseEntity.ok(Map.of(
                "username", auth.getName(),
                "roles", auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList())
        ));
    }
}