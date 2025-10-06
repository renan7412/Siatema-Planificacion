package com.snpl.planificacion.model.service;

import com.snpl.planificacion.model.entity.HistorialCambios;
import com.snpl.planificacion.model.entity.Usuario;
import com.snpl.planificacion.model.repository.HistorialCambiosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialCambiosService {

    @Autowired
    public HistorialCambiosRepository historialCambiosRepo;

    public List<HistorialCambios> listarHistorial(){
        return historialCambiosRepo.findAll();
    }

    public HistorialCambios guardar(HistorialCambios historialCambios) {
        return historialCambiosRepo.save(historialCambios);
    }

    // Permite filtrar los cambios por responsable
    public List<HistorialCambios> buscarPorUsuario(Usuario usuario) {
        return historialCambiosRepo.findByResponsable(usuario);
    }

}
