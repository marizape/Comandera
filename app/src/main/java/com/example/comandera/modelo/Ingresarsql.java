package com.example.comandera.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comandera.Globales;

import static com.example.comandera.Productos.cuentaProductosC;
import static com.example.comandera.clasificacionSeleccionada.cuentaProductos;


public class Ingresarsql extends AppCompatActivity {

    public Long IdCliente = 12345678910L;

    String doc = Globales.getInstance().idDocUl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void registrarClasificacion(String nombreClasificacion, String imagen, Context contexto) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getWritableDatabase();
        int actualizar = 0;
        String sqlTablaMoral = "insert into clasificacion(clas_nombre, clas_imagen) VALUES ('" + nombreClasificacion + "', '" + imagen + "')";

        try {
            System.out.println(" sqlTablaMoral " + sqlTablaMoral);
            db.execSQL(sqlTablaMoral);
            db.close();
            ConexionSQLiteHelper conn2;
            conn2 = new ConexionSQLiteHelper(contexto);
            SQLiteDatabase db2 = conn2.getReadableDatabase();
            Cursor cursor = db2.rawQuery("select MAX(prs_id) as maximo from persona", null);

            if (cursor != null) {
                cursor.moveToFirst();
                IdCliente = cursor.getLong(cursor.getColumnIndex("maximo"));
            }
            cursor.close();
            db2.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "", e.getMessage());
            System.out.println("Fallo_:    ");
        }
        Toast.makeText(contexto, "Id Cliente33: " + IdCliente, Toast.LENGTH_SHORT).show();
        String id = String.valueOf(IdCliente);
        if (id.equals("12345678910")) {

            Toast.makeText(contexto, "Datos no guardados.", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(contexto, "Datos guardados correctamente. ", Toast.LENGTH_LONG).show();


        }
    }

    public void registrarVentaProceso(String docd_cantprod, String docd_precven, String docd_preccom, String docd_descuento, String prd_fk, String ext_fk, String imp_fk, String doc_folio, String doc_fecha, String doc_hora, String doc_iva, String docd_cosind, String doc_observ, String doc_saldo, String prs_fk, String usr_fk, String cja_fk, String est_fk, String esta_fk, String tpd_fk, Context contexto) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getWritableDatabase();

        try {
            validaSiExisteFolio(contexto);
            if (Globales.getInstance().vFolio.equals("noExisteElFol")) {
                String sqlTablaFolio = "insert into folio( fol_folio,tpd_fk,cja_fk) VALUES ('" + doc_folio + "','" + tpd_fk + "','" + cja_fk + "')";
                db.execSQL(sqlTablaFolio);
                System.out.println("  validaSiExisteFolio  " + sqlTablaFolio);


                //Toast.makeText(contexto, "  validaSiExisteFolio1  " , Toast.LENGTH_SHORT).show();
            }
            validaSiExisteDoc(contexto);

            if (Globales.getInstance().vDoc.equals("noExisteElDoc")) {
                String doc_subtotal = docd_precven;
                String sqlTablaDocumento = "insert into documento(doc_folio, doc_fecha,doc_hora,doc_iva,doc_subtotal, doc_total, doc_descuento,doc_cosind, doc_observ,doc_saldo, prs_fk, usr_fk, cja_fk, est_fk, esta_fk ) VALUES ('" + doc_folio + "','" + doc_fecha + "','" + doc_hora + "','" + doc_iva + "','" + doc_subtotal + "','" + doc_subtotal + "','" + docd_descuento + "','" + docd_cosind + "','" + doc_observ + "','" + doc_saldo + "','" + prs_fk + "', '" + usr_fk + "','" + cja_fk + "','" + est_fk + "','" + esta_fk + "')";
                db.execSQL(sqlTablaDocumento);
                System.out.println("  noExisteElDoc  " + sqlTablaDocumento);

                //Toast.makeText(contexto, "  noExisteElDoc  " , Toast.LENGTH_SHORT).show();
            }

            existeD(contexto, prd_fk);
            if (Globales.getInstance().vExisteP.equals("existeElProducto")) {
                Double cantidad = Double.valueOf(docd_cantprod);
                Double precioUnitario = Double.valueOf(docd_precven);
                Double totalPrevio = cantidad * precioUnitario;

                db.execSQL("UPDATE documento_det SET docd_cantprod=" + docd_cantprod + ",docd_precven=" + totalPrevio + " WHERE prd_fk=" + prd_fk + " and doc_fk =" + doc);

                Double TotalVenta = 0.0;
                Cursor cursor3 = db.rawQuery("SELECT SUM(docd_precven) as suma FROM documento_det WHERE documento_det.doc_fk = " + doc, null);
                cursor3.moveToFirst();
                TotalVenta = Double.parseDouble(cursor3.getString(cursor3.getColumnIndex("suma")));
                db.execSQL("UPDATE documento SET doc_subtotal=" + TotalVenta + ",doc_total=" + TotalVenta + " WHERE doc_id=" + doc);

                Toast.makeText(contexto, "Producto agregado al carrito.", Toast.LENGTH_SHORT).show();
            } else {
                if (Globales.getInstance().vExisteP.equals("noExisteElProducto")) {
                    ultimoidoc(contexto, doc_folio);
                    String ultimoId0 = Globales.getInstance().idDocUl;
                    String sqlTablaDocumentoDetalle = "insert into documento_det(docd_cantprod,docd_precven, docd_preccom , docd_descuento,prd_fk, ext_fk, doc_fk, imp_fk) VALUES ('" + docd_cantprod + "','" + docd_precven + "','" + docd_preccom + "', '" + docd_descuento + "','" + prd_fk + "','" + ext_fk + "','" + ultimoId0 + "', '" + imp_fk + "')";
                    db.execSQL(sqlTablaDocumentoDetalle);

                    //si ya existen otros productos diferente al que se ingresara por primera vez tambien se debe actualizar el total
                    elementosExistentesEnDDe(contexto);
                    int elem = Integer.parseInt(Globales.getInstance().elementosExistentesEnDD);
                    if (elem > 1) {

                        Double TotalVenta = 0.0;
                        Cursor cursor3 = db.rawQuery("SELECT SUM(docd_precven) as suma FROM documento_det WHERE documento_det.doc_fk = " + doc, null);
                        cursor3.moveToFirst();
                        TotalVenta = Double.parseDouble(cursor3.getString(cursor3.getColumnIndex("suma")));
                        db.execSQL("UPDATE documento SET doc_subtotal=" + TotalVenta + ",doc_total=" + TotalVenta + " WHERE doc_id=" + doc);
                    }

                    Toast.makeText(contexto, "Producto agregado al carrito.", Toast.LENGTH_SHORT).show();
                }
            }

            contabilizaLosProdAgreCarr(contexto);
            db.close();
        } catch (Exception e) {
            System.out.println("Fallo_:    " + e.getMessage());
            Toast.makeText(contexto, "Error dato no agregado al carrito.", Toast.LENGTH_SHORT).show();
            contabilizaLosProdAgreCarr(contexto);
        }
    }


    public void ultimoidoc(Context context, String df) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        consultaestatus(context);
        Cursor cursor2 = db.rawQuery("select * from documento where doc_folio='" + df + "' and  esta_fk='" + Globales.getInstance().idEstatusLau + "'", null);
        String id = "";
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    id = String.valueOf(cursor2.getString(cursor2.getColumnIndex("doc_id")));
                    Globales.getInstance().idDocUl = String.valueOf(cursor2.getString(cursor2.getColumnIndex("doc_id")));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                } else {
                    if (id.equals("")) {
                        // Globales.getInstance().idDocUl="0";
                    }
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "Null16 ", e.getMessage());
        }
    }

    public void ultimoidoc2(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        //Cursor cursor2 =db.rawQuery("select * from documento where doc_folio='"+df+"'" , null);

        //Cursor cursor2 =db.rawQuery("select doc_folio,doc_id from documento where esta_fk="+porPagar+" or esta_fk="+pagado+" ORDER BY doc_folio ASC" , null);
        //Cursor cursor2 =db.rawQuery("select doc_folio,doc_id from documento ORDER BY doc_folio ASC" , null);
        // Cursor cursor2 =db.rawQuery("select doc_folio,doc_id from documento " , null);
        Cursor cursor2 = db.rawQuery("select ord_folio,ord_id from orden  ", null);
        String id = "";
        try {
            //
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    id = String.valueOf(cursor2.getString(cursor2.getColumnIndex("ord_id")));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    // int v= Integer.parseInt(Globales.getInstance().idDocUl);
                    int v = Integer.parseInt(id);
                    int oper = v + 1;
                    Globales.getInstance().idDocUl = String.valueOf(oper);

                }
                //
                else {
                    if (id.equals("")) {
                        Globales.getInstance().idDocUl = "0";
                    }
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "Null16 ", e.getMessage());
        }
    }


    private void validaSiExisteDoc(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor2 = db.rawQuery("SELECT *  FROM documento", null);
        String id = "";
        try {
            if (cursor2 != null) {

                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    id = String.valueOf(cursor2.getString(cursor2.getColumnIndex("doc_id")));

                    if (id != null) {
                        if (Globales.getInstance().idDocUl.equals(id)) {
                            Globales.getInstance().vDoc = "existeElDoc";
                            break;
                        } else {
                            Globales.getInstance().vDoc = "noExisteElDoc";
                        }
                        index++;
                        cursor2.moveToNext();
                    }
                }
                if (index != 0) {

                } else {
                    if (id.equals("")) {
                        Globales.getInstance().vDoc = "noExisteElDoc";
                    }
                    //Globales.getInstance().vDoc= "noExisteElDoc";
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "Null167 ", e.getMessage());
        }
    }

    private void validaSiExisteFolio(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        String id = "";
        Cursor cursor2 = db.rawQuery("SELECT *  FROM folio", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    id = String.valueOf(cursor2.getString(cursor2.getColumnIndex("fol_folio")));
                    if (id != null) {
                        if (Globales.getInstance().idFolio.equals(id)) {
                            Globales.getInstance().vFolio = "existeElFol";
                            break;
                        } else {
                            Globales.getInstance().vFolio = "noExisteElFol";
                        }

                        index++;
                        cursor2.moveToNext();
                    }
                }
                if (index != 0) {
                    // Globales.getInstance().vFolio= "existeElFol";

                } else {
                    if (id.equals("")) {
                        Globales.getInstance().vFolio = "noExisteElFol";
                    }
                }
            }
            cursor2.close();
            db.close();

        } catch (Exception e) {
            Log.println(Log.ERROR, "", e.getMessage());
        }
    }

    public void contabilizaLosProdAgreCarr(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        consultaestatus(context);
        //CAMBIAR
        // String idu=  Globales.getInstance().id_usuario;// filtrar la informacion
        String idu = Globales.getInstance().usuario;
        //consultaEmpresaEstableCaja(context,idu);
        String est = Globales.getInstance().idEstablecimientoLau;
        ///Cursor cursor2 =db.rawQuery("select sum(docd_cantprod) as cantidadProductos FROM documento_det  INNER JOIN documento ON documento_det.doc_fk=documento.doc_id WHERE  doc_fk ="+doc+" and  esta_fk='4'", null);
        /*MODIFICADO POR MARILU*/
        //   Cursor cursor2 =db.rawQuery("select sum(docd_cantprod) as cantidadProductos FROM documento_det  INNER JOIN documento ON documento_det.doc_fk=documento.doc_id WHERE    esta_fk='"+Globales.getInstance().idEstatusLau+"' and est_fk='"+est+"'", null);
        Cursor cursor2 = db.rawQuery("select sum(ordet_cant) as cantidadProductos FROM orden_det  INNER JOIN orden ON orden_det.ord_fk=orden.ord_id  INNER JOIN mesa ON mesa.mesa_id= orden.mesa_fk ", null);//WHERE  orden_det.esta_fk='3'        and mesa.est_fk=''

        try {
            if (cursor2 != null) {

                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    String id = String.valueOf(cursor2.getString(cursor2.getColumnIndex("cantidadProductos")));
                    if (id.equals("null")) {
                        cuentaProductosC.setText("0");
                        cuentaProductos.setText("0");
                    } else {
                        cuentaProductosC.setText(id);
                        cuentaProductos.setText(id);
                    }
                    if (id.equals("0")) {
                        cuentaProductosC.setText("0");
                        cuentaProductos.setText("0");
                    }
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    System.out.println(" Termino ");
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "", e.getMessage());
        }
    }

    private void existeD(Context contexto, String query) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getReadableDatabase();
        String id = "";

        Cursor cursor2 = db.rawQuery("SELECT prd_fk,docd_cantprod,doc_fk FROM documento_det WHERE prd_fk ='" + query + "' and doc_fk =" + doc, null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    //  Globales.getInstance().vNumDoc= String.valueOf( cursor2.getString(cursor2.getColumnIndex("doc_fk")));
                    Globales.getInstance().vExisteP = "existeElProducto";
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    System.out.println("F  existeD");
                } else {
                    if (id.equals("")) {
                        Globales.getInstance().vExisteP = "noExisteElProducto";
                    }
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "", e.getMessage());
        }
    }

    public void ActualizarVenta(Context contexto, String prd_fk, String docd_cantprod, String docd_precven) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getWritableDatabase();
        existeD(contexto, prd_fk);
        if (Globales.getInstance().vExisteP.equals("existeElProducto")) {

            Double cantidad = Double.valueOf(docd_cantprod);
            Double precioUnitario = Double.valueOf(docd_precven);
            Double totalPrevio = cantidad * precioUnitario;

            db.execSQL("UPDATE documento_det SET docd_cantprod=" + docd_cantprod + ",docd_precven=" + totalPrevio + " WHERE prd_fk=" + prd_fk + " and doc_fk =" + doc);


            Double TotalVenta = 0.0;
            Cursor cursor3 = db.rawQuery("SELECT SUM(docd_precven) as suma FROM documento_det WHERE documento_det.doc_fk = " + doc, null);
            cursor3.moveToFirst();
            TotalVenta = Double.parseDouble(cursor3.getString(cursor3.getColumnIndex("suma")));
            db.execSQL("UPDATE documento SET doc_subtotal=" + TotalVenta + ",doc_total=" + TotalVenta + " WHERE doc_id=" + doc);

            db.close();
            cursor3.close();
            contabilizaLosProdAgreCarr(contexto);

        } else {
            System.out.println("No Existe el producto  ");
        }
        contabilizaLosProdAgreCarr(contexto);
    }

    public void ActualizarVentaEli(Context contexto, String prd_fk) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getWritableDatabase();
        existeD(contexto, prd_fk);
        if (Globales.getInstance().vExisteP.equals("existeElProducto")) {

            db.execSQL("DELETE FROM documento_det WHERE prd_fk=" + prd_fk + " and doc_fk =" + doc);
            Double TotalVenta = 0.0;
            Cursor cursor3 = db.rawQuery("SELECT SUM(docd_precven) as suma FROM documento_det WHERE documento_det.doc_fk = " + doc, null);
            cursor3.moveToFirst();
            TotalVenta = Double.parseDouble(cursor3.getString(cursor3.getColumnIndex("suma")));
            db.execSQL("UPDATE documento SET doc_subtotal=" + TotalVenta + ",doc_total=" + TotalVenta + " WHERE doc_id=" + doc);

            cursor3.close();
            db.close();
            contabilizaLosProdAgreCarr(contexto);
        } else {
            if (Globales.getInstance().vExisteP.equals("noExisteElProducto")) {
                System.out.println("No Existe el producto  ");
            }
        }
        contabilizaLosProdAgreCarr(contexto);
    }

    public void ActualizarV(Context contexto, String prd_fk, String doc_id) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getWritableDatabase();
        existeD(contexto, prd_fk);
        if (Globales.getInstance().vExisteP.equals("existeElProducto")) {
            Cursor cursorC = db.rawQuery("DELETE FROM documento_det WHERE documento_det.doc_fk = " + doc_id, null);
            Cursor cursorC2 = db.rawQuery("DELETE FROM folio WHERE fol_folio IN (SELECT fol_folio FROM folio fol INNER JOIN documento doc ON (fol.fol_folio=doc.doc_folio ) WHERE doc.doc_id =" + doc_id + ")", null);
            Cursor cursorC3 = db.rawQuery("DELETE FROM documento WHERE documento.doc_id= " + doc_id, null);
            cursorC.moveToFirst();
            cursorC2.moveToFirst();
            cursorC3.moveToFirst();
            db.close();
            cursorC.close();
            cursorC2.close();
            cursorC3.close();
            contabilizaLosProdAgreCarr(contexto);

        } else {
            if (Globales.getInstance().vExisteP.equals("noExisteElProducto")) {
                System.out.println("No Existe el producto  ");
            }
        }
        contabilizaLosProdAgreCarr(contexto);
    }


    public void elementosExistentesEnDDe(Context contexto) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getReadableDatabase();
        String id = "";

        Cursor cursor2 = db.rawQuery("SELECT COUNT (*)  FROM orden_det WHERE ord_fk =" + doc, null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    Globales.getInstance().elementosExistentesEnDD = String.valueOf(cursor2.getString(cursor2.getColumnIndex("COUNT (*)")));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    //System.out.println("F  existeD" );
                } else {
                    if (id.equals("")) {
                        Globales.getInstance().elementosExistentesEnDD = "0";
                    }
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "", e.getMessage());
        }
    }

    public void existeCanti(Context contexto, String query) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getReadableDatabase();
        String id = "";

        // Cursor cursor2 =db.rawQuery("SELECT prd_fk,docd_cantprod,doc_fk FROM documento_det WHERE prd_fk ='"+query+"' and doc_fk ="+doc , null);
        Cursor cursor2 = db.rawQuery("SELECT prepro_fk,ordet_cant,ord_fk FROM orden_det WHERE prepro_fk ='" + query + "' and ord_fk =" + doc, null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    //Globales.getInstance().vCPE= String.valueOf( cursor2.getString(cursor2.getColumnIndex("docd_cantprod")));
                    Globales.getInstance().vCPE = String.valueOf(cursor2.getString(cursor2.getColumnIndex("ordet_cant")));
                    Globales.getInstance().vExisteCantidad = "existeCantidad";

                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    System.out.println("F  existeD");
                } else {
                    if (id.equals("")) {
                        Globales.getInstance().vExisteCantidad = "noExisteCantidad";
                    }
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "", e.getMessage());
        }
    }


    public String generarFolio(Context context) {
        //cambiar
        //  String usu =Globales.getInstance().id_usuario;
        String usu = Globales.getInstance().usuario;
        consultaEmpresaEstable(context, usu);

        /////generar folio//////////////////////////////////////////////////////////////////////////
        String empresaF = "", establecimientoF = "";
        String empresa = Globales.getInstance().idEmpresaLau;//cambiar por idemprese

        int e1 = Integer.parseInt(empresa);
        if (empresa != null && e1 != 0) {
            int var = empresa.length();
            if (var == 1) {
                //empresaF= "0"+ empresa;
                empresaF = empresa;
            } else if (var == 2) {
                empresaF = empresa.substring(0, 2);
            } else {
                empresaF = empresa.substring(0, 2);
            }
        } else {
            empresaF = "1";
            // empresaF = "00";
        }

        String establecimiento = Globales.getInstance().idEstablecimientoLau;
        int e2 = Integer.parseInt(establecimiento);
        if (establecimiento != null && e2 != 0) {
            int var = establecimiento.length();
            if (var == 1) {
                establecimientoF = "0" + establecimiento;
            } else {
                establecimientoF = establecimiento.substring(0, 2);
            }
        } else {
            establecimientoF = "00";
        }

        String fo = "";


        ultimoidoc2(context);
        String t1 = Globales.getInstance().idDocUl;


        int t = Integer.parseInt(t1);

        //  consultartpdVenta(context);
        //   String tpd= String.valueOf(Globales.getInstance().idTpdVenta);


        if (t != 0) {//comprueba si ya existe un doc aunque no este en estado en proceso tal vez ya este en estadpo por pagar o  pagado

            ///hacer una consulta del ultimo folio  existente  q teng en proceso o  por pafar

            String fo2 = consultarUltimoFolioPV(context);
            String folio3 = empresaF + establecimientoF + fo2;
            fo = folio3;
            // fo= veriFolio(context,folio3);

        } else {
            //Globales.getInstance().idDocUl="1";
            //
            String fo1 = "1";
            String folio3 = empresaF + establecimientoF + fo1;
            String folio = veriFolio(context, folio3);
            fo = empresaF + establecimientoF + folio;
        }


        String folio = fo;

        return folio;
    }

    //
    public String veriFolio(Context context, String folio) {
        String fo = "";
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        String id = "";
        Cursor cursor2 = db.rawQuery("SELECT *  FROM orden", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    id = String.valueOf(cursor2.getString(cursor2.getColumnIndex("ord_id")));
                    if (id != null) {
                        if (folio.equals(id)) {
                            index++;

                            break;
                        }
                        cursor2.moveToNext();
                    }
                }
                if (index != 0) {

                    int tamaño = id.length();
                    if (tamaño <= 12) {
                        String f1;
                        f1 = id.substring(6);
                        int s = Integer.parseInt(f1);
                        int s2 = s + 1;
                        String f5 = String.valueOf(s2);
                        fo = f5;
                    }
                    if (tamaño == 13) {
                        String f1;
                        f1 = id.substring(7);
                        int s = Integer.parseInt(f1);
                        int s2 = s + 1;
                        String f5 = String.valueOf(s2);
                        fo = f5;
                    }
                } else {
                    fo = "1";
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "", e.getMessage());
        }
        return fo;
    }


    private String consultarUltimoFolioPV(Context context) {
        //consultaestatusPorPagar(context);
        // consultaestatusPagado(context);
        //int porPagar= Globales.getInstance().idEstatusPorPagar;
        //consultartpdVenta(context);
        int idTpdVenta = Globales.getInstance().idTpdVenta;
        String fo = "";
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        String id = "";
        //cambio por la nueva configuracion
        //Cursor cursor2 =db.rawQuery("select doc_folio from documento where esta_fk="+porPagar+" or esta_fk="+pagado, null);
        Cursor cursor2 = db.rawQuery("SELECT ord_folio FROM orden", null);
///cambiar la funcion con lo mio
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    id = String.valueOf(cursor2.getString(cursor2.getColumnIndex("ord_folio")));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {

                    int tamaño = id.length();
                    if (tamaño <= 12) {
                        String f1;
                        f1 = id.substring(6);


                        System.out.println(" f1f1 empresa " + f1);


                        int s = Integer.parseInt(f1);
                        int s2 = s + 1;
                        String f5 = String.valueOf(s2);
                        fo = f5;
                    }
                    if (tamaño == 13) {
                        String f1;
                        f1 = id.substring(7);
                        int s = Integer.parseInt(f1);
                        int s2 = s + 1;
                        String f5 = String.valueOf(s2);
                        fo = f5;
                    }
                } else {
                    //fo="1";
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "", e.getMessage());
        }
        return fo;
    }

    /*
        public void consultaEmpresaEstableCaja(Context context, String usu) {
            ConexionSQLiteHelper conn;
            conn=new ConexionSQLiteHelper(context);
            SQLiteDatabase db = conn.getReadableDatabase();

            //SELECT  emp_fk, cja_id, est_id from establecimiento INNER JOIN caja ON  establecimiento.est_id= caja.est_fk   WHERE usr_fk=9
            //Cursor cursor2 =db.rawQuery("SELECT cja_fk, caja_usuario.usr_fk, est_fk, emp_fk from caja_usuario INNER JOIN caja ON caja_usuario.cja_fk= caja.cja_id INNER JOIN establecimiento  ON caja.est_fk= establecimiento.est_id  INNER JOIN empresa ON  establecimiento.emp_fk= empresa.emp_id  WHERE caja_usuario.usr_fk='"+usu+"'" , null);
            Cursor cursor2 =db.rawQuery("SELECT cfg_idEsta,cfg_idCja,cfg_nomEsta FROM configuracion WHERE  cfg_idUsr='"+usu+"'" , null);

            String id = "";
            try {
                if (cursor2 != null) {
                    cursor2.moveToFirst();
                    int index = 0;
                    while (!cursor2.isAfterLast()) {
                        Globales.getInstance().idEmpresaLau= String.valueOf( cursor2.getString(cursor2.getColumnIndex("cfg_idEsta")));
                        Globales.getInstance().idEstablecimientoLau= String.valueOf( cursor2.getString(cursor2.getColumnIndex("cfg_idEsta")));
                        Globales.getInstance().idCajaLau= String.valueOf( cursor2.getString(cursor2.getColumnIndex("cfg_idCja")));
                        Globales.getInstance().nomEst= String.valueOf( cursor2.getString(cursor2.getColumnIndex("cfg_nomEsta")));
                        index++;
                        cursor2.moveToNext();
                    }
                    if (index != 0) {
                        //tiene datos
                    }
                    else
                    {
                        if(id.equals("")) {
                            Globales.getInstance().idEmpresaLau="0";
                            Globales.getInstance().idEstablecimientoLau="0";
                            Globales.getInstance().idCajaLau="0";
                        }
                    }
                }
                cursor2.close();
                db.close();
            }catch(Exception e){
                Log.println(Log.ERROR,"Null16 ",e.getMessage());
            }
        }

    */
    public void consultaestatus(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursorEstatus = db.rawQuery("SELECT * FROM estatus WHERE estatus.esta_estatus='por preparar'", null);
        try {
            if (cursorEstatus != null) {
                cursorEstatus.moveToFirst();
                Globales.getInstance().idEstatusLau = cursorEstatus.getInt(cursorEstatus.getColumnIndex("esta_id"));
            }
            cursorEstatus.close();
            db.close();
        } catch (Exception e) {
        }
    }

    public void consultaestatusPorPagar(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursorEstatus = db.rawQuery("SELECT * FROM estatus WHERE estatus.esta_estatus='Por pagar'", null);
        try {
            if (cursorEstatus != null) {
                cursorEstatus.moveToFirst();
                Globales.getInstance().idEstatusPorPagar = cursorEstatus.getInt(cursorEstatus.getColumnIndex("esta_id"));
            }
            cursorEstatus.close();
            db.close();
        } catch (Exception e) {
        }
    }


    public void consultaestatusPagado(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursorEstatus = db.rawQuery("SELECT * FROM estatus WHERE estatus.esta_estatus='Pagado'", null);
        try {
            if (cursorEstatus != null) {
                cursorEstatus.moveToFirst();
                Globales.getInstance().idEstatusPagado = cursorEstatus.getInt(cursorEstatus.getColumnIndex("esta_id"));
            }
            cursorEstatus.close();
            db.close();
        } catch (Exception e) {
        }
    }

    public void consultaestatusCancelado(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursorEstatus = db.rawQuery("SELECT * FROM estatus WHERE estatus.esta_estatus='Cancelada'", null);
        try {
            if (cursorEstatus != null) {
                cursorEstatus.moveToFirst();
                Globales.getInstance().idEstatusCancelado = cursorEstatus.getInt(cursorEstatus.getColumnIndex("esta_id"));
            }
            cursorEstatus.close();
            db.close();
        } catch (Exception e) {
        }
    }


    public void consultarIDVentaPublico(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursorEstatus = db.rawQuery("SELECT * FROM persona WHERE persona.prs_nombre='VENTA AL PUBLICO'", null);
        try {
            if (cursorEstatus != null) {
                cursorEstatus.moveToFirst();
                Globales.getInstance().idVentaalPublico = cursorEstatus.getInt(cursorEstatus.getColumnIndex("prs_id"));
            }
            cursorEstatus.close();
            db.close();
        } catch (Exception e) {
        }
    }


    public void consultartpdCancelaciones(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursorEstatus = db.rawQuery("SELECT * FROM tipo_documento WHERE tipo_documento.tpd_documento='Cancelaciones'", null);
        try {
            if (cursorEstatus != null) {
                cursorEstatus.moveToFirst();
                Globales.getInstance().idTpdCancela = cursorEstatus.getInt(cursorEstatus.getColumnIndex("tpd_id"));
            }
            cursorEstatus.close();
            db.close();
        } catch (Exception e) {
        }
    }


    public void consultartpdVenta(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursorEstatus = db.rawQuery("SELECT * FROM tipo_documento WHERE tipo_documento.tpd_documento='Venta'", null);
        try {
            if (cursorEstatus != null) {
                cursorEstatus.moveToFirst();
                Globales.getInstance().idTpdVenta = cursorEstatus.getInt(cursorEstatus.getColumnIndex("tpd_id"));
            }
            cursorEstatus.close();
            db.close();
        } catch (Exception e) {
        }
    }



