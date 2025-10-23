package com.snpl.planificacion.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class ProyectoDTO {

    private Long proyectoId;

    @NotBlank
    @JsonProperty("nombre_proyecto")
    private String nombreProyecto;

    @NotBlank(message = "La descripción no debe estar vacía")
    private String descripcionProyecto;

    @NotNull(message = "El presupuesto es obligatorio")
    private BigDecimal presupuestoProyecto;

    private String tipo;
    private String entidad;
    private String estadoProyecto;  // Activo, finalizado,
    private Long objetivoEstrategicoId;
    private List<Long> metaIds;

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public BigDecimal getPresupuestoProyecto() {
        return presupuestoProyecto;
    }

    public void setPresupuestoProyecto(BigDecimal presupuestoProyecto) {
        this.presupuestoProyecto = presupuestoProyecto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(String estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public Long getObjetivoEstrategicoId() {
        return objetivoEstrategicoId;
    }

    public void setObjetivoEstrategicoId(Long objetivoEstrategicoId) {
        this.objetivoEstrategicoId = objetivoEstrategicoId;
    }

    public List<Long> getMetaIds() {
        return metaIds;
    }

    public void setMetaIds(List<Long> metaIds) {
        this.metaIds = metaIds;
    }
}
