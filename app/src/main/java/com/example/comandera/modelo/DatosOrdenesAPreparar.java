package com.example.comandera.modelo;

public class DatosOrdenesAPreparar {
    String NumeroDeMesa;
    String IdOrdPreparar;
    String NumDeProductos;
    String EstatuAPrepar;

    public DatosOrdenesAPreparar(String numeroDeMesa, String idOrdPreparar, String numDeProductos, String estatuAPrepar) {
        NumeroDeMesa = numeroDeMesa;
        IdOrdPreparar = idOrdPreparar;
        NumDeProductos = numDeProductos;
        EstatuAPrepar = estatuAPrepar;
    }

    public String getNumeroDeMesa() {
        return NumeroDeMesa;
    }

    public void setNumeroDeMesa(String numeroDeMesa) {
        NumeroDeMesa = numeroDeMesa;
    }

    public String getIdOrdPreparar() {
        return IdOrdPreparar;
    }

    public void setIdOrdPreparar(String idOrdPreparar) {
        IdOrdPreparar = idOrdPreparar;
    }

    public String getNumDeProductos() {
        return NumDeProductos;
    }

    public void setNumDeProductos(String numDeProductos) {
        NumDeProductos = numDeProductos;
    }

    public String getEstatuAPrepar() {
        return EstatuAPrepar;
    }

    public void setEstatuAPrepar(String estatuAPrepar) {
        EstatuAPrepar = estatuAPrepar;
    }
}
