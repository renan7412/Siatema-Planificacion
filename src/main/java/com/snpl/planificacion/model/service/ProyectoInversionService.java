package com.snpl.planificacion.model.service;

import com.snpl.planificacion.model.dto.ProyectoDTO;
import com.snpl.planificacion.model.entity.ObjetivosEstrategicos;
import com.snpl.planificacion.model.entity.ProyectoInversion;
import com.snpl.planificacion.model.repository.ObjetivosEstrategicosRepository;
import com.snpl.planificacion.model.repository.ProyectoInversionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectoInversionService {

    private final ObjetivosEstrategicosRepository objetivosRepo;
    private final ProyectoInversionRepository proyectoRepo;

    public ProyectoInversionService(ProyectoInversionRepository proyectoRepo,
                                    ObjetivosEstrategicosRepository objetivosRepo) { // Const
        this.proyectoRepo = proyectoRepo;
        this.objetivosRepo = objetivosRepo;
    }

    public List<ProyectoDTO> listarProyectos(){
        return proyectoRepo.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ProyectoInversion buscarPorId(Long id){
        return proyectoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado por ID: " + id));
    }

    public ProyectoInversion guardarDesdeDTO(ProyectoDTO dto) {

        if (dto.getObjetivoEstrategicoId() == null) {   // Validamos
            throw new IllegalArgumentException("El proyecto debe estar asociado a un objetivo estratégico");
        }

        ObjetivosEstrategicos objetivo = objetivosRepo.findById(dto.getObjetivoEstrategicoId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Objetivo estratégico no encontrado con ID: " + dto.getObjetivoEstrategicoId()
                ));

        ProyectoInversion proyecto = new ProyectoInversion();
        proyecto.setNombreProyecto(dto.getNombreProyecto());
        proyecto.setDescripcion(dto.getDescripcionProyecto());
        proyecto.setPresupuesto(dto.getPresupuestoProyecto());
        proyecto.setTipo(dto.getTipo());
        proyecto.setEntidad(dto.getEntidad());
        proyecto.setObjetivoEstrategico(objetivo);

        return proyectoRepo.save(proyecto);
    }

    public ProyectoDTO crearDesdeDTO(ProyectoInversion proyecto) {
        ProyectoDTO proyectoDTO = new ProyectoDTO();
        proyectoDTO.setNombreProyecto(proyecto.getNombreProyecto());
        proyectoDTO.setDescripcionProyecto(proyecto.getDescripcion());
        proyectoDTO.setPresupuestoProyecto(proyecto.getPresupuesto());
        proyectoDTO.setTipo(proyecto.getTipo());
        proyectoDTO.setEntidad(proyecto.getEntidad());
        proyectoDTO.setObjetivoEstrategicoId(proyecto.getObjetivoEstrategico().getId());
        return proyectoDTO;
    }

    public ProyectoInversion actualizarDesdeDTO(Long proyectoId, ProyectoDTO proyectoDTO){
        ProyectoInversion existente = buscarPorId(proyectoId);

        if (proyectoDTO.getObjetivoEstrategicoId() != null) {
            ObjetivosEstrategicos objetivo = objetivosRepo.findById(proyectoDTO.getObjetivoEstrategicoId())
                    .orElseThrow(() -> new EntityNotFoundException("Objetivo estratégico no encontrado con ID: " + proyectoDTO.getObjetivoEstrategicoId()));
            existente.setObjetivoEstrategico(objetivo);
        }

        existente.setNombreProyecto(proyectoDTO.getNombreProyecto());
        existente.setDescripcion(proyectoDTO.getDescripcionProyecto());
        existente.setPresupuesto(proyectoDTO.getPresupuestoProyecto());
        existente.setTipo(proyectoDTO.getTipo());
        existente.setEntidad(proyectoDTO.getEntidad());

        return proyectoRepo.save(existente);
    }

    public void eliminar(Long proyectoId){
        ProyectoInversion proyecto = buscarPorId(proyectoId); // S i no existe lanza excepción
        proyectoRepo.delete(proyecto);
    }

    public ProyectoDTO convertirADTO(ProyectoInversion proyecto){
        ProyectoDTO proyectoDTO = new ProyectoDTO();
        proyectoDTO.setProyectoId(proyecto.getId());
        proyectoDTO.setNombreProyecto(proyecto.getNombreProyecto());
        proyectoDTO.setDescripcionProyecto(proyecto.getDescripcion());
        proyectoDTO.setPresupuestoProyecto(proyecto.getPresupuesto());
        proyectoDTO.setTipo(proyecto.getTipo());
        proyectoDTO.setEntidad(proyecto.getEntidad());

        if (proyecto.getObjetivoEstrategico() != null){
            proyectoDTO.setObjetivoEstrategicoId(proyecto.getObjetivoEstrategico().getId());
        }
        return proyectoDTO;
    }

    // Convertir DTO a Entidad
    /*public ProyectoInversion convertirAEntidad(ProyectoDTO dto, ObjetivosEstrategicos objetivo) {
        ProyectoInversion proyecto = new ProyectoInversion();
        proyecto.setId(dto.getProyectoId()); // opcional si es update
        proyecto.setNombreProyecto(dto.getNombreProyecto());
        proyecto.setDescripcion(dto.getDescripcionProyecto());
        proyecto.setPresupuesto(dto.getPresupuestoProyecto());
        proyecto.setTipo(dto.getTipo());
        proyecto.setEntidad(dto.getEntidad());
        proyecto.setObjetivoEstrategico(objetivo);
        return proyecto;
    }*/
}
