package com.snpl.planificacion.model.service;

import com.snpl.planificacion.model.dto.PlanDTO;
import com.snpl.planificacion.model.entity.Meta;
import com.snpl.planificacion.model.entity.ObjetivosEstrategicos;
import com.snpl.planificacion.model.entity.PlanInstitucional;
import com.snpl.planificacion.model.entity.Usuario;
import com.snpl.planificacion.model.repository.PlanInstitucionalRepository;
import com.snpl.planificacion.model.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanInstitucionalService {

    private final PlanInstitucionalRepository planInsRepo;

    private final UsuarioRepository usuarioRepository;

    public PlanInstitucionalService(PlanInstitucionalRepository planInsRepo, UsuarioRepository usuarioRepository) {
        this.planInsRepo = planInsRepo;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public PlanDTO obtenerPlanDTOConRelaciones(Long id) {
        PlanInstitucional plan = planInsRepo.findByIdConRelaciones(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado con ID: " + id));

        return convertirADTO(plan); // tu método ya definido
    }

    // Convertimos entidad a DTO
    private PlanDTO convertirADTO(PlanInstitucional planInstitucional) {
        PlanDTO planDTO = new PlanDTO();
        planDTO.setIdPlan(planInstitucional.getId());
        planDTO.setNombrePlan(planInstitucional.getNombre());
        planDTO.setCronograma_inicio(planInstitucional.getCronograma_inicio());
        planDTO.setCronograma_fin(planInstitucional.getCronograma_fin());
        planDTO.setVigencia(planInstitucional.getVigencia());
        planDTO.setActivo(planInstitucional.isActivo());  // != null && planInstitucional.getVigencia() > LocalDate.now().getYear());
        //planDTO.setUsuarioId(planInstitucional.getUsuario().getId());


        if (planInstitucional.getUsuario() != null) {
            planDTO.setUsuarioId(planInstitucional.getUsuario().getId());
            planDTO.setUsuarioNombre(planInstitucional.getUsuario().getNombre());
        }
        if (planInstitucional.getMetas() != null && !planInstitucional.getMetas().isEmpty()){
            planDTO.setMetaIds(planInstitucional.getMetas().stream()
                    .map(Meta::getId)
                    .collect(Collectors.toList()));
        }
        if(planInstitucional.getObjetivos() != null && !planInstitucional.getObjetivos().isEmpty()){
            planDTO.setObjetivosIds(planInstitucional.getObjetivos().stream()
                    .map(ObjetivosEstrategicos::getId)
                    .collect(Collectors.toList()));
        }
        return planDTO;
    }

    public List<PlanDTO> listarPlanes(){
        return planInsRepo.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public Page<PlanDTO> listarPlanesPaginados(Pageable pageable) {
        return planInsRepo.findAll(pageable)
                .map(this::convertirADTO);
    }

    // Crear un nuevo Plan desde DTO
    public PlanDTO crearDesdeDTO(PlanDTO dto) {
        PlanInstitucional planInstitucional = new PlanInstitucional();
        planInstitucional.setNombre(dto.getNombrePlan());
        planInstitucional.setVigencia(dto.getVigencia());
        planInstitucional.setCronograma_inicio(dto.getCronograma_inicio());
        planInstitucional.setCronograma_fin(dto.getCronograma_fin());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        planInstitucional.setUsuario(usuario);

        // planInstitucional.setUsuarioId(dto.getUsuarioId());

        PlanInstitucional guardado = planInsRepo.save(planInstitucional);
        return convertirADTO(guardado);
    }

    // Actualizar un plan existente desde DTO
    public PlanDTO actualizarDesdeDTO(Long id, PlanDTO dto) {
        PlanInstitucional existente = planInsRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan no encontrado"));

        existente.setNombre(dto.getNombrePlan());
        existente.setVigencia(dto.getVigencia());
        existente.setCronograma_inicio(dto.getCronograma_inicio());
        existente.setCronograma_fin(dto.getCronograma_fin());
        //existente.setUsuarioId(dto.getUsuarioId());
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        existente.setUsuario(usuario);

        PlanInstitucional actualizado = planInsRepo.save(existente);
        return convertirADTO(actualizado);
    }

    // Eliminar un plan
    @Transactional
    public void eliminarPlan(Long id) {    // findByIdConRelaciones(id), se asegura que las metas estén cargadas
        PlanInstitucional planInstitucional = planInsRepo.findByIdConRelaciones(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan no encontrado"));

        // Validación, evitar si tiene metas asociadas
        if (!planInstitucional.getMetas().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar el plan");
        }
        planInsRepo.delete(planInstitucional);
    }

    @Transactional(readOnly = true)
    public PlanDTO obtenerPlanDTO(Long id) {
        PlanInstitucional plan = planInsRepo.findByIdConRelaciones(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan no encontrado"));
        //plan.getMetas().size();   // Fuerza la carga
        //plan.getObjetivos().size();   // Fuerza la carga
        return convertirADTO(plan);
    }
}
