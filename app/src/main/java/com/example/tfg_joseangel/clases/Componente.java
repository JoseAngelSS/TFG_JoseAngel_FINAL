package com.example.tfg_joseangel.clases;

import java.io.Serializable;

public class Componente implements Serializable {
    private int idComp;
    private String nombre;
    private double precio;
    private int cantidad;
    private int idMar;

    public Componente(int idComp, String nombre, double precio, int cantidad, int idMar) {
        this.idComp = idComp;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idMar = idMar;
    }

    public Componente() {
        this.idComp = 0;
        this.nombre = "";
        this.precio = 0;
        this.cantidad= 0;
        this.idMar = 1;
    }

    public Componente(String nombre, double precio, int cantidad, int idMar){
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idMar = idMar;
    }

    public int getIdComp() {
        return idComp;
    }

    public void setIdComp(int idComp) {
        this.idComp = idComp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdMar() {
        return idMar;
    }

    public void setIdMar(int idMar) {
        this.idMar = idMar;
    }
}
