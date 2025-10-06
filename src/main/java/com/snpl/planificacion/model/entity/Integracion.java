package com.snpl.planificacion.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "integraciones")
public class Integracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del sistema externo es obligatorio.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La url o endpoint de la API es obligatorio.")
    @Column(nullable = false, length = 255)
    private String api;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Integracion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "El nombre del sistema externo es obligatorio.") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre del sistema externo es obligatorio.") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "La url o endpoint de la API es obligatorio.") String getApi() {
        return api;
    }

    public void setApi(@NotBlank(message = "La url o endpoint de la API es obligatorio.") String api) {
        this.api = api;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
