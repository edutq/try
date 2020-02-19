package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos;

public class usuariosCompartidosVG {

    private int id;
    private String telefono;
    private String nombre;
    private int foto;

    private boolean administrador;

    public usuariosCompartidosVG(int id, String telefono, String nombre, int foto, boolean administrador) {
        this.id = id;
        this.telefono = telefono;
        this.nombre = nombre;
        this.foto = foto;
        this.administrador = administrador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
}
