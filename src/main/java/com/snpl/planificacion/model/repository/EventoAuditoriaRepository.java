package com.snpl.planificacion.model.repository;

import com.snpl.planificacion.model.entity.EventoAuditoria;
import com.snpl.planificacion.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoAuditoriaRepository extends JpaRepository<EventoAuditoria, Long> {
    List<EventoAuditoria> findByUsuario(Usuario usuario);
}
