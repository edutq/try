package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView;

import java.util.Date;

public class usuarioHorarioVG {

    String fecha_inicio;
    String hora_inicio;
    String fecha_fin;
    String hora_fin;
    int id;

    public usuarioHorarioVG(int id, String fecha_inicio, String hora_inicio, String fecha_fin, String hora_fin) {

        this.id = id;
        this.fecha_inicio = fecha_inicio;
        this.hora_inicio = hora_inicio;
        this.fecha_fin = fecha_fin;
        this.hora_fin = hora_fin;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public int getId() {
        return id;
    }
}
