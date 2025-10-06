package com.snpl.planificacion.api;

import com.snpl.planificacion.model.dto.ObjetivoDTO;
import com.snpl.planificacion.model.entity.ObjetivosEstrategicos;
import com.snpl.planificacion.model.service.ObjetivosEstrategicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/objetivos")
public class ObjetivosEstrategicosController {

    public final ObjetivosEstrategicosService objetivosEstratService;


    public ObjetivosEstrategicosController(ObjetivosEstrategicosService objetivosEstratService, ObjetivosEstrategicosService objetivosEstrategicosService) {
        this.objetivosEstratService = objetivosEstratService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ObjetivoDTO>> listarObjetivos() {
        List<ObjetivoDTO> objetivos = objetivosEstratService.listarObjetivos()
                .stream()
                .filter(Objects::nonNull)
                //.map(objetivosEstratService::convertirADTO)
                .toList();
        return ResponseEntity.ok(objetivos);
    }

    // Método para obtener objetivos estratégicos a través de su ID
    @GetMapping("/{id}")
    public ResponseEntity<ObjetivoDTO> obtenerPlan(@PathVariable Long id){
        ObjetivosEstrategicos objetivo = objetivosEstratService.obtenerPorId(id);
        return ResponseEntity.ok(objetivosEstratService.convertirADTO(objetivo));
    }

    // Método con el cual se crea un nuevo objetivo estratégico
    @PostMapping("/crear")
    public ResponseEntity<ObjetivoDTO> crearObjetivo(@RequestBody ObjetivosEstrategicos objetivo) {
        ObjetivosEstrategicos creado = objetivosEstratService.crearObjetivo(objetivo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objetivosEstratService.convertirADTO(creado));
    }

    //  Método con el cual se actualiza los datos del objetivo estratégico existente
    @PutMapping("/{id}")
    public ResponseEntity<ObjetivoDTO> actualizarObjetivo(
            @PathVariable Long id,
            @RequestBody ObjetivosEstrategicos objetivo){
        ObjetivosEstrategicos actualizado = objetivosEstratService.actualizarObjetivos(id, objetivo);
        return ResponseEntity.ok(objetivosEstratService.convertirADTO(actualizado));
    }

    // Método para realizar la eliminación de los datos de los objetivos estratégicos por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarObjetivo(@PathVariable Long id) {
        objetivosEstratService.eliminarObjetivos(id);
        return ResponseEntity.noContent().build();
    }


}
