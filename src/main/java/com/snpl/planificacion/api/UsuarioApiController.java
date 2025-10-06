package com.snpl.planificacion.api;

import com.snpl.planificacion.model.dto.UsuarioDTO;
import com.snpl.planificacion.model.entity.Usuario;
import com.snpl.planificacion.model.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioApiController {

    @Autowired
    public UsuarioService usuarioService;

    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> ListarUsuariosDTO(){ // Lista Usuarios (solo Admin)
        return ResponseEntity.ok(usuarioService.listarUsuariosDTO());    //.stream()
    }

    // Método de paginación (dividir grandes conjuntos de datos), código escalable y eficiente
    @GetMapping("/paginar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UsuarioDTO>> listarUsuariosPaginado(@PageableDefault(size = 10, sort = "username") Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listarUsuariosPaginado(pageable));
    }

    // Obtener usuario por ID (solo ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id){
        UsuarioDTO usuarioDTO = usuarioService.obtenerDTOPorId(id);
        return ResponseEntity.ok(usuarioDTO);
        //return usuarioDTO != null ? ResponseEntity.ok(usuarioDTO) : ResponseEntity.notFound().build();
    }

    // Método para crear usuario (solo ADMIN)
    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario nuevo = usuarioService.crearUsuarioDTO(usuarioDTO);
        UsuarioDTO respuesta = usuarioService.convertirUsuarioDTO(nuevo);   // UsuarioMapper.toDTO(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // Actualizar usuario existente (solo ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id,
                                                     @RequestBody UsuarioDTO usuarioDTO){

        Usuario actualizado = usuarioService.actualizarUsuario(id,usuarioDTO);
        return ResponseEntity.ok(usuarioDTO);  // UsuarioMapper.toDTO(actualizado)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario Eliminado correctamente"));
    }
}
