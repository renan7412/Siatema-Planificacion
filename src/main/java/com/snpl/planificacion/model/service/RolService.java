package com.snpl.planificacion.model.service;

import com.snpl.planificacion.model.dto.RolDTO;
import com.snpl.planificacion.model.entity.Permiso;
import com.snpl.planificacion.model.entity.Rol;
import com.snpl.planificacion.model.repository.PermisoRepository;
import com.snpl.planificacion.model.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RolService {

    @Autowired
    public RolRepository rolRepository;

    @Autowired
    public PermisoRepository permisoRepo;

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    public Rol obtenerPorId(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    public Rol crearRol(RolDTO rolDTO) {
        Rol rol = new Rol();
        rol.setNombre(rolDTO.getNombre());

        /*Set<Permiso> permisos = rolDTO.getPermisos().stream()
                map(nombrePermiso -> permisoRepo.findByNombre(nombrePermiso))
                        .orElseThrow() -> new RuntimeException("Permiso no encontrado: " + nombrePermiso)))
                .collect(Collectiors.toSet());

        rol.setPermisos(permisos);*/

        return rolRepository.save(rol);
    }

    /*public  Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }*/

    public Rol actualizarRol(Long id, Rol rol) {
        Rol existente = obtenerPorId(id);
        if (existente != null){
            existente.setNombre(rol.getNombre());
            return rolRepository.save(rol);
        }
        return null;
    }

    public void eliminarRol(Long id) {
        rolRepository.deleteById(id);
    }
}
