package com.snpl.planificacion.api;

import com.snpl.planificacion.model.dto.RolDTO;
import com.snpl.planificacion.model.entity.Rol;
import com.snpl.planificacion.model.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.client.HttpClientAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RolController {

    @Autowired
    public RolService rolService;

    @Autowired
    private HttpClientAutoConfiguration httpClientAutoConfiguration;

    // Devuelve todos los roles
    @GetMapping
    public List<Rol> listarRoles(){
        return rolService.listarRoles();
    }

    // Se busca un rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerRol(@PathVariable Long id){
        Rol rol = rolService.obtenerPorId(id);
        return rol != null ? ResponseEntity.ok(rol) : ResponseEntity.notFound().build();
    }

    // Se crea un nuevo rol
    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody RolDTO rolDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.crearRol(rolDTO));
    }

    // Se actualiza el nombre del rol
    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Long id, @RequestBody Rol rol){
        Rol actualizado = rolService.actualizarRol(id, rol);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    // Se elimina un rol de la BD por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}
