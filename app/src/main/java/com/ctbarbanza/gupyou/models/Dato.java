package com.ctbarbanza.gupyou.models;

import java.util.Calendar;

public class Dato {

    private String emisor;
    private String usuario;
    private Calendar fecha = Calendar.getInstance();

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
}
