package com.snpl.planificacion.model.service;

import com.snpl.planificacion.model.dto.UsuarioDTO;
import com.snpl.planificacion.model.entity.Permiso;
import com.snpl.planificacion.model.entity.Rol;
import com.snpl.planificacion.model.entity.Usuario;
import com.snpl.planificacion.model.repository.RolRepository;
import com.snpl.planificacion.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtilService jwtUtil;

    @Autowired
    public RolRepository rolRepository;

    @Autowired
    public UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Registro de nuevo usuario
    public String register(UsuarioDTO usuario) {

        // Verifíca sí el usuario existe
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuario ya existe");
        }
        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Se requiere al menos un rol para el usuario");
        }

        // Creamos entidad para usuarios desde DTO
        Usuario nuevo = new Usuario();
        nuevo.setUsername(usuario.getUsername());
        nuevo.setPassword(passwordEncoder.encode(usuario.getPassword()));
        nuevo.setNombre(usuario.getNombre());
        nuevo.setApellido(usuario.getApellido());
        nuevo.setEmail(usuario.getEmail());

        // Convertimos nombres de roles en entidad rol
        Set<Rol> roles = usuario.getRoles().stream()
                .map(nombre -> rolRepository.findByNombre(nombre.toLowerCase())
                        .orElseThrow(() -> new IllegalArgumentException("Rol no valido:" + nombre)))
                .collect(Collectors.toSet());
        nuevo.setRoles(roles);

        usuarioRepository.save(nuevo);

        // Extraemos permisos como authorities para el token
        Set<String> authorities = roles.stream()
                .flatMap(rol -> Stream.concat(
                        Stream.of("ROLE_" + rol.getNombre()),  // Rol como authority
                        rol.getPermisos().stream().map(Permiso::getNombre)  // Nombre Permisos
                ))
                .collect(Collectors.toSet());

        // Creamos UserDetails y generamos el token
        UserDetails userDetails = User
                .withUsername(nuevo.getUsername())
                .password(nuevo.getPassword())
                .authorities(authorities.toArray(new String[0]))
                /*.authorities(nuevo.getRoles().stream()
                        .flatMap(rol -> rol.getPermisos().stream())
                        .map(Permiso::getNombre) // Nombres del Rol
                        .toArray(String[]::new))*/
                .build();
        return jwtUtil.generateToken(userDetails);
    }

    // Login (autenticación)
    public String login(String username, String plainPassword) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Validamos contraseña
        if (!passwordEncoder.matches(plainPassword, usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas");
        }

        // Obtener permisos de los roles del usuario
        Set<String> authorities = usuario.getRoles().stream()
                .flatMap(rol -> Stream.concat(
                        Stream.of("ROLE_" + rol.getNombre()),
                        rol.getPermisos().stream().map(Permiso::getNombre)
                ))
                .collect(Collectors.toSet());

        // Creamos el userDetails con permisos y roles
        UserDetails userDetails = User
                .withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(authorities.toArray(new String[0]))
                /*.authorities(usuario.getRoles().stream()
                        .flatMap(rol -> rol.getPermisos().stream())
                        .map(Permiso::getNombre) // Nombres del Rol
                        .toArray(String[]::new))*/
                .build();
        return jwtUtil.generateToken(userDetails);

    }
}