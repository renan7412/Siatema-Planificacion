package com.snpl.planificacion.security;

import com.snpl.planificacion.model.entity.Usuario;
import com.snpl.planificacion.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                        .flatMap(rol -> rol.getPermisos().stream())
                        .map(permiso -> new SimpleGrantedAuthority(permiso.getNombre()))
                        .collect(Collectors.toList());

            /*List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRoles().stream()));
            String authorities = usuario.getRoles()
                .stream()
                .map(Rol::getNombre)
                .collect(Collectors.joining(", "));*/

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                authorities
        );
    }
}
