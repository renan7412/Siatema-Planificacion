package com.snpl.planificacion.model.repository;

import com.snpl.planificacion.model.entity.HistorialCambios;
import com.snpl.planificacion.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialCambiosRepository extends JpaRepository<HistorialCambios, Long> {
    List<HistorialCambios> findByResponsable(Usuario usuario);
}
