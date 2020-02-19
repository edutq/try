package com.us.skyguardian.translock;

public class Usuario {

    private String telefono;
    private String nombre;
    private String apellido;
    private boolean activo;
    private int rol;
    private int id;
    private static Usuario user;

    private Usuario() {

        this.telefono = "";
        this.nombre = "";
        this.activo = false;
        this.rol = 0;
        this.apellido = "";
        this.id = 0;
    }

    private Usuario(int id, String telefono, String nombre, String apellido, boolean activo, int rol) {

        this.id = id;
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellido = apellido;
        this.activo = activo;
        this.rol = rol;
    }

    public static Usuario getUser(int id, String telefono, String nombre, String apellido, boolean activo, int rol) {

        if (user == null) {

            user = new Usuario(id, telefono, nombre, apellido, activo, rol);

        }
        return user;
    }

    public static Usuario getUser() {
        if (user == null) {
            user = new Usuario();
        }
        return user;
    }

    public static void destroyUser() {
        user = null;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
