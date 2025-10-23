package com.snpl.planificacion.controller;

import com.snpl.planificacion.model.entity.EventoAuditoria;
import com.snpl.planificacion.model.entity.Usuario;
import com.snpl.planificacion.model.service.EventoAuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@PreAuthorize("hasRole('ADMIN')")
public class EventoAuditoriaController {

    @Autowired
    public EventoAuditoriaService eventoService;

    @GetMapping
    public List<EventoAuditoria> listaEventos() {
        return eventoService.listarEventos();
    }

    // permite el registro del evento de forma manual
    @PostMapping
    public ResponseEntity<EventoAuditoria> registrarEvento(@RequestBody EventoAuditoria evento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.guardarEvento(evento));
    }

    // buscamos por usuario
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<EventoAuditoria>> buscarPorUsuario(@PathVariable Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return ResponseEntity.ok(eventoService.buscarPorUsuario(usuario));
    }

}
