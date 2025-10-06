package com.snpl.planificacion.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "planes_institucionales")
public class PlanInstitucional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean activo;

    @Column(nullable = false, length = 80)
    private String nombre;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cronograma_inicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cronograma_fin;

    // orphanRemoval = true (borra metas asociadas al plan y se puede perder información "cuidado")
    // fetch = FetchType.EAGER: esto puede afectar al rendimiento si las listas son grandes o se realiza demasiadas consultas
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "planInstitucional", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Meta> metas;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "planInstitucional", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ObjetivosEstrategicos> objetivos;

    private Integer vigencia;

    @ManyToOne
    @JsonProperty("usuario_id")
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public PlanInstitucional() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<Meta> getMetas() {
        return metas;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }

    public Set<ObjetivosEstrategicos> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(Set<ObjetivosEstrategicos> objetivos) {
        this.objetivos = objetivos;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
