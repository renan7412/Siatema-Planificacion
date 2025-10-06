package com.snpl.planificacion.model.dto;

import java.util.Set;

public class RolDTO {
    private String nombre;
    private Set<String> permisos;

    public RolDTO() {

    }
    public RolDTO(String nombre, Set<String> permisos) {
        this.nombre = nombre;
        this.permisos = permisos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<String> permisos) {
        this.permisos = permisos;
    }
}
