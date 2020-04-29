package com.example.comandera;
import android.accounts.NetworkErrorException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandera.modelo.ConexionSQLiteHelper;
import com.example.comandera.modelo.ProductoAdapter;
import com.example.comandera.modelo.ProductoAdapterCocinero;
import com.example.comandera.modelo.Productoss;
import com.example.comandera.modelo.ProductossCocinero;
import com.example.comandera.modelo.consultas;
import com.example.comandera.ui.login.InicioSesion;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class cambia_estatus_venta extends AppCompatActivity implements ProductossCocinero.MiListenerCocinero{
    ConexionSQLiteHelper conn;
    consultas consul= new consultas();

    RecyclerView recyclerView;
    TextView ocupacionmesa3;
    ProductoAdapterCocinero adaptaProdsListaCocinero;
  //  ArrayList<ProductossCocinero>productossCocineros;
    int bandera=0;
    double precioUnitario=0.0;
    String nomb;
    int cant;
    private RecyclerView.LayoutManager layoutManager;
    Button btnPorPreparar, btnEntregada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambia_estatus_venta);
        ocupacionmesa3= findViewById(R.id.ocupacionmesa3);
        String nommesa=Globales.getInstance().ordenN;
        ocupacionmesa3.setText(nommesa);
        recyclerView = findViewById(R.id.recyclerViewC);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        conn=new ConexionSQLiteHelper(getApplicationContext());
        //productossCocineros = new ArrayList<ProductossCocinero>();



    //  adaptaProdsListaCocinero = new ProductoAdapterCocinero(productossCocineros);
     //   adaptaProdsListaCocinero=new ProductoAdapterCocinero(cambia_estatus_venta.this,productossCocineros); QUITAR
        consultaSQL2();
    //    recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnPorPreparar = (Button) findViewById(R.id.btnPorPreparar);
        btnEntregada = (Button) findViewById(R.id.btnEntregada);


        btnPorPreparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Envia los productos en la tabla ordenes
                Globales.getInstance().idOrden="";
                String folio=Globales.getInstance().folioEnviar;
                consul.actualizarEstadoOrden(folio,getApplicationContext());//estado por preprar
                Intent btnPorPreparar = new Intent(cambia_estatus_venta.this, ordenes_a_preparar.class);
                startActivity(btnPorPreparar);
                finish();
            }
        });

        btnEntregada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Envia los productos en la tabla ordenes
                Globales.getInstance().idOrden="";
                String folio=Globales.getInstance().folioEnviar;
                //consul.actualizarEstadoOrden(folio,getApplicationContext());//estado por preprar
                //consul.consultaEstatusEntregada(getApplicationContext());

                consul.ModificaEstatusEntregada(getApplicationContext());
                ///glob jnkjkj id  entraga


                ///funcion que  haga un update  que cambie el estatus tanto de la orden como de los productos



                Intent btnEntregada = new Intent(cambia_estatus_venta.this, ordenes_a_preparar.class);
                startActivity(btnEntregada);
                finish();

            }
        });


    }

    //CONSULTA A BD PARA OBTENER LOS PRODUCTOS DEL X VENTA
    private void consultaSQL2() {
        List<ProductossCocinero> producto = new ArrayList<ProductossCocinero>();
      //  productossCocineros.clear();
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor2 =db.rawQuery("SELECT ord_id, ord_folio,ordet_cant, prst_descripcion,ordet_observa, prd_nombre, prd_imagen,prepro_precompra, orden_det.prepro_fk\n" +
                "FROM estatus, orden, orden_det, producto, prest_prod \n" +
                "INNER JOIN presentacion ON presentacion.prst_id = prest_prod.prst_fk\n" +
                "WHERE estatus.esta_estatus='por preparar' AND orden.esta_fk=estatus.esta_id  \n" +
                "AND orden.ord_id = orden_det.ord_fk\n" +
                "AND orden_det.prepro_fk = prest_prod.prepro_id\n" +
                "AND prest_prod.prd_fk = producto.prd_id", null);

        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                Bitmap bitmap = null;
                while (!cursor2.isAfterLast()){

                    int index1=index;
                    precioUnitario = Double.parseDouble(cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                    nomb= cursor2.getString(cursor2.getColumnIndex("prd_nombre"));
                    cant= cursor2.getInt(cursor2.getColumnIndex("ordet_cant"));///////////////////////////////////


                    Globales.getInstance().idDetalle = cursor2.getInt(cursor2.getColumnIndex("ord_id"));
                    //productsID[index] = cursor2.getInt(cursor2.getColumnIndex("prepro_fk"));
                    //Se obtiene la imagen del producto seleccionado
                    byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                    ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(bais);
                    //var para presentacion
                    String presentacion = cursor2.getString(cursor2.getColumnIndex("prst_descripcion"));

                    //String estatusPreparacion=consul.consultaEstatusDePreparacion(getApplicationContext());
                    //String estatusprepro="holaaaa";
//////////DUDA
                    //Asignación de la información
                    producto.add(new ProductossCocinero(bitmap,index1+1+" "+ nomb,cant,precioUnitario,presentacion));
                   // ProductossCocinero producto = new ProductossCocinero(bitmap,index1+1+" "+ nomb,cant,precioUnitario,presentacion);
                    //productossCocineros.add(producto);

                   // btnMontoTotal.setText("Registrar venta \t\n\t $ " +String.format("%.2f", toTal));
                  //  Globales.getInstance().TotVenta =  toTal;
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {

                    final ProductoAdapterCocinero adaptador = new ProductoAdapterCocinero((ArrayList<ProductossCocinero>) producto);

                    recyclerView.setAdapter(adaptador);


                   // recyclerView.setAdapter(adaptaProdsListaCocinero);
                }
                bandera++;
            }

        }catch(Exception e){
        }
    }

   /* public void ConsultaSQLi3(){
        consultaSQL2();
    }*/

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
        return true;
        //De los usuarios
       // getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
/*
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
            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(cambia_estatus_venta.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(cambia_estatus_venta.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                            Toast.makeText(cambia_estatus_venta.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
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
}
