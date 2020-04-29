package com.example.comandera;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandera.modelo.ConexionSQLiteHelper;
import com.example.comandera.modelo.DatosOrdenesAPreparar;
import com.example.comandera.modelo.Ingresarsql;
import com.example.comandera.modelo.RecyclerViewDatosOrdenes;
import com.example.comandera.modelo.RecyclerViewDatosOrdenesAPreparar;
import com.example.comandera.modelo.consultas;
import com.example.comandera.modelo.datosOrdenes;
import com.example.comandera.ui.login.InicioSesion;

import java.util.ArrayList;
import java.util.List;

public class ordenes_a_preparar extends AppCompatActivity {
    consultas consul = new consultas();
    Spinner spinnerlistaEstatusAPreparar;
    int contador = 0;
    Button btndetalleorden, btncancelarordpreparar;
    ConexionSQLiteHelper conn;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    final Ingresarsql sq = new Ingresarsql();
    RecyclerView recyclerViewClasificacionProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oredeness_a_preparar);

       // spinnerlistaEstatusAPreparar = (Spinner) findViewById(R.id.estatusordpreparar);

        //  btndetalleorden = (Button) findViewById(R.id.btndetalleorden);
        //  btncancelarordpreparar=(Button) findViewById(R.id.btncancelarordpreparar);
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        conn = new ConexionSQLiteHelper(getApplicationContext());
        String folio = Globales.getInstance().folioEnviar;
        consul.actualizarEstadoOrden(folio, getApplicationContext());//estado por preprar

        cargarEstatusOrdenesAPreparar();
        cargaTodasLasOrdenesAPreparar();

       // consul.ModificaEstatusEntregada(getApplicationContext());
