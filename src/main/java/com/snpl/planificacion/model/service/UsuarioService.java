package com.snpl.planificacion.model.service;

import com.snpl.planificacion.model.dto.UsuarioDTO;
import com.snpl.planificacion.model.entity.Rol;
import com.snpl.planificacion.model.entity.Usuario;
import com.snpl.planificacion.model.repository.RolRepository;
import com.snpl.planificacion.model.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository usuarioRepo;

    @Autowired
    private RolRepository rolRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO covertirUsuarioDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
    public Usuario convertirADominio(UsuarioDTO dto) {
        return modelMapper.map(dto, Usuario.class);
    }

    // Listar todos los usuarios
    public List<UsuarioDTO> listarUsuariosDTO() {
        return usuarioRepo.findAll().stream()
                .map(usuario -> {
                    UsuarioDTO dto = new UsuarioDTO();
                    dto.setId(usuario.getId());
                    dto.setUsername(usuario.getUsername());
                    dto.setNombre(usuario.getNombre());
                    dto.setApellido(usuario.getApellido());
                    dto.setEmail(usuario.getEmail());
                    dto.setRoles(usuario.getRoles().stream()
                            .map(Rol::getNombre)
                            .collect(Collectors.toSet()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Page<UsuarioDTO> listarUsuariosPaginado(Pageable pageable) {
        return usuarioRepo.findAll(pageable)
                .map(this::convertirUsuarioDTO);
    }

    public UsuarioDTO convertirUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setRoles(usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet()));
        return dto;
    }

    // Buscar por ID
    public UsuarioDTO obtenerDTOPorId(Long id) {
        Usuario usuario = usuarioRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        return convertirUsuarioDTO(usuario);
    }

    // Crear Usuario + roles
    public Usuario crearUsuarioDTO(UsuarioDTO usuarioDTO) {

        if (usuarioDTO.getRoles() == null || usuarioDTO.getRoles().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede crear el usuario");
        }
        if (usuarioRepo.existsByUsername(usuarioDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe.");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword())); // Se encripta password
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());

        // Buscar Roles en BD
        Set<Rol> roles = convertirRoles(usuarioDTO.getRoles());
        usuario.setRoles(roles);
        return usuarioRepo.save(usuario);
    }

    // Actualizar Usuario
    public Usuario actualizarUsuario(Long id, @Valid UsuarioDTO usuarioDTO) {
        Usuario existente = usuarioRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Solo actualiza los campos que llegan
        if (usuarioDTO.getNombre() != null) existente.setNombre(usuarioDTO.getNombre());
        if (usuarioDTO.getApellido() != null) existente.setApellido(usuarioDTO.getApellido());
        if (usuarioDTO.getEmail() != null) existente.setEmail(usuarioDTO.getEmail());

        // Si viene nuevo password, lo encriptamos
        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isBlank()){
            existente.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        }

        // Si se envían nuevos roles
        if (usuarioDTO.getRoles() != null && !usuarioDTO.getRoles().isEmpty()){
            Set<Rol> nuevoRoles = convertirRoles(usuarioDTO.getRoles());
            existente.setRoles(nuevoRoles);
        }
        return usuarioRepo.save(existente);
    }

    // Método para convertir nombres de roles a entidades Rol desde la BD
    public Set<Rol> convertirRoles(Set<String> nombreRoles){
        List<Rol> roles = rolRepo.findByNombreIn(nombreRoles);
        if(roles.size() != nombreRoles.size()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uno o más roles no existen eb BD");
        }
        return new HashSet<>(roles);
    }

    // Eliminar usuario
    public void eliminarUsuario(Long id) {
        if (!usuarioRepo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }
        usuarioRepo.deleteById(id);
    }
}
