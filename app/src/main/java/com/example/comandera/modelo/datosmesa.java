package com.example.comandera.modelo;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class datosmesa {
    public String nombre;
    public String id;
    public Drawable imagen;

    public datosmesa(String nombre, Drawable imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public datosmesa(String nombre, Drawable imagen, String id) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.id=id;
    }
    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }


}
