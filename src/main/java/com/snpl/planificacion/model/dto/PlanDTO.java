package com.snpl.planificacion.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class PlanDTO {

    @NotNull(message = "Debe seleccionar un plan institucional")
    private Long idPlan;

    private String nombrePlan;

    @JsonProperty("cronogramaInicio")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cronograma_inicio;

    @JsonProperty("cronogramaFin")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cronograma_fin;

    private Integer vigencia; // año o periodo

    private boolean activo;  // check, inactivo no check

    @NotNull
    private Long usuarioId;
    private String usuarioNombre;

    private List<Long> metaIds;
    private List<Long> objetivosIds;

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public LocalDate getCronograma_inicio() {
        return cronograma_inicio;
    }

    public void setCronograma_inicio(LocalDate cronograma_inicio) {
        this.cronograma_inicio = cronograma_inicio;
    }

    public LocalDate getCronograma_fin() {
        return cronograma_fin;
    }

    public void setCronograma_fin(LocalDate cronograma_fin) {
        this.cronograma_fin = cronograma_fin;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public List<Long> getMetaIds() {
        return metaIds;
    }

    public void setMetaIds(List<Long> metaIds) {
        this.metaIds = metaIds;
    }

    public List<Long> getObjetivosIds() {
        return objetivosIds;
    }

    public void setObjetivosIds(List<Long> objetivosIds) {
        this.objetivosIds = objetivosIds;
    }
}
