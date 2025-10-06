package com.snpl.planificacion.model.repository;

import com.snpl.planificacion.model.entity.ObjetivosEstrategicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjetivosEstrategicosRepository extends JpaRepository<ObjetivosEstrategicos,Long> {
}
