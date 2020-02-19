package com.us.skyguardian.translock.main.candados;

import android.graphics.drawable.Drawable;

import ir.mirrajabi.searchdialog.core.Searchable;

public class CandadosVG implements Searchable {

    private String nombre;
    private int foto;
    private int icono;
    private int id;
    private String imei;
    private String marca;
    private boolean admin;
    private String identificador;

    public CandadosVG(String nombre, int foto, int icono, int id, String imei, String marca, boolean admin, String identificador) {

        this.nombre = nombre;
        this.foto = foto;
        this.icono = icono;
        this.id = id;
        this.imei = imei;
        this.marca = marca;
        this.admin = admin;
        this.identificador = identificador;
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

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public String getTitle() {
        return this.nombre;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
}
