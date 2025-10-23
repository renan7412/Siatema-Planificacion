package com.snpl.planificacion.controller;

import com.snpl.planificacion.model.entity.HistorialCambios;
import com.snpl.planificacion.model.entity.Usuario;
import com.snpl.planificacion.model.service.HistorialCambiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@PreAuthorize("hasRole('ADMIN')")
public class HistorialCambiosController {

    @Autowired
    public HistorialCambiosService historialService;

    @GetMapping
    public List<HistorialCambios> listarHistorial(){
        return historialService.listarHistorial();
    }

    // Se registra los cambios de forma manual
    @PostMapping
    public ResponseEntity<HistorialCambios> registrarCambios(@RequestBody HistorialCambios historial){
        return ResponseEntity.status(HttpStatus.CREATED).body(historialService.guardar(historial));
    }

    // Filtra el historial por usuario
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<HistorialCambios>> buscarPorUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario(); // Simulación de busqueda
        usuario.setId(id);
        return ResponseEntity.ok(historialService.buscarPorUsuario(usuario));
    }
}
