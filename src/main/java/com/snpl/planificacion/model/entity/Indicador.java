package com.snpl.planificacion.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "indicadores")
public class Indicador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(length = 100)
    private String FuenteDatos;

    @Column(length = 50)
    private String unidadMedida;

    @Column(length = 50)
    private String periocidadMedicion;

    @ManyToMany(mappedBy = "indicadores")
    private List<ObjetivosEstrategicos> objetivosEstrategicos;  // = new ArrayList<>();

    public Indicador() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public List<ObjetivosEstrategicos> getObjetivosEstrategicos() {
        return objetivosEstrategicos;
    }

    public void setObjetivosEstrategicos(List<ObjetivosEstrategicos> objetivosEstrategicos) {
        this.objetivosEstrategicos = objetivosEstrategicos;
    }

    public String getPeriocidadMedicion() {
        return periocidadMedicion;
    }

    public void setPeriocidadMedicion(String periocidadMedicion) {
        this.periocidadMedicion = periocidadMedicion;
    }
}