/*-
    public void consultaEmpresaEstableCajaCancel(Context context, String usu) {
        ConexionSQLiteHelper conn;
        conn=new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        //SELECT  emp_fk, cja_id, est_id from establecimiento INNER JOIN caja ON  establecimiento.est_id= caja.est_fk   WHERE usr_fk=9
        //Cursor cursor2 =db.rawQuery("SELECT cja_fk, caja_usuario.usr_fk, est_fk, emp_fk from caja_usuario INNER JOIN caja ON caja_usuario.cja_fk= caja.cja_id INNER JOIN establecimiento  ON caja.est_fk= establecimiento.est_id  INNER JOIN empresa ON  establecimiento.emp_fk= empresa.emp_id  WHERE caja_usuario.usr_fk='"+usu+"'" , null);
        Cursor cursor2 =db.rawQuery("SELECT  cfg_idEsta,cfg_idEsta, cfg_idCja from configuracion WHERE cfg_idUsr='"+usu+"'" , null);
        String id = "";
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    Globales.getInstance().idEmpresaCancel= String.valueOf( cursor2.getString(cursor2.getColumnIndex("cfg_idEsta")));
                    Globales.getInstance().idEstablecimientoCancel= String.valueOf( cursor2.getString(cursor2.getColumnIndex("cfg_idEsta")));
                    Globales.getInstance().idCajaCancel= String.valueOf( cursor2.getString(cursor2.getColumnIndex("cfg_idCja")));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    //tiene datos
                }
                else
                {
                    if(id.equals("")) {
                        Globales.getInstance().idEmpresaCancel="0";
                        Globales.getInstance().idEstablecimientoCancel="0";
                        Globales.getInstance().idCajaCancel="0";
                    }
                }
            }
            cursor2.close();
            db.close();
        }catch(Exception e){
            Log.println(Log.ERROR,"Null16 ",e.getMessage());
        }
    }*/


    public String generarFolioCancelacion(Context context) {
        String usu = Globales.getInstance().idUsuarioCancelacion;
        // consultaEmpresaEstableCajaCancel(context,usu);
        String empresaF = "", establecimientoF = "", cajaF = "";
        String empresa = Globales.getInstance().idEmpresaCancel;
        int e1 = Integer.parseInt(empresa);
        if (empresa != null && e1 != 0) {

            int var = empresa.length();
            if (var == 1) {
                empresaF = empresa;
            } else if (var == 2) {
                empresaF = empresa.substring(0, 2);
            } else {
                empresaF = empresa.substring(0, 2);
            }
        } else {
            empresaF = "1";
            //empresaF = "00";
        }
        String establecimiento = Globales.getInstance().idEstablecimientoCancel;
        int e2 = Integer.parseInt(establecimiento);
        if (establecimiento != null && e2 != 0) {
            int var = establecimiento.length();
            if (var == 1) {
                establecimientoF = "0" + establecimiento;
            } else {
                establecimientoF = establecimiento.substring(0, 2);
            }
        } else {
            establecimientoF = "00";
        }
        String caja = Globales.getInstance().idCajaCancel;
        int e3 = Integer.parseInt(caja);
        if (caja != null && e3 != 0) {
            int var = caja.length();
            if (var == 1) {
                cajaF = "0" + caja;
            } else {
                cajaF = caja.substring(0, 2);
            }
        } else {
            cajaF = "00";
        }


        consultartpdCancelaciones(context);
        int tpdC = Globales.getInstance().idTpdCancela;
        String fo = "1";

        String folio = empresaF + establecimientoF + cajaF + tpdC + fo;

        return folio;
    }

    public String generarFolioCCYaExisteUnFECancel(Context context, String folioAuxCancel) {
        String usu = Globales.getInstance().idUsuarioCancelacion;
        // consultaEmpresaEstableCajaCancel(context,usu);
        String empresaF = "", establecimientoF = "", cajaF = "";
        String empresa = Globales.getInstance().idEmpresaCancel;
        int e1 = Integer.parseInt(empresa);
        if (empresa != null && e1 != 0) {


            int var = empresa.length();
            if (var == 1) {
                empresaF = empresa;
            } else if (var == 2) {
                empresaF = empresa.substring(0, 2);
            } else {
                empresaF = empresa.substring(0, 2);
            }
        } else {
            empresaF = "1";
            //empresaF = "0";
        }
        String establecimiento = Globales.getInstance().idEstablecimientoCancel;
        int e2 = Integer.parseInt(establecimiento);
        if (establecimiento != null && e2 != 0) {
            int var = establecimiento.length();
            if (var == 1) {
                establecimientoF = "0" + establecimiento;
            } else {
                establecimientoF = establecimiento.substring(0, 2);
            }
        } else {

            establecimientoF = "00";
        }
        String caja = Globales.getInstance().idCajaCancel;
        int e3 = Integer.parseInt(caja);
        if (caja != null && e3 != 0) {
            int var = caja.length();
            if (var == 1) {
                cajaF = "0" + caja;
            } else {
                cajaF = caja.substring(0, 2);
            }
        } else {
            cajaF = "00";
        }
        String fo = "";
        int tamaño = folioAuxCancel.length();
        if (tamaño <= 12) {
            String f1;
            f1 = folioAuxCancel.substring(6);
            int s = Integer.parseInt(f1);
            int s2 = s + 1;
            String f5 = String.valueOf(s2);
            fo = f5;
        }
        if (tamaño == 13) {
            String f1;
            f1 = folioAuxCancel.substring(7);
            int s = Integer.parseInt(f1);
            int s2 = s + 1;
            String f5 = String.valueOf(s2);
            fo = f5;
        }
        consultartpdCancelaciones(context);
        int tpdC = Globales.getInstance().idTpdCancela;
        String folio = empresaF + establecimientoF + cajaF + tpdC + fo;
        return folio;
    }


    public void consultarIdAdministrador(Context context) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursorEstatus = db.rawQuery("SELECT DISTINCT cfg_idRol FROM configuracion WHERE cfg_nomRol='Administrador'", null);
        try {
            if (cursorEstatus != null) {
                cursorEstatus.moveToFirst();
                Globales.getInstance().idRolAdministrador = cursorEstatus.getInt(cursorEstatus.getColumnIndex("cfg_idRol"));
            }
            cursorEstatus.close();
            db.close();
        } catch (Exception e) {
        }
    }

    public void registrarVenta(Context contexto, String ordet_cant, String ordet_precio, String ordet_importe, String ordet_observa, String prepro_fk, String esta_fk_ordet, String ord_folio, String ord_fecha, String ord_hora, String ord_total, String ord_iva, String mesa_fk, String cjus_fk, String esta_fk_ord) {
        /*
        ConexionSQLiteHelper conn;
        conn=new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getWritableDatabase();
        mesa_fk="1";
        String ord_fk="1";
        String ord_subtotal="6";
        String  sqlOrden ="INSERT INTO orden (ord_folio,ord_fecha,ord_hora,ord_total,ord_subtotal,ord_iva,mesa_fk,cjus_fk,esta_fk)VALUES('"+ord_folio+"','"+ord_fecha+"','"+ord_hora+"','"+ord_total+"','"+ord_subtotal+"','"+ord_iva+"','"+mesa_fk+"','"+cjus_fk+"','"+esta_fk_ord+"')" ;
        String  OrdenDetallada ="INSERT INTO orden_det(ordet_cant,ordet_precio, ordet_importe, ordet_observa,ord_fk,prepro_fk,esta_fk) VALUES ('"+ordet_cant+"','"+ordet_precio+"','"+ordet_importe+"','"+ordet_observa+"','"+ord_fk+"','"+prepro_fk+"','"+esta_fk_ordet+"')" ;
        try {
            db.execSQL(sqlOrden);
            db.execSQL(OrdenDetallada);
            //Toast.makeText(contexto, "venta registrado. " , Toast.LENGTH_LONG).show();
            db.close();
        }catch(Exception e){
            System.out.println("  Error  " + e.getMessage());
        }*/

        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getWritableDatabase();

        //mesa_fk="1";
       // String mesa = Globales.getInstance().ordenN;
       // String ord_fk = "1";
       // String ord_subtotal = "6";
        try {
            String folio = Globales.getInstance().folioEnviar;
            if (folio.length() != 0) {
                String validaSiExisteUnIdOrden = Globales.getInstance().idOrden;
                if (validaSiExisteUnIdOrden.length() != 0)//si la variable idOrden  tiene algo significa que  existe ya una orden creada
                {
                   String idOrden = Globales.getInstance().idOrden;
                    String OrdenDetallada = "INSERT INTO orden_det(ordet_cant,ordet_precio, ordet_importe, ordet_observa,ord_fk,prepro_fk,esta_fk) VALUES ('" + ordet_cant + "','" + ordet_precio + "','" + ordet_importe + "','" + ordet_observa + "','" + idOrden + "','" + prepro_fk + "','" + esta_fk_ordet + "')";
                    db.execSQL(OrdenDetallada);
                    System.out.println(" siExisteUnIdOrden " + OrdenDetallada);
                } else {///cuando se crea por primera vez una venta
                    String sqlOrden = "INSERT INTO orden (ord_folio,ord_fecha,ord_hora,ord_total,ord_subtotal,ord_iva,mesa_fk,cjus_fk,esta_fk)VALUES('" + ord_folio + "','" + ord_fecha + "','" + ord_hora + "','" + ord_total + "','" + ord_total + "','" + ord_iva + "','" + mesa_fk + "','" + cjus_fk + "','" + esta_fk_ord + "')";
                    db.execSQL(sqlOrden);
                    String idOrden = consultaIdOrden(contexto, folio);
                    Globales.getInstance().idOrden = idOrden;
                    String OrdenDetallada = "INSERT INTO orden_det(ordet_cant,ordet_precio, ordet_importe, ordet_observa,ord_fk,prepro_fk,esta_fk) VALUES ('" + ordet_cant + "','" + ordet_precio + "','" + ordet_importe + "','" + ordet_observa + "','" + idOrden + "','" + prepro_fk + "','" + esta_fk_ordet + "')";
                    db.execSQL(OrdenDetallada);
                }
                ////////
                // Existe el producto

                existeProducto(contexto,prepro_fk);
                    if (Globales.getInstance().existeProducto.equals("Existe el producto")) {
                        Double cantidad = Double.valueOf(ordet_cant);
                        Double precioUnitario = Double.valueOf(ordet_importe);
                        Double total = cantidad * precioUnitario;
                        db.execSQL("UPDATE orden_det SET ordet_cant=" + cantidad + ",ordet_importe=" + total);
                        Toast.makeText(contexto, "Producto agregado", Toast.LENGTH_SHORT).show();
                        Double TotalVenta = 0.0;

                       String ord_fk= Globales.getInstance().idOrden ;
                        Cursor cursor3 = db.rawQuery("SELECT SUM(ordet_importe) as suma FROM orden_det WHERE orden_det.ord_fk = " + ord_fk, null);
                        cursor3.moveToFirst();
                        TotalVenta = Double.parseDouble(cursor3.getString(cursor3.getColumnIndex("suma")));
                        db.execSQL("UPDATE orden SET ord_subtotal=" + TotalVenta + ",ord_total=" + TotalVenta + " WHERE ord_id=" + ord_fk);
                    } else {

                    if (Globales.getInstance().existeProducto.equals("noExiate producto")) {

                        // String OrdenDetallada="";
                      /*  String idOrden =Globales.getInstance().idOrden ;
                        String OrdenD = "INSERT INTO orden_det(ordet_cant,ordet_precio, ordet_importe, ordet_observa,ord_fk,prepro_fk,esta_fk) VALUES ('" + ordet_cant + "','" + ordet_precio + "','" + ordet_importe + "','" + idOrden + "','" + ordet_observa + "','" + prepro_fk + "','" + esta_fk_ordet + "')";
                        db.execSQL(OrdenD);
                        System.out.println("Datos insertados" + OrdenD);*/
                    }
                }
/*
                if (Globales.getInstance().existeCantidad.equals("Existe Cantidad")) {
                    String cantidad = "";
                    String Orden = ("UPDATE orden_det SET ordet_cant=+cantidad WHERE ordet_id='1'");
                    db.execSQL(Orden);
                } else {
                    if (Globales.getInstance().existeCantidad.equals("noExisteCantidad")) {
                        String cantidad = "";
                        String OrdenDetallada = "UPDATE orden_det SET ordet_precio='60'+cantidad WHERE ordet_id='1'";
                        db.execSQL(OrdenDetallada);
                    }
                }
                if (Globales.getInstance().existePrecio.equals("Existe Precio")) {
                    String precio = "";
                    String OrdenPrecio = ("UPDATE orden_det SET ordet_precio='3' WHERE ordet_id='1'");
                    db.execSQL(OrdenPrecio);
                } else {
                    String precio = "";*/
                   /*String OPrecio=(); modificar con una inserccion de precio
                    db.execSQL(OPrecio);*/
                   /* String oPrecio = "UPDATE orden_det SET ordet_precio='3' WHERE ordet_id='1'";
                    db.execSQL(oPrecio);
                }*/
                /////////

            } else {
            }



        /*    else
            {
                if(Globales.getInstance().existeProducto.equals("noExiate producto")){

                }
            }*/

          /*  if(Globales.getInstance().existeCantidad.equals("Existe Cantidad")){

                String Orden=("UPDATE orden_det SET ordet_cant='3' WHERE ordet_id='1'");
                db.execSQL(Orden);
            }else {
                if(Globales.getInstance().existeCantidad.equals("noExisteCantidad")){

                    String OrdenDetallada ="UPDATE orden_det SET ordet_precio='60' +cantidad WHERE ordet_id='1'";
                    db.execSQL(OrdenDetallada);
                }
            }
            if(Globales.getInstance().existePrecio.equals("Existe Precio")){
                String precio="";
                String OrdenPrecio=("UPDATE orden_det SET ordet_precio='3' WHERE ordet_id='1'");
                db.execSQL(OrdenPrecio);
            }else {
                String precio="";
                   /*String OPrecio=(); modificar con una inserccion de precio
                    db.execSQL(OPrecio);*/
           /*     String oPrecio="UPDATE orden_det SET ordet_precio='3' WHERE ordet_id='1'";
                db.execSQL(oPrecio);
            }*/
            /////////

            //Toast.makeText(contexto, "venta registrado. " , Toast.LENGTH_LONG).show();
            db.close();
        } catch (Exception e) {
            System.out.println("  Error  " + e.getMessage());
        }
    }

    public void consultaEmpresaEstable(Context context, String usu) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor2 = db.rawQuery("SELECT usr_id,emp_fk,est_id FROM establecimiento INNER JOIN usuario ON usuario.est_fk=establecimiento.est_id WHERE usr_id=" + usu, null);

        String id = "";
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    Globales.getInstance().idEmpresaLau = String.valueOf(cursor2.getString(cursor2.getColumnIndex("emp_fk")));
                    Globales.getInstance().idEstablecimientoLau = String.valueOf(cursor2.getString(cursor2.getColumnIndex("est_id")));

                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    //tiene datos
                } else {
                    if (id.equals("")) {
                        Globales.getInstance().idEmpresaLau = "0";
                        Globales.getInstance().idEstablecimientoLau = "0";
                    }
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "Null16 ", e.getMessage());
        }
    }

    public String consultaIdOrden(Context context, String folio) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor2 = db.rawQuery("select ord_folio,ord_id from orden where  ord_folio=" + folio, null);
        String id = "", ord_folio = "";
        String idOrden = "";
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    id = String.valueOf(cursor2.getString(cursor2.getColumnIndex("ord_id")));
                    ord_folio = String.valueOf(cursor2.getString(cursor2.getColumnIndex("ord_folio")));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    idOrden = id;
                } else {
                    idOrden = "";
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "Null16 ", e.getMessage());
        }
        return idOrden;
    }

    private void existeProducto(Context contexto, String query) {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getReadableDatabase();
        String id = "";


        String idOrden =Globales.getInstance().idOrden ;
        //Cursor cursor2 =db.rawQuery("SELECT prd_fk,docd_cantprod,doc_fk FROM documento_det WHERE prd_fk ='"+query+"' and doc_fk ="+doc , null);
        Cursor cursor2 = db.rawQuery("SELECT prepro_fk,ordet_cant,ord_fk FROM orden_det WHERE prepro_fk='" + query + "' and ord_fk='" + idOrden+ "'", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    Globales.getInstance().vNumDoc = String.valueOf(cursor2.getString(cursor2.getColumnIndex("ord_fk")));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    System.out.println("F  existeD");
                    Globales.getInstance().existeProducto = "Existe el producto";
                } else {
                    Globales.getInstance().existeProducto = "noExiate producto";
                }
            }
            cursor2.close();
            db.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "", e.getMessage());
        }

    }



}