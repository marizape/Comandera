package com.example.comandera.modelo;
import android.graphics.Bitmap;

public class ProductossCocinero {
    public String nombre, idProducto;
    public int cantidad;
    public double precio;
    Bitmap imagen;
    public  String presentacionP;


    public String getPresentacionP() {
        return presentacionP;
    }

    public void setPresentacionP(String presentacionP) {
        this.presentacionP = presentacionP;
    }


    public ProductossCocinero(Bitmap imagen, String nombre, int cantidad, double precio, String presentacionP){
        this.imagen = imagen;
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.precio=precio;
        this.presentacionP=presentacionP;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
    public interface MiListenerCocinero {

    }
}




