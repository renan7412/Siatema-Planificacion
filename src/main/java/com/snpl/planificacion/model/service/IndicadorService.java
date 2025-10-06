package com.snpl.planificacion.model.service;

import com.snpl.planificacion.model.dto.IndicadorDTO;
import com.snpl.planificacion.model.entity.Indicador;
import com.snpl.planificacion.model.repository.IndicadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndicadorService {

    private final IndicadorRepository indicadorRepo;
    public IndicadorService(IndicadorRepository indicadorRepo) {
        this.indicadorRepo = indicadorRepo;
    }

    // Listamos todos los Indicadores
    public List<IndicadorDTO> listarIndicadores() {
        return indicadorRepo.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    // Buscamos un indicador uno como DTO
    public IndicadorDTO obtenerPorId(Long id) {
        Indicador indicador = indicadorRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(
                        "Indicador no encontrado con ese id:" + id));
        return convertirADTO(indicador);
    }

    // Guardamos un nuevo indicador desde DTO
    public IndicadorDTO guardarIndicador(IndicadorDTO dto) {
        Indicador indicador = convertirAEntidad(dto);
        Indicador guardado = indicadorRepo.save(indicador);
        return convertirADTO(guardado);
    }

    // Actualizamos un indicador existente
    public IndicadorDTO actualizar(Long id, IndicadorDTO dto){
        Indicador existente = indicadorRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Indicador no encontrado con ese id: " + id));

        existente.setNombre(dto.getNombreIndicador());
        existente.setDescripcion(dto.getDescripcionIndicador());
        existente.setFuenteDatos(dto.getFuenteDatos());
        existente.setUnidadMedida(dto.getUnidadMedida());
        //existente.setPeriocidadMedicion(dto.getPeriocidadMedicion());
        return convertirADTO(indicadorRepo.save(existente));
    }

    // Eliminar
    public void eliminarIndicador(Long id) {
        if (!indicadorRepo.existsById(id)) {
            throw new IllegalArgumentException("Indicador no encontrado: " + id);
        }
        indicadorRepo.deleteById(id);
    }

    private IndicadorDTO convertirADTO(Indicador indicador) {
        IndicadorDTO dto = new IndicadorDTO();
        dto.setIdIndicador(indicador.getId());
        dto.setNombreIndicador(indicador.getNombre());
        dto.setDescripcionIndicador(indicador.getDescripcion());
        dto.setFuenteDatos(indicador.getFuenteDatos());
        dto.setUnidadMedida(indicador.getUnidadMedida());
        //dto.setPeriocidadMedicion(indicador.getPeriocidadMedicion());

        /*if (indicador.getObjetivosEstrategicos() != null) {
            dto.setObjetivosIds(
                    indicador.getObjetivosEstrategicos().stream()
                            .map(ObjetivosEstrategicos::getId)
                            .toList()
            );
        }*/
        return dto;
    }

    private Indicador convertirAEntidad(IndicadorDTO dto) {
        Indicador indicador = new Indicador();
        indicador.setId(dto.getIdIndicador()); // útil en update
        indicador.setNombre(dto.getNombreIndicador());
        indicador.setDescripcion(dto.getDescripcionIndicador());
        indicador.setFuenteDatos(dto.getFuenteDatos());
        indicador.setUnidadMedida(dto.getUnidadMedida());
        //indicador.setPeriocidadMedicion(dto.getPeriocidadMedicion());
        // OJO: aquí si quieres setear objetivosEstrategicos necesitas buscarlos en repo
        return indicador;
    }
}
