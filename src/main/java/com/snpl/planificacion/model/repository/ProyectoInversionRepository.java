package com.snpl.planificacion.model.repository;

import com.snpl.planificacion.model.entity.ProyectoInversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoInversionRepository extends JpaRepository<ProyectoInversion, Long> {
    List<ProyectoInversion> findByTipo(String tipo);
}
