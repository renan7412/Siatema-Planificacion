package com.snpl.planificacion.api;

import com.snpl.planificacion.model.dto.IntegracionDTO;
import com.snpl.planificacion.model.entity.Integracion;
import com.snpl.planificacion.model.service.IntegracionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/integraciones")
@PreAuthorize("hasRole('ADMIN')")
public class IntegracionController {

    @Autowired
    public IntegracionService integracionService;

    @GetMapping
    public ResponseEntity<List<IntegracionDTO>> listar() {
        return ResponseEntity.ok(integracionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntegracionDTO> obtener(@PathVariable Long id) {
        IntegracionDTO dto = integracionService.obtenerPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<IntegracionDTO> crear(@Valid @RequestBody IntegracionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(integracionService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IntegracionDTO> actualizar(@PathVariable Long id, @RequestBody @Valid IntegracionDTO dto) {
        IntegracionDTO actualizada = integracionService.actualizar(id, dto);
        return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        integracionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
