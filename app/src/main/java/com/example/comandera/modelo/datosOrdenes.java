package com.example.comandera.modelo;

public class datosOrdenes {
    String numMesa;
    String ordenId;
    String estatus;

    public datosOrdenes(String numMesa, String ordenId, String estatus) {
        this.numMesa = numMesa;
        this.ordenId = ordenId;
        this.estatus = estatus;
    }


    public String getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(String numMesa) {
        this.numMesa = numMesa;
    }

    public String getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(String ordenId) {
        this.ordenId = ordenId;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
