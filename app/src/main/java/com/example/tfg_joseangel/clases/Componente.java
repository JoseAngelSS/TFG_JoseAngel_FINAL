package com.example.tfg_joseangel.clases;

import java.io.Serializable;

public class Componente implements Serializable {
    private String idComp;
    private String nombre;
    private String precio;
    private String cantidad;
    private String idMar;

    public Componente(String idComp, String nombre, String precio, String cantidad, String idMar) {
        this.idComp = idComp;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idMar = idMar;
    }

    public Componente() {
        this.idComp = "";
        this.nombre = "";
        this.precio = "";
        this.cantidad= "";
        this.idMar = "";
    }

    public Componente(String nombre, String precio, String cantidad, String idMar){
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idMar = idMar;
    }

    public String getIdComp() {
        return idComp;
    }

    public void setIdComp(String idComp) {
        this.idComp = idComp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getIdMar() {
        return idMar;
    }

    public void setIdMar(String idMar) {
        this.idMar = idMar;
    }
}
