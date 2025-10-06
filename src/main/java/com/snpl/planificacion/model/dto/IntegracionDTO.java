package com.snpl.planificacion.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class IntegracionDTO {
    private Long idIntegracion;

    @NotBlank(message = "El nombre de la integración es obligatorio")
    private String nombreIntegracion;

    @NotBlank(message = "El campo API no puede estar vacío")
    private String api;

    @NotNull(message = "Debe asociar un usuario")
    private Long usuarioId;
    // estado, tipo, sistemaOrigen


    public Long getIdIntegracion() {
        return idIntegracion;
    }

    public void setIdIntegracion(Long idIntegracion) {
        this.idIntegracion = idIntegracion;
    }

    public String getNombreIntegracion() {
        return nombreIntegracion;
    }

    public void setNombreIntegracion(String nombreIntegracion) {
        this.nombreIntegracion = nombreIntegracion;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public @NotNull(message = "Debe asociar un usuario") Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(@NotNull(message = "Debe asociar un usuario") Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
