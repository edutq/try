package com.us.skyguardian.translock.main.historial;

import java.time.format.DateTimeFormatter;

public class historialVG {

    private String nombre_candado;
    private String fecha;
    private String hora;
    private String activador;
    private String evento;
    private String afectado;

    public historialVG(String nombre_candado, String hora, String fecha, String activador, String evento, String afectado) {


        this.nombre_candado = nombre_candado;
        this.fecha = fecha;
        this.hora = hora;
        this.activador = activador;
        this.evento = evento;
        this.afectado = afectado;
    }

    public String getNombre_candado() {
        return nombre_candado;
    }

    public void setNombre_candado(String nombre_candado) {
        this.nombre_candado = nombre_candado;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getActivador() {
        return activador;
    }

    public void setActivador(String activador) {
        this.activador = activador;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getAfectado() {
        return afectado;
    }

    public void setAfectado(String afectado) {
        this.afectado = afectado;
    }
}
