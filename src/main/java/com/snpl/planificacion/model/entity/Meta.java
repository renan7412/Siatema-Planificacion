package com.snpl.planificacion.model.entity;

import com.snpl.planificacion.Enum.EstadoMeta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String nombre;

    @Size(max = 500)
    @Column(length = 500)
    private String descripcion;

    @Column(name = "responsable", length = 255)
    private String responsable;

    @Column(nullable = false) // Ajusta a true o @NotNull si BD refleja
    @Enumerated(EnumType.STRING)
    private EstadoMeta estado;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private PlanInstitucional planInstitucional;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "meta_proyecto",
                joinColumns = @JoinColumn(name = "meta_id"),
                inverseJoinColumns = @JoinColumn(name = "proyecto_id"))
    private Set<ProyectoInversion> proyectos = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "objetivo_id")
    private ObjetivosEstrategicos objetivosEstrategicos;

    public Meta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(max = 80) String getNombre() {
        return nombre;
    }

    public void setNombre(@Size(max = 80) String nombre) {
        this.nombre = nombre;
    }

    public @Size(max = 500) String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@Size(max = 500) String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public EstadoMeta getEstado() {
        return estado;
    }

    public void setEstado(EstadoMeta estado) {
        this.estado = estado;
    }

    public @NotNull PlanInstitucional getPlanInstitucional() {
        return planInstitucional;
    }

    public void setPlanInstitucional(@NotNull PlanInstitucional planInstitucional) {
        this.planInstitucional = planInstitucional;
    }

    public Set<ProyectoInversion> getProyectos() {
        return proyectos;
    }

    public void setProyectos(Set<ProyectoInversion> proyectos) {
        this.proyectos = proyectos;
    }

    public ObjetivosEstrategicos getObjetivosEstrategicos() {
        return objetivosEstrategicos;
    }

    public void setObjetivosEstrategicos(ObjetivosEstrategicos objetivosEstrategicos) {
        this.objetivosEstrategicos = objetivosEstrategicos;
    }
}
