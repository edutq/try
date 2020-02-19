package com.us.skyguardian.translock.main.compartidos;

public class compartidosVG {

    private String nombre;
    private int cantidadCopartidos;
    private int id;
    private String imei;
    private String identificador;

    public compartidosVG(String nombre, int cantidadCopartidos, int id, String imei, String identificador) {
        this.nombre = nombre;
        this.cantidadCopartidos = cantidadCopartidos;
        this.id = id;
        this.imei = imei;
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadCopartidos() {
        return cantidadCopartidos;
    }

    public void setCantidadCopartidos(int cantidadCopartidos) {
        this.cantidadCopartidos = cantidadCopartidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
}
