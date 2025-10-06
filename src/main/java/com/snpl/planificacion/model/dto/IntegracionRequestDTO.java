package com.snpl.planificacion.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class IntegracionRequestDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String api;

    @NotNull
    private Long usuarioId;
}
