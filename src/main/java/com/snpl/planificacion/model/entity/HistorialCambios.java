package com.snpl.planificacion.model.entity;

import com.snpl.planificacion.Enum.TipoAccion;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "historial_cambios")
public class HistorialCambios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario responsable;

    private LocalDate fecha;
    private LocalDate hora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAccion tipoAccion;

    public HistorialCambios() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getHora() {
        return hora;
    }

    public void setHora(LocalDate hora) {
        this.hora = hora;
    }

    public TipoAccion getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(TipoAccion tipoAccion) {
        this.tipoAccion = tipoAccion;
    }
}
