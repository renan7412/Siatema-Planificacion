package com.snpl.planificacion.api;

import com.snpl.planificacion.model.dto.ProyectoDTO;
import com.snpl.planificacion.model.entity.ProyectoInversion;
import com.snpl.planificacion.model.service.ProyectoInversionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/proyecto")
@PreAuthorize("hasRole('ADMIN')")
public class ProyectoInversionController {

    @Autowired
    public ProyectoInversionService proyectoInverService;

    @GetMapping("/listar")
    public ResponseEntity<List<ProyectoDTO>> listarProyectoInversion() {
        List<ProyectoDTO> proyectos = proyectoInverService.listarProyectos();
        return ResponseEntity.ok(proyectos);
    }

    @GetMapping("/{proyectoId}")
    public ResponseEntity<ProyectoDTO> buscarPorId(@PathVariable Long proyectoId) {
        ProyectoInversion proyecto = proyectoInverService.buscarPorId(proyectoId);
        return ResponseEntity.ok(proyectoInverService.convertirADTO(proyecto));  // Si lanza excepción, se maneja globalmente
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearDesdeDTO(@Valid @RequestBody ProyectoDTO proyectoDTO) {
        try {
            ProyectoInversion creado = proyectoInverService.guardarDesdeDTO(proyectoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(proyectoInverService.convertirADTO(creado));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el proyecto: " + e.getMessage());
        }
        //ProyectoInversion creado = proyectoInverService.guardarDesdeDTO(proyectoDTO);
       // return ResponseEntity.status(HttpStatus.CREATED).body(proyectoInverService.convertirADTO(creado));
    }

    @PutMapping("/{proyectoId}")
    public ResponseEntity<ProyectoDTO> actualizarDesdeDTO(@PathVariable Long proyectoId,
                                                  @Valid @RequestBody ProyectoDTO proyectoDTO) {
        ProyectoInversion actualizado = proyectoInverService.actualizarDesdeDTO(proyectoId, proyectoDTO);
        return ResponseEntity.ok(proyectoInverService.convertirADTO(actualizado));
    }

    @DeleteMapping("/{proyectoId}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long proyectoId) {
        proyectoInverService.eliminar(proyectoId);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Proyecto eliminado con éxito"));
    }
}