/* se ocuptaron los botones para el DETALLE DE LA ORDEN

        btndetalleorden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnordennueva = new Intent(ordenes_a_preparar.this, Productos.class);
                startActivity(btnordennueva);

            }

        });

        btncancelarordpreparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnordennueva = new Intent(ordenes_a_preparar.this, MostrarVentas.class);
                startActivity(btnordennueva);
            }

        });
*/
   /*     spinnerlistaEstatusAPreparar.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn, View v, int posicion, long id) {

                        // Toast.makeText(spn.getContext(), "Esatus: " + spn.getItemAtPosition(posicion).toString(), Toast.LENGTH_LONG).show();
                        if (contador != 0) {
                            // cargarProductosConCaracteristica();
                            //  cargaPorEstatusLasOrdenes();
                        }
                        if (contador == 0) {
                            contador++;
                        }
                    }

                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
      /*  if(Globales.getInstance().regresaMenuCocinero==1){
            finish();
            startActivity(new Intent(getApplicationContext(),MenuCocinero.class));
            Globales.getInstance().regresaMenuCocinero=0;
        }else {
            Intent intencion2 = new Intent(getApplication(), ordenes_a_preparar.class);
            startActivity(intencion2);
            finish();
        }*/
       return true;

        //De los usuarios
        //   getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);

     /*   String usuario=Globales.getInstance().usuario;
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

    }

    public boolean onOptionsItemSelected(MenuItem item) {


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
        int id = item.getItemId();
        if (id == R.id.opcion1) {
            Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
            startActivity(intencion2);
        }

        if (id == R.id.opcion2) {
            LayoutInflater imagenadvertencia_alert = LayoutInflater.from(ordenes_a_preparar.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(ordenes_a_preparar.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                            Toast.makeText(ordenes_a_preparar.this, "Sesión  Cerrada", Toast.LENGTH_LONG).show();
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

    public void cargarEstatusOrdenesAPreparar() {
        SQLiteDatabase db = conn.getReadableDatabase();
        List<String> listaDatosAPreparar = new ArrayList<>();
        listaDatosAPreparar.clear();
        sq.consultaEstatusEnProceso(getApplicationContext());
        consul.consultaEstatusPorPreparar(getApplicationContext());
        sq.consultaEstatusPorPagar(getApplicationContext());
        sq.consultaEstatusPagada(getApplicationContext());
        sq.consultaEstatusPreparado(getApplicationContext());

        int enProceso = Globales.getInstance().idEstatusEnProceso;
        int porPreparar = Globales.getInstance().idEstatusPorPreparar;
        int preparado = Globales.getInstance().preparado;
        int porPagar = Globales.getInstance().idEstatusPorPagar;
        int pagada = Globales.getInstance().idEstatusPagado;

        //listaDatos.add("Seleccione");
        //    listaDatos.add("Todos");

        // Cursor cursor2 =db.rawQuery("SELECT esta_estatus  from estatus  WHERE esta_id='"+porPagar+"' OR esta_id='"+pagado+"' OR esta_id='"+cancelada+"' OR esta_id='"+enProceso+"'", null);
        Cursor cursor2 = db.rawQuery("SELECT esta_id, esta_estatus FROM estatus WHERE esta_id='" + enProceso + "' or  esta_id='" + porPreparar + "' or  esta_id='" + preparado + "' or  esta_id='" + porPagar + "' or  esta_id='" + pagada + "' ", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {

                    String nombre = String.valueOf(cursor2.getString(cursor2.getColumnIndex("esta_estatus")));
                    listaDatosAPreparar.add(nombre);
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaDatosAPreparar);
                    spinnerlistaEstatusAPreparar.setAdapter(adapter);

                } else {
                    Toast.makeText(ordenes_a_preparar.this, "No hay concidencias4.", Toast.LENGTH_SHORT).show();
                    listaDatosAPreparar.clear();
                    spinnerlistaEstatusAPreparar.setAdapter(null);
                }
            }
        } catch (Exception e) {
            Log.println(Log.ERROR, "", e.getMessage());
        }
    }


    private void cargaTodasLasOrdenesAPreparar() {
        Globales.getInstance().listaRVOrdenesPreparar.clear();
        SQLiteDatabase db = conn.getReadableDatabase();
        //Cursor cursor2 = db.rawQuery("SELECT mesa_num, ord_id, orden.esta_fk, estatus.esta_estatus FROM orden INNER JOIN mesa on orden.mesa_fk=mesa.mesa_id INNER JOIN estatus ON orden.esta_fk=estatus.esta_id ", null);
        Cursor cursor2 = db.rawQuery("SELECT mesa_num, ord_id, orden.esta_fk, esta_estatus FROM orden INNER JOIN mesa on orden.mesa_fk=mesa.mesa_id INNER JOIN estatus ON orden.esta_fk=estatus.esta_id ", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    String cantidadNumProductos = "";
                    String mesa_num = String.valueOf(cursor2.getString(cursor2.getColumnIndex("mesa_num")));
                    String ord_id = String.valueOf(cursor2.getString(cursor2.getColumnIndex("ord_id")));
                    String ordenesta_fk = String.valueOf(cursor2.getString(cursor2.getColumnIndex("esta_estatus")));// checar lo del punto +++ la consulta
                  //  String ordenesta_fk = String.valueOf(cursor2.getString(cursor2.getColumnIndex("esta_estatus")));

                    //String numeroPrepara="1";
                    ///////////////////////////////
                    Cursor cursor = db.rawQuery("select sum(ordet_cant) as cantidadProductos FROM orden_det  INNER JOIN orden ON orden_det.ord_fk=orden.ord_id  INNER JOIN mesa ON mesa.mesa_id= orden.mesa_fk WHERE ord_fk='" + ord_id + "'", null);
                    try {
                        if (cursor != null) {
                            cursor.moveToFirst();
                            int index2 = 0;
                            while (!cursor.isAfterLast()) {
                                cantidadNumProductos = String.valueOf(cursor.getString(cursor.getColumnIndex("cantidadProductos")));


                                index2++;
                                cursor.moveToNext();
                            }

                        }
                        cursor.close();
                        // db.close();
                    } catch (Exception e) {
                        Log.println(Log.ERROR, "", e.getMessage());
                    }
                    //////////////////////////////////
                    Globales.getInstance().listaRVOrdenesPreparar.add(new DatosOrdenesAPreparar(mesa_num, ord_id, cantidadNumProductos, ordenesta_fk));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                  /*  final RecyclerViewDatosOrdenes adaptador = new RecyclerViewDatosOrdenes((ArrayList<datosOrdenes>)  Globales.getInstance().listaRV);
                    recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
                    recyclerView.setAdapter(adaptador);*/

              /*     final RecyclerViewDatosOrdenesAPreparar adaptador = new RecyclerViewDatosOrdenesAPreparar((ArrayList<DatosOrdenesAPreparar>)  Globales.getInstance().listaRVOrdenesPreparar);
                    recyclerView.setAdapter(adaptador);*/

                    final RecyclerViewDatosOrdenesAPreparar adaptador = new RecyclerViewDatosOrdenesAPreparar((ArrayList<DatosOrdenesAPreparar>) Globales.getInstance().listaRVOrdenesPreparar);
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("DemoRecView", "Pulsado el elemento " + recyclerView.getChildAdapterPosition(v));
                            int elemen = recyclerView.getChildAdapterPosition(v);
                            System.out.println(" Pulsado el elemento    " + Globales.getInstance().listaRVOrdenesPreparar.get(elemen).getEstatuAPrepar());
                            String numeroDeMesa = Globales.getInstance().listaRVOrdenesPreparar.get(elemen).getNumeroDeMesa();
                            int identificadorClasificacion = Integer.parseInt(Globales.getInstance().listaRVOrdenesPreparar.get(elemen).getIdOrdPreparar());
                            String cantidadNumProductos = Globales.getInstance().listaRVOrdenesPreparar.get(elemen).getNumDeProductos();
                            // String numMesaPrepa="1";

                            Intent intencion = new Intent(getApplication(), cambia_estatus_venta.class);
                            Globales.getInstance().idDeLaOrdenABuscar = identificadorClasificacion;
                            Globales.getInstance().ordenN = "Mesa" + numeroDeMesa;
                            Globales.getInstance().idMesa = numeroDeMesa;
                            // cantidadNumProductos=numeroDeProducto;
                            Globales.getInstance().regresarOrdenes = 1;
                            startActivity(intencion);
                            finish();
                        }
                    });
                    recyclerView.setAdapter(adaptador);
                    Toast.makeText(ordenes_a_preparar.this, "Datos cargados.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ordenes_a_preparar.this, "No hay concidencias.", Toast.LENGTH_SHORT).show();
                    Globales.getInstance().listaRV.clear();
                    recyclerView.setAdapter(null);
                }
            }
        } catch (Exception e) {
            Log.println(Log.ERROR, "", e.getMessage());
        }
    }

}
