package com.snpl.planificacion.model.dto;

import com.snpl.planificacion.model.entity.Indicador;

import java.util.Date;
import java.util.List;

public class ObjetivoDTO {

    private Long idObjetivo;
    private String nombreObjetivos;
    private Date fechaObjetivos;
    private String estadoObjetivos;
    private String descripcionObjetivos;

    private Long idPlan;
    private String nombrePlan;

    //private List<Long> metaIds;
    private List<Long> indicadoresIds;

    public Long getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(Long idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public String getNombreObjetivos() {
        return nombreObjetivos;
    }

    public void setNombreObjetivos(String nombreObjetivos) {
        this.nombreObjetivos = nombreObjetivos;
    }

    public Date getFechaObjetivos() {
        return fechaObjetivos;
    }

    public void setFechaObjetivos(Date fechaObjetivos) {
        this.fechaObjetivos = fechaObjetivos;
    }

    public String getEstadoObjetivos() {
        return estadoObjetivos;
    }

    public void setEstadoObjetivos(String estadoObjetivos) {
        this.estadoObjetivos = estadoObjetivos;
    }

    public String getDescripcionObjetivos() {
        return descripcionObjetivos;
    }

    public void setDescripcionObjetivos(String descripcionObjetivos) {
        this.descripcionObjetivos = descripcionObjetivos;
    }

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



    public List<Long> getIndicadoresIds() {
        return indicadoresIds;
    }

    public void setIndicadoresIds(List<Long> indicadoresIds) {
        this.indicadoresIds = indicadoresIds;
    }
}
