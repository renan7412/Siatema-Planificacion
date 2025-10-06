package com.snpl.planificacion.model.repository;

import com.snpl.planificacion.Enum.EstadoMeta;
import com.snpl.planificacion.model.entity.Meta;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {
    List<Meta> findByEstado(EstadoMeta estado);
}
