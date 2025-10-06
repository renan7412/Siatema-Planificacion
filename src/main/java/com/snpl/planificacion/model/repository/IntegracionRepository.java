package com.snpl.planificacion.model.repository;

import com.snpl.planificacion.model.entity.Integracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntegracionRepository extends JpaRepository<Integracion, Long> {
    List<Integracion> findByNombreContainingIgnoreCase(String nombre);
}
