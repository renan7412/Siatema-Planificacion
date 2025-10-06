package com.snpl.planificacion.mapper;

import com.snpl.planificacion.model.dto.UsuarioDTO;
import com.snpl.planificacion.model.entity.Rol;
import com.snpl.planificacion.model.entity.Usuario;

import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioMapper {

    // De entidad a DTO
    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setRoles(usuario.getRoles() != null
                ? usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet())
                : Set.of());
        return dto;
    }

    // De DTO a entidad
    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setUsername(dto.getUsername());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        // La contraseña y roles se manejan en el servicio (seguridad)
        return usuario;
    }
}
