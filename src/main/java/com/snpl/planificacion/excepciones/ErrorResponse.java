package com.snpl.planificacion.excepciones;

import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

//@ResponseBody
public class ErrorResponse {

    private String mensaje;
    private LocalDateTime fecha;

    public ErrorResponse(String mensaje){
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
