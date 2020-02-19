package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos;

public class usuariosCompartidosSearchVG {

    private int id;
    private String nombre;
    private String telefono;
    private int foto;

    public usuariosCompartidosSearchVG(int id, String nombre, String telefono, int foto) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.foto = foto;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
