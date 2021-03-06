package com.example.comandera;

import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comandera.modelo.ConexionSQLiteHelper;
import com.example.comandera.modelo.RecyclerViewBP;
import com.example.comandera.modelo.consultas;
import com.example.comandera.modelo.datosmesa;
import com.example.comandera.ui.login.InicioSesion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class disponibilidadmesas extends AppCompatActivity {
    consultas consul= new consultas();
    Button btnproductos;
    Button btncuenta;
    GridView gridView;
    RecyclerView recyclerView;
    TextView ordenMesa;
    private boolean modoSeleccion;
    private SparseBooleanArray seleccionados;
    private View item;

    private RecyclerView.LayoutManager layoutManager;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilidadmesas);
        btnproductos = (Button) findViewById(R.id.btnproductos);

        btnproductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fol=consul.ConsultaSiExisteFolioDeMesa(getApplicationContext(),Globales.getInstance().mesa_id);
                    if(fol.length()!=0){
                        Globales.getInstance().folioEnviar=fol;
                    }else {
                        Globales.getInstance().folioEnviar = consul.generarFolio();
                    }


                  //  consul.cambioDeEstatus(getApplicationContext(),Globales.getInstance().idMesa);// se cambi el ordenN por idMesa
                    finish();
                    Intent btnproductos = new Intent(disponibilidadmesas.this,Productos.class);
                    startActivity(btnproductos);

            }
        });
        btncuenta = (Button) findViewById(R.id.btncuenta);
        btncuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btncuenta = new Intent(disponibilidadmesas.this, MostrarVentas.class);
                startActivity(btncuenta);
            }
        });
