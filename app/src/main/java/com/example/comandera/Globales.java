package com.example.comandera;

import com.example.comandera.modelo.ProductosDatos;

import java.util.ArrayList;
import java.util.List;

public class Globales {

    public String usuario;
    public String numeroM="0";
    public  String ordenN;

   // public String ingresoM;




    public String idDocUl;
    public String idFolio;
    public String vDoc;
    public String vFolio;
    public String vExisteP;
    public String vExisteP2;
    public String  elementosExistentesEnDD;
    public String vNumDoc;
    public String vNumDoc2;
    public String vCPE;
    public String vExisteCantidad;
    public String ineFrontal;
    public String ineInversa;
    public Long IdCliente=12345678910L;
    public String nombreUsuario;
    public String nombreUsuarioCancelacion;
    public String folioVenta;
    public String idEmpresaLau;
    public String idEstablecimientoLau;
    public String idCajaLau;
    public String nomEst;
    public String idCajaCancelacion;
    public String  idUsuarioCancelacion;
    public String  idEstablecimientoCancelacion;
    public int  idEstatusLau;
    public int  idEstatusPorPagar;
    public int  idEstatusPagado;
    public int  idEstatusCancelado;
    public int  idVentaalPublico;
    public int  idTpdCancela;
    public int  idTpdVenta;
    public String idEmpresaCancel;
    public String idEstablecimientoCancel;
    public String idCajaCancel;
    public String folioCancel;
    public String idUltimoDocDCancelacion;
    public String cancelDLV;
    public String nombreEstCancel;
    public String subTCancel;
    public String ivaCancel;
    public String totalCancel;
    public String mensajeEnT;
    public String cajeroEnT;
    public String existeAD;
    public int idRolCajero;
    public int idRolAdministrador;
    public int idRolSuperAdmi;
    public String tipoDeUsuario;
    public String fechas;
    public String renglon;
//////////////////////////////////////////////////////////////
public String  cajaa2;
    public String  establecimientoo2;
    public String  cajero2;
    public String direccion;

    //Main Activity
    public String emailUsr;
    public  String claveUsr;
    public String emailUsr2;
    public  String claveUsr2;
    public int Rol;
    public  String Rol2;
    public String id_usuario;
    public String prsFk;
    public String prsNom;
    public String direccion2;

    //Seleccion bodega
    public String  cajaId;
    public String  cajaa;
    public String  establecimientoId;
    public String  establecimientoo;

    public String  cajero;
    public String  caja;
    public String  mont;
    public String  tpmId;
    public String  tpmNom;
    public String  estNom;
    public String cjusId;
    public int canti;

    public int operacion;
    public Double subTotal;
    public Double TotVenta;
    public int idProducto;
    public int cantiOperacion;
    public  int canTotal;
    public int idDetalle;
    public int idStatus;
    public int positionRecycler;
    public String fchaSelect;
    public int actualiSin;
    public String idVentaPrevia;



    public  String idMetodo;
    public int metodoFK;
    //VENTA ANTERIOR
    public double cantidadPago;
    public int idDocm;
    public String statusPago;

    //CORTE DE CAJA
    public double totalEfectivo2;
    public double totalTarjeta2;
    public String claveCU;
    public double montoApertura;
    public double montoTotl;
    public List<ProductosDatos> listclientes = new ArrayList<ProductosDatos>();
    //public List<CancelacionDatos> listaDatosCancelacion = new ArrayList<CancelacionDatos>();
    //public List<CancelacionDatos> listaRV = new ArrayList<CancelacionDatos>();




    /////variables marilu
    public String  idUsuarioL;
    public  int IdEstablecimiento;
    public int IdEstatusMa;
    public  int numeroInicio;
    public int idEstatu;



    private static Globales instance = new Globales();

    public static Globales getInstance() { return instance; }

    public static void setInstance(Globales  instance) {
        Globales.instance = instance;
    }

}
