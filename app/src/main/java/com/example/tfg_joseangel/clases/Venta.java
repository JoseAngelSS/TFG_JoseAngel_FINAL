package com.example.tfg_joseangel.clases;

import java.io.Serializable;

public class Venta implements Serializable {

    private String Ref;
    private String idCompvend;
    private String nunidad;


    public Venta(String ref, String idCompvend, String nunidad) {
        this.Ref = ref;
        this.idCompvend = idCompvend;
        this.nunidad = nunidad;
    }

    public Venta() {
        this.Ref = "";
        this.idCompvend = "";
        this.nunidad = "";
    }

    public Venta(String idCompvend, String nunidad) {
        this.idCompvend = idCompvend;
        this.nunidad = nunidad;
    }

    public String getRef() {
        return Ref;
    }

    public void setRef(String ref) {
        Ref = ref;
    }

    public String getIdCompvend() {
        return idCompvend;
    }

    public void setIdCompvend(String idComp) {
        this.idCompvend = idComp;
    }

    public String getNunidad() {
        return nunidad;
    }

    public void setNunidad(String nunidad) {
        this.nunidad = nunidad;
    }

}