//      int num=Integer.parseInt(Globales.getInstance().numeroM);
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        validar();


    }

    public boolean onCreateOptionsMenu(Menu menu){
        /*
        //si el usuario es un mesero le mostramos tal menu, si es cocinero otro menu
        String usuario=Globales.getInstance().usuario;
        if(usuario.equals("Administrador@hotmail.com")) {
            getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
         //   Globales.getInstance().usuario=""; Limpiar el campo
        }
        //String usuario=Globales.getInstance().usuario;
        if(usuario.equals("Mesero@hotmail.com")) {
            getMenuInflater().inflate(R.menu.menuoverflowcasitamesero, menu);
            // Globales.getInstance().usuario = "";
        }
        return true;*/
        getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
        return true;
    }
    public  boolean onOptionsItemSelected(MenuItem item){
        /*
        int id= item.getItemId();
        if (id==R.id.opcion1){
            Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
            startActivity(intencion2);
        }

        if (id==R.id.opcion3){
            Intent intencion2 = new Intent(getApplication(), MenuMesero.class);
            startActivity(intencion2);
        }

        if (id==R.id.opcion2){
            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(disponibilidadmesas.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(disponibilidadmesas.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                            Toast.makeText(disponibilidadmesas.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
                        }
                    });
            alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alerta.show();
        }
        return super.onOptionsItemSelected(item);*/

        int id= item.getItemId();
        if (id==R.id.opcion1){
            Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
            startActivity(intencion2);

        }if (id==R.id.opcion2){
            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(disponibilidadmesas.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(disponibilidadmesas.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                            Toast.makeText(disponibilidadmesas.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
                        }
                    });
            alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alerta.show();
        }
        return super.onOptionsItemSelected(item);
    //}
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void cargarMesas() {
        final ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(getApplicationContext());
        SQLiteDatabase db = conn.getWritableDatabase();
        final List<datosmesa> arrayList = new ArrayList<datosmesa>();
        final Drawable imagen = ContextCompat.getDrawable(getApplicationContext(), R.drawable.mesita);
        String mesa="";
        Cursor cursor2 = db.rawQuery("select mesa_id,est_fk, mesa_num from mesa", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {

                    mesa = String.valueOf(cursor2.getString(cursor2.getColumnIndex("mesa_num")));
                    String id = String.valueOf(cursor2.getString(cursor2.getColumnIndex("mesa_id")));
                    String estatu = String.valueOf(cursor2.getString(cursor2.getColumnIndex("est_fk")));

                   // String mes = Globales.getInstance().nombreMesa + mesa;
                    String mes = Globales.getInstance().nombreMesa+mesa;
                  //  String[] var2=Globales.getInstance().nombreMesa.split( " ");
                   // String var3=var2[1];
                    arrayList.add(new datosmesa(mes, imagen, id));
                   // consul.consultarEstatusDesabilitado(getApplicationContext());
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    RecyclerViewBP adapter = new RecyclerViewBP((ArrayList<datosmesa>) arrayList);
                    adapter.setOnClickListener((new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Globales.getInstance().folioEnviar=consul.generarFolio();
                            //boolean v= new boolean[true];
                            int elemen = recyclerView.getChildAdapterPosition(v);
                            if (!v.isSelected()) {

                                ////PENDIENTE POR HACER

                                consul.consutaElEstatusHabilidato(getApplicationContext());
                                int idEstatus=Globales.getInstance().IdEstatusMa;
                                v.setSelected(true);//Rojo
                                String id= arrayList.get(elemen).getid();
                                Globales.getInstance().mesa_id=id;
                                String r= arrayList.get(elemen).getNombre();
                                int vari= consul.consultarIdMesaEstatus(getApplicationContext(),id);
                                Globales.getInstance().ordenN = arrayList.get(elemen).getNombre();
                                String[] var2=r.split( " ");
                                String var3=var2[1];
                                Globales.getInstance().idMesa=var3;
                                consul.consultarEstatusDesabilitado(getApplicationContext());
                                int idEstatuDesabilitado=Globales.getInstance().idEstatu;

                                if(vari==idEstatus){
                                    //rojo
                                    v.setBackgroundColor(Color.parseColor("#F04B27"));
                                    consul.cambioDeEstatusDisponi(getApplicationContext(),Globales.getInstance().idMesa,idEstatuDesabilitado);
                                }
                                if(vari!=idEstatus){
                                    v.setBackgroundColor(Color.parseColor("#7EF172"));
                                    consul.cambioDeEstatusDisponi(getApplicationContext(),Globales.getInstance().idMesa,idEstatus);
                                }

                                if(vari==idEstatuDesabilitado){
                                    //verde
                                    v.setBackgroundColor(Color.parseColor("#7EF172"));
                                    consul.cambioDeEstatusDisponi(getApplicationContext(),Globales.getInstance().idMesa,idEstatus);
                                }

                            } else {
                                //verde
                                v.setSelected(false);
                                v.setBackgroundColor(Color.parseColor("#7EF172"));
                            }

                        }

                    }));
                    recyclerView.setAdapter(adapter);

                }

            }


        } catch (Exception e) {
            //

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void validar() {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(getApplicationContext());
        SQLiteDatabase db = conn.getWritableDatabase();
        final List<datosmesa> arrayList = new ArrayList<datosmesa>();
        Drawable imagen = ContextCompat.getDrawable(getApplicationContext(), R.drawable.mesita);
        Cursor cursor2 =db.rawQuery("select * from mesa", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;

                while (!cursor2.isAfterLast()) {
                    String mesa= String.valueOf( cursor2.getString(cursor2.getColumnIndex("mesa_num")));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    cargarMesas();
                }
                else{
                    LayoutInflater imagenadvertencia_alert = LayoutInflater.from(disponibilidadmesas.this);
                    final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
                    AlertDialog.Builder alerta = new AlertDialog.Builder(disponibilidadmesas.this);
                    alerta.setMessage("Debe ingresar un número en Configuracion de Mesa")
                            .setCancelable(true)
                            .setCustomTitle(vista)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
                                    startActivity(intencion2);
                                }
                            });
                    alerta.show();
                }

            }

        }catch(Exception e){
            //
        }
    }

}
