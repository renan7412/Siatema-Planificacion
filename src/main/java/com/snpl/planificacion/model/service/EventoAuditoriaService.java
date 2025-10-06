package com.snpl.planificacion.model.service;

import com.snpl.planificacion.model.entity.EventoAuditoria;
import com.snpl.planificacion.model.entity.Usuario;
import com.snpl.planificacion.model.repository.EventoAuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoAuditoriaService {

    @Autowired
    public EventoAuditoriaRepository eventoAuditoriaRepo;

    public List<EventoAuditoria> listarEventos(){
        return eventoAuditoriaRepo.findAll();
    }

    public EventoAuditoria guardarEvento(EventoAuditoria evento){
        return eventoAuditoriaRepo.save(evento);
    }

    public EventoAuditoria buscarEventoPorId(Long id){
        return eventoAuditoriaRepo.findById(id).orElse(null);
    }

    public List<EventoAuditoria> buscarPorUsuario(Usuario usuario) {
        return eventoAuditoriaRepo.findByUsuario(usuario);
    }

    public EventoAuditoria crearEvento(EventoAuditoria evento){
        return eventoAuditoriaRepo.save(evento);
    }

    public void eliminarEvento(EventoAuditoria evento){
        eventoAuditoriaRepo.delete(evento);
    }
}
