package com.snpl.planificacion.model.service;

import com.snpl.planificacion.model.dto.ObjetivoDTO;
import com.snpl.planificacion.model.entity.Indicador;
import com.snpl.planificacion.model.entity.Meta;
import com.snpl.planificacion.model.entity.ObjetivosEstrategicos;
import com.snpl.planificacion.model.repository.ObjetivosEstrategicosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObjetivosEstrategicosService {

    private final ObjetivosEstrategicosRepository objetivosEstratRep;

    public ObjetivosEstrategicosService (ObjetivosEstrategicosRepository objetivosEstratRep) {
        this.objetivosEstratRep = objetivosEstratRep;
    }

    public List<ObjetivoDTO> listarObjetivos() {
        return objetivosEstratRep.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ObjetivosEstrategicos obtenerPorId(Long id){
        return objetivosEstratRep.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Objetivo no encontrado con ID: " + id));
    }

    public ObjetivosEstrategicos crearObjetivo(ObjetivosEstrategicos objetivo){
        if (objetivo.getNombre() == null || objetivo.getNombre().isBlank()){
            throw new IllegalArgumentException("El objetivo debe tener un nombre válido");
        }
        return objetivosEstratRep.save(objetivo);
    }

    public ObjetivosEstrategicos actualizarObjetivos(Long id, ObjetivosEstrategicos objetivos) {
        ObjetivosEstrategicos existente = obtenerPorId(id);
        existente.setNombre(objetivos.getNombre());
        existente.setDescripcion(objetivos.getDescripcion());
        existente.setPlanInstitucional(objetivos.getPlanInstitucional());
        existente.setFecha(objetivos.getFecha());
        existente.setEstado(objetivos.getEstado());
        return objetivosEstratRep.save(existente);
    }

    public void eliminarObjetivos(Long id){
        if (!objetivosEstratRep.existsById(id)){
            throw new EntityNotFoundException("No se puede eliminar. Objetivo no encontrado con ID: " + id);
        }
        objetivosEstratRep.deleteById(id);
    }

    public ObjetivoDTO convertirADTO(ObjetivosEstrategicos objetivo){
        ObjetivoDTO objetivoDTO = new ObjetivoDTO();
        objetivoDTO.setIdObjetivo(objetivo.getId());
        objetivoDTO.setNombreObjetivos(objetivo.getNombre());
        objetivoDTO.setDescripcionObjetivos(objetivo.getDescripcion());
        objetivoDTO.setFechaObjetivos(objetivo.getFecha());
        objetivoDTO.setEstadoObjetivos(objetivo.getEstado());

        if (objetivo.getPlanInstitucional() != null){
            objetivoDTO.setIdPlan(objetivo.getPlanInstitucional().getId());
            objetivoDTO.setNombrePlan(objetivo.getPlanInstitucional().getNombre());
        }

        if (objetivo.getMetas() != null && !objetivo.getMetas().isEmpty()){
            objetivoDTO.setMetaIds(
                    objetivo.getMetas().stream()
                    .map(Meta::getId)
                    .collect(Collectors.toList())
            );
        }

        if (objetivo.getIndicadores() != null && !objetivo.getIndicadores().isEmpty()) {
            objetivoDTO.setIndicadoresIds(
                    objetivo.getIndicadores().stream()
                            .map(Indicador::getId)
                            .collect(Collectors.toList())
            );
        }
        return objetivoDTO;
    }
}
