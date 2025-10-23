package com.snpl.planificacion.controller;

import com.snpl.planificacion.model.dto.PlanDTO;
import com.snpl.planificacion.model.service.PlanInstitucionalService;
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

@RestController
@RequestMapping("/api/planes")
@PreAuthorize("hasRole('ADMIN')")
public class PlanInstitucionalController {

    // @Autowired inyección por campo
    private final PlanInstitucionalService planService;

    // inyección por constructor en campo
    @Autowired
    public PlanInstitucionalController(PlanInstitucionalService planService) {
        this.planService = planService;
    }

    // Método para realizar el listado de los planes Institucionales
    @GetMapping("/listar")
    public ResponseEntity<List<PlanDTO>> listarPlanes(){
        return ResponseEntity.ok(planService.listarPlanes());
    }

    // Método para listar planes con paginación
    @GetMapping("/paginar")
    public ResponseEntity<Page<PlanDTO>> listarPaginados(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(planService.listarPlanesPaginados(pageable));
    }

    // Método con el cual se crea un plan nuevo
    @PostMapping("/crear")
    public ResponseEntity<PlanDTO> crearPlan(@RequestBody @Valid PlanDTO planDTO) {
        PlanDTO creado = planService.crearDesdeDTO(planDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Método para obtener un plan a través de su ID
    @GetMapping("/{idPlan}")
    public ResponseEntity<PlanDTO> obtenerPlanDTO(@PathVariable Long idPlan) {
        PlanDTO planDTO = planService.obtenerPlanDTOConRelaciones(idPlan);
        return ResponseEntity.ok(planDTO);
    }

    //  Método con el cual se actualizan los datos de un plan existente
    @PutMapping("/{idPlan}")
    public ResponseEntity<PlanDTO> actualizarPlan(@PathVariable Long idPlan,
                                                  @RequestBody @Valid PlanDTO planDTO){
        PlanDTO actualizado = planService.actualizarDesdeDTO(idPlan, planDTO);
        return ResponseEntity.ok(actualizado);
   }

   // Método para realizar la eliminación de los datos del plan por ID
    @DeleteMapping("/{idPlan}")
    public ResponseEntity<Void> eliminarPlan(@PathVariable Long idPlan) {
        planService.eliminarPlan(idPlan);
        return ResponseEntity.noContent().build();
    }
}
