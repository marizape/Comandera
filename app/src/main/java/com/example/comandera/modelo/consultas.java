package com.example.comandera.modelo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import com.example.comandera.Globales;
import com.example.comandera.MainActivity;
import com.example.comandera.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static javax.xml.datatype.DatatypeConstants.DATETIME;

public class consultas {
//    consultas consul= new consultas();
    public String ConsultarNomEstablecimiento(Context context, String id_usuario) {
        String nombreEst = "";

        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor cursorEstablecimiento = db.rawQuery("SELECT est_id, est_nombre FROM establecimiento INNER JOIN usuario ON usuario.est_fk=establecimiento.est_id WHERE usr_id=" + id_usuario, null);

        try {
            if (cursorEstablecimiento != null) {
                cursorEstablecimiento.moveToFirst();
                nombreEst = cursorEstablecimiento.getString(cursorEstablecimiento.getColumnIndex("est_nombre"));
                Globales.getInstance().IdEstablecimiento=cursorEstablecimiento.getInt(cursorEstablecimiento.getColumnIndex("est_id"));

            }
            cursorEstablecimiento.close();
            db.close();
        } catch (Exception e) {
        }
        return nombreEst;
    }

    public void GuardarDatoMesa(Context context, int numeMesa, int estable,int estatus) {
        //int num=1;
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getWritableDatabase();
       // String mesa =" Mesa " +numeMesa;


        try {
           // int variable= Integer.parseInt(cg_id);
            int variable= numeMesa;
            Cursor cursor2 =db.rawQuery("SELECT mesa_num from  mesa WHERE mesa_num="+variable , null);
            try {
                if (cursor2 != null) {
                    cursor2.moveToFirst();
                    int index = 0;
                    while (!cursor2.isAfterLast()) {
                        String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("mesa_num")));
                        index++;
                        cursor2.moveToNext();
                    }
                    if (index != 0) {
                        System.out.println(" ya existe el dato  ");
                        Toast.makeText(context, "   dato existe . " , Toast.LENGTH_LONG).show();
                        int var2=numeMesa+1;
                        String sql ="INSERT INTO mesa(mesa_num,est_fk,esta_fk) VALUES ('"+var2+"','"+estable+"','"+estatus+"')" ;
                        db.execSQL(sql);
                    }
                    else
                    {

                        String sql ="INSERT INTO mesa(mesa_num,est_fk,esta_fk) VALUES ('"+numeMesa+"','"+estable+"','"+estatus+"')" ;
                        db.execSQL(sql);
                        Toast.makeText(context, "Exito uiuuyh . " , Toast.LENGTH_LONG).show();
                        System.out.println("  registrar  " );
                        db.close();
                    }
                }
                cursor2.close();
                db.close();
            }catch(Exception e){
                System.out.println("Error en registrar  "+e.getMessage());
            }
/*
            db.execSQL(sql);
            System.out.println("  Registrar mesa  " );
            db.close();
*/
        }catch(Exception e){
            System.out.println("  Error  " + e.getMessage());
        }
      /*  Cursor InsMesa = db.rawQuery("INSERT INTO mesa(mesa_num,est_fk,esta_fk) VALUES ('"+numeMesa+"','"+estable+"','"+estatus+"')", null);
        //INSERT INTO mesa(mesa_num,est_fk,esta_fk) VALUES ('"numMesa"','"estable"','"estatus"'); Agrega desde la BDD
        try {
            if (InsMesa != null) {
                InsMesa.moveToFirst();
              //  num = InsMesa.getInt(InsMesa.getColumnIndex("mesa_num"));
                Globales.getInstance().numeroM = InsMesa.getString(InsMesa.getColumnIndex("mesa_num"));
            }
            InsMesa.close();
        //    db.insertOrThrow(InsMesa,null,"a");
        //    db.close();

        } catch (Exception e) {
        }db.close();*/
       // return numMesa;
    }
    public void  AgregarEstatus(Context context){
    //    String idestatus = "";3
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getWritableDatabase();
            Cursor Estatus = db.rawQuery("SELECT esta_id FROM estatus WHERE esta_estatus LIKE '%habilitado%';", null);
            try {
                if (Estatus != null) {
                    Estatus.moveToFirst();
                  //   idestatus = Estatus.getString(Estatus.getColumnIndex("esta_id"));
                    Globales.getInstance().IdEstatusMa = Estatus.getInt(Estatus.getColumnIndex("esta_id"));
                }
                Estatus.close();
                db.close();
            } catch (Exception e) {
            }
    }

    public void ConsultarEstatus( Context context, String mesaNum){
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor consultaEstatus = db.rawQuery("SELECT mesa_num, esta_estatus FROM mesa, estatus", null);
        try{
            if(consultaEstatus!=null){
                consultaEstatus.moveToFirst();
            }
            consultaEstatus.close();
            db.close();
        }catch (Exception e){
        }
    }

    public  int  loginM(Context context, String usuario, String contraseña){

        ConexionSQLiteHelper conn;
        conn=new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        String usu, pass;
        int var=0, idUsu;
        Cursor cursor2 =db.rawQuery("SELECT usr_id,usr_usuario, usr_password FROM usuario WHERE usr_usuario='"+usuario+"'  AND usr_password='"+contraseña+"' " , null);

        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    idUsu= Integer.parseInt(String.valueOf(cursor2.getColumnIndex("usr_id")));
                    Globales.getInstance().idUsuarioL=String.valueOf( cursor2.getString(cursor2.getColumnIndex("usr_id")));

                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    //Toast.makeText(context, "Exito" , Toast.LENGTH_SHORT).show();123456
                    var=1;
                }
            }
            cursor2.close();
            db.close();
        }catch(Exception e){
            Log.println(Log.ERROR,"Error ",e.getMessage());
        }
        return  var;

    }

    public void cambioDeEstatus(Context context, int id){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        consultarEstatusDesabilitado(context);
        String vari= String.valueOf(Globales.getInstance().idEstatu);
        db.execSQL("UPDATE mesa SET esta_fk="+vari+" WHERE mesa_id="+id);
        db.close();
    }

    public void consultarEstatusDesabilitado(Context context){
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor Estatu = db.rawQuery("SELECT esta_id,esta_estatus FROM estatus WHERE esta_estatus='desabilitado';", null);
        try {
            if (Estatu != null) {
                Estatu.moveToFirst();
                Globales.getInstance().idEstatu = Estatu.getInt(Estatu.getColumnIndex("esta_id"));
            }
            Estatu.close();
            db.close();
        } catch (Exception e) {
        }
    }

    public void cargarOrdenes(Context context){
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        //String variOr=String.valueOf(Globales.getInstance().ordenN);
        Cursor Orden = db.rawQuery("SELECT mesa_num, esta_fk FROM mesa", null);
        //, orden="+numOrden+"
        try {
            if (Orden != null) {
                Orden.moveToFirst();
               // Globales.getInstance().ordenN = String.valueOf(Orden.getInt(Orden.getColumnIndex("mesa_num"))); Porque no se usa
            }
            Orden.close();
            db.close();
        } catch (Exception e) {
        }
    }

    public void registrarComentario(String comentario,String ordet_id, Context contexto) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("UPDATE orden_det SET ordet_observa='"+comentario+"' WHERE ordet_id ='"+ordet_id+"'");
        System.out.println("  Comentario guardado   " );
        db.close();
    }

    public void verificarFolio(Context context){

        String folio="";
        ConexionSQLiteHelper conn;
        conn=new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        String id="";
        Cursor cursor2 =db.rawQuery("SELECT *  FROM orden" , null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("ord_id")));
                    if(id!=null) {
                        if (folio.equals(id)) {
                            index++;

                            break;
                        }
                        cursor2.moveToNext();
                    }
                }
                if (index != 0) {

                    int tamaño=id.length();
                    if(tamaño<=12){
                        String f1;
                        f1=id.substring(6);
                        int  s= Integer.parseInt(f1);
                        int s2= s+1;
                        String f5= String.valueOf(s2);
                        folio=f5;
                    }
                    if(tamaño==13){
                        String f1;
                        f1=id.substring(7);
                        int  s= Integer.parseInt(f1);
                        int s2= s+1;
                        String f5= String.valueOf(s2);
                        folio=f5;
                    }
                }
                else
                {
                    folio="1";
                }
            }
            cursor2.close();
            db.close();
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
       // return fo;


    }

    public String generarFolio() {
        String numero = Globales.getInstance().ordenN;
        final Calendar calendar = Calendar.getInstance();
        long time = System.currentTimeMillis();
        Date fecha = new Date(time);
        String s = fecha.toString();
        String[] fechaString = s.split(" ");
        int mes = calendar.get(Calendar.MONTH) + 1;
        String diaNumeroS = fechaString[2];
        String[] horas = fechaString[3].split(":");
        String horasS = horas[0];
        String minutosS = horas[1];
        String segundosS = horas[2];
        String anios = fechaString[5];
        String folioA = numero + diaNumeroS + mes + anios + horasS + minutosS + segundosS;
     //   System.out.println(" Fecha: " + diaNumeroS + "" + mes + "/" + anios + " " + horasS + ":" + minutosS + ":" + segundosS);
        System.out.println(" folioA: " + folioA);

     return folioA;
    }


}


//final SimpleDateFormat fe = new SimpleDateFormat("ddMMyyyy");
// Calendar calendar = Calendar.getInstance();
// DATETIME(`ddMMyyyy HHmmss`);
//  Fecha objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate

//  final Calendar c = Calendar.getInstance();
//   SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");




//  System.out.println(objDate);
//    String strDateFormat = "hh: mm: ss a dd-MMM-aaaa"; // El formato de fecha está especificado
//  SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); // La cadena de formato de fecha se pasa como un argumento al objeto


//String formato = "yyyy-MM-dd HH:mm:ss";
//DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
// LocalDateTime ahora = LocalDateTime.now();
// return formateador.format(ahora);
// DATETIME( "DD-MMM-YYYY hh:mm A";`20141231 235959`);
//  final Calendar calendar = Calendar.getInstance();
//dia = calendar.get(Calendar.DAY_OF_MONTH);
// mes = calendar.get(Calendar.MONTH);
// anio = calendar.get(Calendar.YEAR);
// return;