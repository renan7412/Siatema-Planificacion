package com.snpl.planificacion.model.repository;

import com.snpl.planificacion.model.entity.PlanInstitucional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanInstitucionalRepository extends JpaRepository<PlanInstitucional, Long> {
    // carga relaciones en la misma consulta, evita el problema de lazy loading
    @Query("SELECT p FROM PlanInstitucional p LEFT JOIN FETCH p.metas LEFT JOIN FETCH p.objetivos WHERE p.id = :id")
    Optional<PlanInstitucional> findByIdConRelaciones(@Param("id") Long id);

}
