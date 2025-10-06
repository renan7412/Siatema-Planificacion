package com.snpl.planificacion.model.dto;

import java.util.List;

public class IndicadorDTO {
    private Long idIndicador;
    private String nombreIndicador;
    private String descripcionIndicador;
    private String FuenteDatos;
    private String unidadMedida;   // periocidadMedicion

    private List<Long> objetivosIds;

    public Long getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(Long idIndicador) {
        this.idIndicador = idIndicador;
    }

    public String getNombreIndicador() {
        return nombreIndicador;
    }

    public void setNombreIndicador(String nombreIndicador) {
        this.nombreIndicador = nombreIndicador;
    }

    public String getDescripcionIndicador() {
        return descripcionIndicador;
    }

    public void setDescripcionIndicador(String descripcionIndicador) {
        this.descripcionIndicador = descripcionIndicador;
    }

    public String getFuenteDatos() {
        return FuenteDatos;
    }

    public void setFuenteDatos(String fuenteDatos) {
        FuenteDatos = fuenteDatos;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public List<Long> getObjetivosIds() {
        return objetivosIds;
    }

    public void setObjetivosIds(List<Long> objetivosIds) {
        this.objetivosIds = objetivosIds;
    }
}
