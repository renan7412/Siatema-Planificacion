package com.snpl.planificacion.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "proyectos_inversion")
public class ProyectoInversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(precision = 12, scale = 2)
    private BigDecimal presupuesto;

    @Size(max = 500)
    @Column(length = 500)
    private String descripcion;

    @NotBlank
    @Column(length = 50)
    private String tipo; // EJ: Infraestructura, social, educativo, etc

    @NotBlank
    @Column(length = 100)
    private String entidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "objetivo_id", nullable = false)
    @NotNull
    private ObjetivosEstrategicos objetivoEstrategico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(BigDecimal presupuesto) {
        this.presupuesto = presupuesto;
    }

    public @Size(max = 500) String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@Size(max = 500) String descripcion) {
        this.descripcion = descripcion;
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

    public @NotNull ObjetivosEstrategicos getObjetivoEstrategico() {
        return objetivoEstrategico;
    }

    public void setObjetivoEstrategico(@NotNull ObjetivosEstrategicos objetivoEstrategico) {
        this.objetivoEstrategico = objetivoEstrategico;
    }
}
