package com.us.skyguardian.translock.main.compartidos.compartirCandado;

public class candadoSearchVG {

    private String nombre;
    private int foto;
    private int id;
    private String identificador;

    public candadoSearchVG(int id, String nombre, int foto, String identificador) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.identificador = identificador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
}
