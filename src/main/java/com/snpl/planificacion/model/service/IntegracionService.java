package com.snpl.planificacion.model.service;

import com.snpl.planificacion.model.dto.IntegracionDTO;
import com.snpl.planificacion.model.dto.IntegracionRequestDTO;
import com.snpl.planificacion.model.dto.IntegracionResponseDTO;
import com.snpl.planificacion.model.entity.Integracion;
import com.snpl.planificacion.model.entity.Usuario;
import com.snpl.planificacion.model.repository.IntegracionRepository;
import com.snpl.planificacion.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegracionService {

    @Autowired
    private IntegracionRepository integracionRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public List<IntegracionDTO> listar() {
        return integracionRepo.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public IntegracionDTO obtenerPorId(Long id) {
        return integracionRepo.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    public IntegracionDTO guardar(IntegracionDTO dto) {
        Integracion entidad = convertirAEntidad(dto);
        return convertirADTO(integracionRepo.save(entidad));
    }

    public IntegracionDTO actualizar(Long id, IntegracionDTO dto) {
        Integracion existente = integracionRepo.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(dto.getNombreIntegracion());
            existente.setApi(dto.getApi());

            // Convertimos ID en entidad
            Usuario usuario = usuarioRepo.findById(dto.getUsuarioId()).orElse(null);
            existente.setUsuario(usuario);

            return convertirADTO(integracionRepo.save(existente));
        }
        return null;
    }

    public void eliminar(Long id) {
        integracionRepo.deleteById(id);
    }

    // Métodos de conversión
    private IntegracionDTO convertirADTO(Integracion entidad) {
        IntegracionDTO dto = new IntegracionDTO();
        dto.setIdIntegracion(entidad.getId());
        dto.setNombreIntegracion(entidad.getNombre());
        dto.setApi(entidad.getApi());
        dto.setUsuarioId(
                entidad.getUsuario() != null ? entidad.getUsuario().getId() : null
        );
        return dto;
    }

    private Integracion convertirAEntidad(IntegracionDTO dto) {
        Integracion entidad = new Integracion();
        entidad.setId(dto.getIdIntegracion());
        entidad.setNombre(dto.getNombreIntegracion());
        entidad.setApi(dto.getApi());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepo.findById(dto.getUsuarioId()).orElse(null);
            entidad.setUsuario(usuario);
        }
        return entidad;
    }
}
