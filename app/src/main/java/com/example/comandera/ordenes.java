package com.example.comandera;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comandera.modelo.ClasificacionDatos;
import com.example.comandera.modelo.ConexionSQLiteHelper;
import com.example.comandera.modelo.Ingresarsql;
import com.example.comandera.modelo.RecyclerViewClasificacion;
import com.example.comandera.modelo.RecyclerViewDatosOrdenes;
import com.example.comandera.modelo.consultas;
import com.example.comandera.modelo.datosOrdenes;
import com.example.comandera.ui.login.InicioSesion;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class ordenes extends AppCompatActivity {
    consultas consul= new consultas();
    Spinner spinnerlistaEstatus;
    int contador=0;
    Button btnordennueva;
   //Button btnproductos;
    Button btnordencuenta;
    ConexionSQLiteHelper conn;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    final Ingresarsql sq = new Ingresarsql();
    RecyclerView recyclerViewClasificacionProductos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);
        recyclerView = findViewById(R.id.recyclerView2);
       recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        spinnerlistaEstatus=(Spinner)findViewById(R.id.estatus);
        btnordennueva = (Button) findViewById(R.id.btnordennueva);
        btnordencuenta=(Button) findViewById(R.id.btnordencuenta);
        conn=new ConexionSQLiteHelper(getApplicationContext());
        cargarEstatus();
        cargaTodasLasOrdenes();




        btnordennueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnordennueva = new Intent(ordenes.this, Productos.class);
                startActivity(btnordennueva);

            }

        });

        btnordencuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnordennueva = new Intent(ordenes.this, MostrarVentas.class);
                startActivity(btnordennueva);
            }

        });

        //manda a llamar lo de Carrgar ordenes
        consul.cargarOrdenes(getApplicationContext());
