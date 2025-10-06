package com.snpl.planificacion.api;

import com.snpl.planificacion.model.dto.IndicadorDTO;
import com.snpl.planificacion.model.entity.Indicador;
import com.snpl.planificacion.model.service.IndicadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/indicador")
@PreAuthorize("hasRole('ADMIN')")
public class IndicadorController {

    private final IndicadorService indicadorService;
    public IndicadorController(IndicadorService indicadorService) {
        this.indicadorService = indicadorService;
    }

    // Listar todos
    @GetMapping("/listar")
    public ResponseEntity<List<IndicadorDTO>> ListarIndicadores(){
        return ResponseEntity.ok(indicadorService.listarIndicadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndicadorDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(indicadorService.obtenerPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<IndicadorDTO> crearIndicador(@RequestBody IndicadorDTO dto){
        IndicadorDTO creado = indicadorService.guardarIndicador(dto);
        URI location = URI.create("/api/indicadores/" + creado.getIdIndicador());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IndicadorDTO> actualizar(@PathVariable Long id, @RequestBody IndicadorDTO dto){
        return ResponseEntity.ok(indicadorService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIndicador(@PathVariable Long id){
        indicadorService.eliminarIndicador(id);
        return ResponseEntity.noContent().build();
    }

}
