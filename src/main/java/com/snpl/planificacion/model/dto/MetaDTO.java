package com.snpl.planificacion.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snpl.planificacion.Enum.EstadoMeta;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class MetaDTO {

    @JsonProperty("idMeta")
    private Long idMeta;

    @JsonProperty("nombreMeta")
    @NotBlank
    @Size(max = 80)
    private String nombreMeta;

    @Size(max = 500)
    private String descriptionMeta;

    private String responsableMeta;

    @NotNull
    private EstadoMeta estado;

    @NotNull
    private Long idPlan;
    private String nombrePlan;

    private Long objetivoId;
    private String objetivoNombre;

    @ManyToMany(mappedBy = "meta", fetch = FetchType.EAGER)
    private List<Long> proyectoIds;  // (ID) de los proyectos vinculados

    public Long getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(Long idMeta) {
        this.idMeta = idMeta;
    }

    public @NotBlank @Size(max = 80) String getNombreMeta() {
        return nombreMeta;
    }

    public void setNombreMeta(@NotBlank @Size(max = 80) String nombreMeta) {
        this.nombreMeta = nombreMeta;
    }

    public @Size(max = 500) String getDescriptionMeta() {
        return descriptionMeta;
    }

    public void setDescriptionMeta(@Size(max = 500) String descriptionMeta) {
        this.descriptionMeta = descriptionMeta;
    }

    public String getResponsableMeta() {
        return responsableMeta;
    }

    public void setResponsableMeta(String responsableMeta) {
        this.responsableMeta = responsableMeta;
    }

    public EstadoMeta getEstado() {
        return estado;
    }

    public void setEstado( EstadoMeta estado) {
        this.estado = estado;
    }

    public @NotNull Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(@NotNull Long idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public Long getObjetivoId() {
        return objetivoId;
    }

    public void setObjetivoId(Long objetivoId) {
        this.objetivoId = objetivoId;
    }

    public String getObjetivoNombre() {
        return objetivoNombre;
    }

    public void setObjetivoNombre(String objetivoNombre) {
        this.objetivoNombre = objetivoNombre;
    }

    public List<Long> getProyectoIds() {
        return proyectoIds;
    }

    public void setProyectoIds(List<Long> proyectoIds) {
        this.proyectoIds = proyectoIds;
    }
}