/////Agregar lo de spiner
        spinnerlistaEstatus.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn, View v, int posicion, long id) {

                       // Toast.makeText(spn.getContext(), "Esatus: " + spn.getItemAtPosition(posicion).toString(), Toast.LENGTH_LONG).show();
                        if(contador!=0) {
                           // cargarProductosConCaracteristica();
                            cargaPorEstatusLasOrdenes();
                        }
                        if (contador==0){
                            contador++;
                        }
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });
      //  consul.MostrarOrdenesEnLaTabla(getApplicationContext());


    }




    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
        return true;
        //De los usuarios
     //   getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);

      /*  String usuario=Globales.getInstance().usuario;
        if(usuario.equals("Administrador@hotmail.com")) {
            getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
            //   Globales.getInstance().usuario=""; Limpiar el campo
        }
        //String usuario=Globales.getInstance().usuario;
        if(usuario.equals("Mesero@hotmail.com")) {
            getMenuInflater().inflate(R.menu.menuoverflowcasitamesero, menu);
            // Globales.getInstance().usuario = "";


        }
        return true;
        */
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        /*int id= item.getItemId();
        if (id==R.id.opcion1){
            Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
            startActivity(intencion2);
            //  Toast.makeText(ConfiguracionMesa.this,"Bienvenido !",Toast.LENGTH_LONG).show();
        }

        if (id==R.id.opcion3){
            Intent intencion2 = new Intent(getApplication(), MenuMesero.class);
            startActivity(intencion2);
        }

        if (id==R.id.opcion2){
            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(ordenes.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(ordenes.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                            Toast.makeText(ordenes.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
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
    }*/
        int id= item.getItemId();
        if (id==R.id.opcion1){
            Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
            startActivity(intencion2);

        }if (id==R.id.opcion2){
            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(ordenes.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(ordenes.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                            Toast.makeText(ordenes.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
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
    }

    private void cargaTodasLasOrdenes() {
        Globales.getInstance().listaRV.clear();
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor2 =db.rawQuery("SELECT mesa_num, ord_id, orden.esta_fk, estatus.esta_estatus FROM orden INNER JOIN mesa on orden.mesa_fk=mesa.mesa_id INNER JOIN estatus ON orden.esta_fk=estatus.esta_id ", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    String mesa_num= String.valueOf( cursor2.getString(cursor2.getColumnIndex("mesa_num")));
                    String ord_id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("ord_id")));
                    String ordenesta_fk= String.valueOf( cursor2.getString(cursor2.getColumnIndex("estatus.esta_estatus")));

                    Globales.getInstance().listaRV.add(new datosOrdenes(mesa_num,ord_id,ordenesta_fk));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                  /*  final RecyclerViewDatosOrdenes adaptador = new RecyclerViewDatosOrdenes((ArrayList<datosOrdenes>)  Globales.getInstance().listaRV);
                    recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
                    recyclerView.setAdapter(adaptador);*/

                  /*  final RecyclerViewDatosOrdenes adaptador = new RecyclerViewDatosOrdenes((ArrayList<datosOrdenes>)  Globales.getInstance().listaRV);
                    recyclerView.setAdapter(adaptador);*/

                   final RecyclerViewDatosOrdenes adaptador = new RecyclerViewDatosOrdenes((ArrayList<datosOrdenes>) Globales.getInstance().listaRV);
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("DemoRecView", "Pulsado el elemento " + recyclerView.getChildAdapterPosition(v));
                            int elemen=   recyclerView.getChildAdapterPosition(v);
                            System.out.println(" Pulsado el elemento    " + Globales.getInstance().listaRV.get(elemen).getEstatus());
                            String numeroDeMesa= Globales.getInstance().listaRV.get(elemen).getNumMesa();
                            int identificadorClasificacion= Integer.parseInt(Globales.getInstance().listaRV.get(elemen).getOrdenId());
                            Intent intencion = new Intent(getApplication(), Productos.class);
                            Globales.getInstance().idDeLaOrdenABuscar=identificadorClasificacion;
                            Globales.getInstance().ordenN="Mesa"+numeroDeMesa;
                            Globales.getInstance().idMesa=numeroDeMesa;
                            Globales.getInstance().regresarOrdenes=1;
                            startActivity(intencion);
                            finish();
                        }
                    });
                    recyclerView.setAdapter(adaptador);
                    Toast.makeText(ordenes.this, "Datos cargados.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ordenes.this,"No hay concidencias.",Toast.LENGTH_SHORT).show();
                    Globales.getInstance().listaRV.clear();
                    recyclerView.setAdapter(null);
                }


            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }

    private void cargaPorEstatusLasOrdenes() {
        String estatus = spinnerlistaEstatus.getSelectedItem().toString();
        Globales.getInstance().listaRV.clear();
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor2 =db.rawQuery("SELECT mesa_num, ord_id, orden.esta_fk, estatus.esta_estatus FROM orden INNER JOIN mesa on orden.mesa_fk=mesa.mesa_id INNER JOIN estatus ON orden.esta_fk=estatus.esta_id WHERE estatus.esta_estatus='"+estatus+"'", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    String mesa_num= String.valueOf( cursor2.getString(cursor2.getColumnIndex("mesa_num")));
                    String ord_id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("ord_id")));
                    String ordenesta_fk= String.valueOf( cursor2.getString(cursor2.getColumnIndex("estatus.esta_estatus")));
                    Globales.getInstance().listaRV.add(new datosOrdenes(mesa_num,ord_id,ordenesta_fk));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    /*final RecyclerViewDatosOrdenes adaptador = new RecyclerViewDatosOrdenes((ArrayList<datosOrdenes>)  Globales.getInstance().listaRV);
                    recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
                    recyclerView.setAdapter(adaptador);*/

                 /*   final RecyclerViewDatosOrdenes adaptador = new RecyclerViewDatosOrdenes((ArrayList<datosOrdenes>)  Globales.getInstance().listaRV);
                    recyclerView.setAdapter(adaptador);
                    Toast.makeText(ordenes.this, "Datos cargados.", Toast.LENGTH_SHORT).show();*/
                    final RecyclerViewDatosOrdenes adaptador = new RecyclerViewDatosOrdenes((ArrayList<datosOrdenes>) Globales.getInstance().listaRV);
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("DemoRecView", "Pulsado el elemento " + recyclerView.getChildAdapterPosition(v));
                            int elemen=   recyclerView.getChildAdapterPosition(v);
                            System.out.println(" Pulsado el elemento    " + Globales.getInstance().listaRV.get(elemen).getEstatus());
                            String numeroDeMesa= Globales.getInstance().listaRV.get(elemen).getNumMesa();
                            int identificadorClasificacion= Integer.parseInt(Globales.getInstance().listaRV.get(elemen).getOrdenId());
                            Intent intencion = new Intent(getApplication(), Productos.class);
                            Globales.getInstance().idDeLaOrdenABuscar=identificadorClasificacion;
                            Globales.getInstance().ordenN="Mesa"+numeroDeMesa;
                            Globales.getInstance().idMesa=numeroDeMesa;
                            Globales.getInstance().regresarOrdenes=1;
                            startActivity(intencion);
                            finish();
                        }
                    });
                    recyclerView.setAdapter(adaptador);
                    Toast.makeText(ordenes.this, "Datos cargados.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ordenes.this,"No hay concidencias.",Toast.LENGTH_SHORT).show();
                    Globales.getInstance().listaRV.clear();
                    recyclerView.setAdapter(null);
                }


            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }


    public  void  cargarEstatus(){
        SQLiteDatabase db = conn.getReadableDatabase();
        List<String> listaDatos = new ArrayList<>();
        listaDatos.clear();
        sq.consultaEstatusEnProceso(getApplicationContext());
        consul.consultaEstatusPorPreparar(getApplicationContext());
        sq.consultaEstatusPorPagar(getApplicationContext());
        sq.consultaEstatusPagada(getApplicationContext());
        sq.consultaEstatusPreparado(getApplicationContext());

        int enProceso= Globales.getInstance().idEstatusEnProceso;
        int porPreparar= Globales.getInstance().idEstatusPorPreparar;
        int preparado=Globales.getInstance().preparado;
        int porPagar=Globales.getInstance().idEstatusPorPagar;
        int pagada=Globales.getInstance().idEstatusPagado;

        //listaDatos.add("Seleccione");
    //    listaDatos.add("Todos");

       // Cursor cursor2 =db.rawQuery("SELECT esta_estatus  from estatus  WHERE esta_id='"+porPagar+"' OR esta_id='"+pagado+"' OR esta_id='"+cancelada+"' OR esta_id='"+enProceso+"'", null);
        Cursor cursor2 =db.rawQuery("SELECT esta_id, esta_estatus FROM estatus WHERE esta_id='"+enProceso+"' or  esta_id='"+porPreparar+"' or  esta_id='"+preparado+"' or  esta_id='"+porPagar+"' or  esta_id='"+pagada+"' ", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("esta_estatus")));
                    listaDatos.add(nombre);
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaDatos);
                    spinnerlistaEstatus.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(ordenes.this,"No hay concidencias4.",Toast.LENGTH_SHORT).show();
                    listaDatos.clear();
                    spinnerlistaEstatus.setAdapter(null);
                }
            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }

  /*  private void cargarClasificacionesDeOrdenes() {
        //sq.contabilizaLosProdAgreCarr(getApplicationContext());
        SQLiteDatabase db = conn.getReadableDatabase();
        List<String> list3 = new ArrayList<String>();
        final List<ClasificacionDatos> listclientes = new ArrayList<ClasificacionDatos>();
        listclientes.clear();
        Cursor cursor2 =db.rawQuery("select * from clasificacion", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                Bitmap bitmap = null;
                while (!cursor2.isAfterLast()) {
                    String idClas= String.valueOf( cursor2.getString(cursor2.getColumnIndex("clas_id")));
                    String nombreClas= String.valueOf( cursor2.getString(cursor2.getColumnIndex("clas_nombre")));
                    byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("clas_imagen"));
                    ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(bais);
                    listclientes.add(new ClasificacionDatos(bitmap, nombreClas,idClas));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    ///////////////1 CC
                    final RecyclerViewClasificacion adaptador = new RecyclerViewClasificacion((ArrayList<ClasificacionDatos>) listclientes);
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("DemoRecView", "Pulsado el elemento " + recyclerViewClasificacionProductos.getChildAdapterPosition(v));
                            int elemen=   recyclerViewClasificacionProductos.getChildAdapterPosition(v);
                            System.out.println(" Pulsado el elemento    " + listclientes.get(elemen).getNombreClasificacion());
                            String nombreclasificacion= listclientes.get(elemen).getNombreClasificacion();
                            int identificadorClasificacion= Integer.parseInt(listclientes.get(elemen).getIdentificador());
                            Intent intencion = new Intent(getApplication(), Productos.class);
                            Globales.getInstance().idDeLaOrdenABuscar=identificadorClasificacion;

                            startActivity(intencion);
                            finish();
                        }
                    });
                    recyclerViewClasificacionProductos.setAdapter(adaptador);
                }

            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }*/



}
