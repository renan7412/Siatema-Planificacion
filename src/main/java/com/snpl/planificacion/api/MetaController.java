package com.snpl.planificacion.api;

import com.snpl.planificacion.model.dto.MetaDTO;
import com.snpl.planificacion.model.entity.Meta;
import com.snpl.planificacion.model.repository.PlanInstitucionalRepository;
import com.snpl.planificacion.model.service.MetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metas")
public class MetaController {

    @Autowired
    public MetaService metaService;

    @Autowired
    private PlanInstitucionalRepository planInstitucionalRepo;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MetaDTO>> listar(){
        return ResponseEntity.ok(metaService.listarMetas());
    }

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetaDTO> crear(@RequestBody @Valid MetaDTO meta){
        MetaDTO creada = metaService.crearDesdeDTO(meta);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{idMeta}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetaDTO> obtenerPorId(@PathVariable Long idMeta){
        MetaDTO meta = metaService.obtenerPorId(idMeta);
        if (meta == null) {
            return ResponseEntity.notFound().build();
        }
        MetaDTO dto = metaService.convertirADTO(new Meta());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{idMeta}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetaDTO> actualizar(@PathVariable Long idMeta, @RequestBody MetaDTO metaDTO){
        Meta actualizada = metaService.actualizar(idMeta, metaDTO);
        MetaDTO dto = metaService.convertirADTO(actualizada);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{idMeta}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarMeta(@PathVariable Long idMeta){
        metaService.eliminarMeta(idMeta);
        return ResponseEntity.noContent().build();
    }
}
