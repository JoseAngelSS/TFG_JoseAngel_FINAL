package com.example.tfg_joseangel.clases;

import java.io.Serializable;
import java.util.Objects;

public class Marca implements Serializable {

    private int idMarca;
    private String nombre;

    public Marca(int idMarca, String nombre){
        this.idMarca = idMarca;
        this.nombre = nombre;
    }

    public Marca(String nMa){
        this.idMarca = 0;
        this.nombre = nMa;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marca that = (Marca) o;
        return idMarca == that.idMarca;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMarca);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
