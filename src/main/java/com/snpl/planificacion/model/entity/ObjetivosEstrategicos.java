package com.snpl.planificacion.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // Relación con ProyectoInversión
@Table(name = "objetivos_estrategicos")
public class ObjetivosEstrategicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(nullable = false, length = 200 )
    private String descripcion;

    // @Column(length = 100)
    //private String codigoObjetivos; // Relación con ProyectoInversión

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private PlanInstitucional planInstitucional;

    @ManyToMany
    @JoinTable(name = "objetivo_indicador",
                joinColumns = @JoinColumn(name = "objetivo_id"),
                inverseJoinColumns = @JoinColumn(name = "indicador_id"))
    private List<Indicador> indicadores; // = new ArrayList<>();

    @OneToMany(mappedBy = "objetivosEstrategicos", cascade = CascadeType.ALL)
    private List<Meta> metas;  // = new ArrayList<>();


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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public PlanInstitucional getPlanInstitucional() {
        return planInstitucional;
    }

    public void setPlanInstitucional(PlanInstitucional planInstitucional) {
        this.planInstitucional = planInstitucional;
    }

    public List<Indicador> getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(List<Indicador> indicadores) {
        this.indicadores = indicadores;
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }
}
