package com.snpl.planificacion.model.repository;

import com.snpl.planificacion.model.entity.Indicador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndicadorRepository extends JpaRepository<Indicador, Long> {
    List<Indicador> findByNombreContainingIgnoreCase(String nombre);
}
