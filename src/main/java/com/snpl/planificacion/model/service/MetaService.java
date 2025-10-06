package com.snpl.planificacion.model.service;

import com.snpl.planificacion.Enum.EstadoMeta;
import com.snpl.planificacion.model.dto.MetaDTO;
import com.snpl.planificacion.model.entity.Meta;
import com.snpl.planificacion.model.entity.PlanInstitucional;
import com.snpl.planificacion.model.entity.ProyectoInversion;
import com.snpl.planificacion.model.repository.MetaRepository;
import com.snpl.planificacion.model.repository.PlanInstitucionalRepository;
import com.snpl.planificacion.model.repository.ProyectoInversionRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class  MetaService {

    @Autowired
    public MetaRepository metaRepo;

    @Autowired
    private PlanInstitucionalRepository planInstitucionalRepo;

    @Autowired
    private ProyectoInversionRepository proyectoInversionRepo;

    public MetaDTO crearDesdeDTO(MetaDTO metaDTO) {
        Meta nuevaMeta = new Meta();
        nuevaMeta.setNombre(metaDTO.getNombreMeta());
        nuevaMeta.setDescripcion(metaDTO.getDescriptionMeta());
        nuevaMeta.setResponsable(metaDTO.getResponsableMeta());
        nuevaMeta.setEstado(metaDTO.getEstado());

        PlanInstitucional planInstitucional = planInstitucionalRepo.findById(metaDTO.getIdPlan())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan no encontrado"));
        nuevaMeta.setPlanInstitucional(planInstitucional);

        if (metaDTO.getProyectoIds() != null){
            Set<ProyectoInversion> proyectos = new HashSet<>();
            for (Long id : metaDTO.getProyectoIds()) {
                ProyectoInversion proyecto = proyectoInversionRepo.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proyecto no encontrado"));
                proyectos.add(proyecto);
            }
            nuevaMeta.setProyectos(proyectos);
        }
        Meta guardada = metaRepo.save(nuevaMeta);
        return convertirADTO(guardada);
    }
    public MetaDTO convertirADTO(Meta meta) {
        MetaDTO metaDTO = new MetaDTO();
        metaDTO.setIdMeta(meta.getId());
        metaDTO.setNombreMeta(meta.getNombre());
        metaDTO.setDescriptionMeta(meta.getDescripcion());
        metaDTO.setResponsableMeta(meta.getResponsable());
        metaDTO.setEstado(meta.getEstado());
        //metaDTO.setIdPlan(meta.getPlanInstitucional().getId());
        //metaDTO.setNombrePlan(meta.getPlanInstitucional().getNombre());

        List<Long> proyectoIds = meta.getProyectos().stream()
                .map(ProyectoInversion::getId)
                .collect(Collectors.toList());
        metaDTO.setProyectoIds(proyectoIds);

        if (meta.getPlanInstitucional() != null){
            metaDTO.setIdPlan(meta.getPlanInstitucional().getId());
            metaDTO.setNombrePlan(meta.getPlanInstitucional().getNombre());
        } else {
            metaDTO.setIdPlan(null);
            metaDTO.setNombrePlan("Sin plan asignado");
        }

        // Extrae los IDs de una lista
        if(meta.getProyectos() != null && !meta.getProyectos().isEmpty()){
            metaDTO.setProyectoIds(meta.getProyectos().stream()
                    .map(ProyectoInversion::getId)
                    .collect(Collectors.toList()));
        }
        return metaDTO;
    }

    public List<MetaDTO> listarMetas(){
        return metaRepo.findAll().stream()
                .map(meta -> {
                    MetaDTO metaDTO = new MetaDTO();
                    metaDTO.setIdMeta(meta.getId());
                    metaDTO.setNombreMeta(meta.getNombre());
                    metaDTO.setDescriptionMeta(meta.getDescripcion());
                    metaDTO.setResponsableMeta(meta.getResponsable());
                    metaDTO.setEstado(meta.getEstado());
                    metaDTO.setIdPlan(meta.getPlanInstitucional().getId());
                    return metaDTO;
                })
                .collect(Collectors.toList());
    }

    public MetaDTO obtenerPorId(Long idMeta){
        Meta meta = metaRepo.findById(idMeta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meta no encontrada"));
        Hibernate.initialize(meta.getProyectos());   // Inicializamos proyectos
        return convertirADTO(meta);
    }

    public Meta guardarDesdeDTO(MetaDTO metaDTO){
        Meta nuevaMeta = new Meta();
        nuevaMeta.setNombre(metaDTO.getNombreMeta());
        nuevaMeta.setDescripcion(metaDTO.getDescriptionMeta());
        nuevaMeta.setResponsable(metaDTO.getResponsableMeta());
        nuevaMeta.setEstado(metaDTO.getEstado());

        PlanInstitucional planInstitucional =
                planInstitucionalRepo.findById(metaDTO.getIdPlan())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan no encontrado"));
        nuevaMeta.setPlanInstitucional(planInstitucional);

        if (metaDTO.getProyectoIds() != null) {
            Set<ProyectoInversion> proyectos = metaDTO.getProyectoIds().stream()
                    .map(id -> proyectoInversionRepo.findById(id)
                       .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proyecto no encontrado: " + id)))
                    .collect(Collectors.toSet());
            nuevaMeta.setProyectos(proyectos);
        }
        Meta guardada = metaRepo.save(nuevaMeta);
        convertirADTO(guardada);
        return(guardada);
    }

    public Meta actualizar(Long idMeta, MetaDTO dto) {
        Meta existente = metaRepo.findById(idMeta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meta no encontrada"));

        // Actualizar campos
        existente.setNombre(dto.getNombreMeta());
        existente.setDescripcion(dto.getDescriptionMeta());
        existente.setResponsable(dto.getResponsableMeta());
        existente.setEstado(dto.getEstado());

        // Se asocia al plan institucional
        if (dto.getIdPlan() != null) {
            PlanInstitucional planInstitucional =
                    planInstitucionalRepo.findById(dto.getIdPlan())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan no encontrado"));
            existente.setPlanInstitucional(planInstitucional);
        }

        // Se asocia a proyectos
        if (dto.getProyectoIds() != null && !dto.getProyectoIds().isEmpty()) {
            Set<ProyectoInversion> proyectos = dto.getProyectoIds().stream()
                    .map(idProyecto -> proyectoInversionRepo.findById(idProyecto)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proyecto no encontrado: " + idProyecto)))
                    .collect(Collectors.toSet());
            existente.setProyectos(proyectos);
        }
        return metaRepo.save(existente);
    }

    @Transactional
    public void eliminarMeta(Long id) {
        Meta meta = metaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meta no encontrada"));
        // Validamos con estado
        if (meta.getEstado() == EstadoMeta.cumplido){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No se puede eliminar una meta cumplida");
        }

        // Validamos si hay relación con Planes
        if (meta.getPlanInstitucional() != null && meta.getPlanInstitucional().isActivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La meta está vinculada a un plan activo");
        }
        metaRepo.delete(meta);
    }
}
