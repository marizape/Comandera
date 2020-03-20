package com.example.comandera;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandera.modelo.ClasificacionDatos;
import com.example.comandera.modelo.ConexionSQLiteHelper;
import com.example.comandera.modelo.Ingresarsql;
import com.example.comandera.modelo.ProductosDatos;
import com.example.comandera.modelo.RecyclerViewClasificacion;
import com.example.comandera.modelo.RecyclerViewLL;
import com.example.comandera.modelo.consultas;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Productos extends AppCompatActivity {
    //  Button canclar;
    consultas consul= new consultas();
    EditText busqueda,thide;
    int[] ids= new int[100];
    ImageButton botonBuscar;
    public static TextView cuentaProductosC;
    View separador;
    ImageView imageView5;
    ImageButton  botonTeclado,botonTecladoOculto;
    Button regresar;
    ConexionSQLiteHelper conn;
    Ingresarsql sq = new Ingresarsql();

    TextView fol,doc,cuentaProductos3,ocupacionmesa;
    RecyclerView recyclerViewClasificacionProductos;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        busqueda= findViewById(R.id.busqueda2);
        botonBuscar= findViewById(R.id.btnBuscar2);
        regresar = findViewById(R.id.btnAceptar);
        recyclerViewClasificacionProductos= findViewById(R.id.productos);
        cuentaProductosC= findViewById(R.id.cuentaProductosC);
        cuentaProductos3= findViewById(R.id.cuentaProductos3);

        botonTeclado= findViewById(R.id.btnTeclado);
        botonTecladoOculto= findViewById(R.id.btnTecladoOculto);

        conn=new ConexionSQLiteHelper(getApplicationContext());
        imageView5= findViewById(R.id.imageView5);
        separador= findViewById(R.id.separador);
        //busqueda.setVisibility(View.INVISIBLE);
        botonTecladoOculto.setVisibility(View.INVISIBLE);
        botonTecladoOculto.setEnabled(false);
        busqueda.setEnabled(false);

        ocupacionmesa= findViewById(R.id.ocupacionmesa);

        String nommesa=Globales.getInstance().idMesa;
        ocupacionmesa.setText(nommesa);

        String vari= String.valueOf(Globales.getInstance().idDeLaOrdenABuscar);
        if( vari.length()!=0){
            consul.IdDeLaOrdenABuscar(getApplicationContext());
            String nommesa2=Globales.getInstance().idMesa;
            ocupacionmesa.setText(nommesa2);
        }
        layoutManager = new LinearLayoutManager(this);
        recyclerViewClasificacionProductos.setLayoutManager(layoutManager);
        botonTeclado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busqueda.setEnabled(true);
                busqueda.setHint("Buscar producto");
                // busqueda.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                sq.contabilizaLosProdAgreCarr(getApplicationContext());
                busqueda.setVisibility(View.VISIBLE);
                botonTeclado.setVisibility(View.INVISIBLE);
                botonTecladoOculto.setVisibility(View.VISIBLE);
                botonTecladoOculto.setEnabled(true);
                Toast.makeText(getApplicationContext(),"Teclado activado", Toast.LENGTH_SHORT).show();
                busqueda.requestFocus(); //Asegurar que editText tiene focus
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(busqueda, InputMethodManager.SHOW_IMPLICIT);
            }
        });




        botonTecladoOculto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                busqueda.setEnabled(false);
                busqueda.setHint("Activar Teclado  →");
                busqueda.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                sq.contabilizaLosProdAgreCarr(getApplicationContext());
                busqueda.setVisibility(View.VISIBLE);

                botonTeclado.setVisibility(View.VISIBLE);
                botonTecladoOculto.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Teclado desactivado", Toast.LENGTH_SHORT).show();
                botonTecladoOculto.setEnabled(false);
                busqueda.requestFocus(); //Asegurar que editText tiene focus
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(busqueda.getWindowToken(), 0);

            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Productos.this, clasificacionSeleccionada.class));
            }
        });
        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //System.out.println(s.toString() + " " + start + " " + count + " " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //System.out.println(s.toString() + " " + start + " " + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //cargarTodosLosProductos();

                sq.contabilizaLosProdAgreCarr(getApplicationContext());
                cargarClasificaciones();
            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sq.contabilizaLosProdAgreCarr(getApplicationContext());
               // buscarProducto();
                String query = busqueda.getText().toString();
                if(query.equals("")){
                    cargarClasificaciones();
                }
                else {
                    buscarProducto();
                }
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //cambiar
                startActivity(new Intent(Productos.this, MostrarVentas.class));
            }
        });

        cuentaProductosC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Productos.this, MostrarVentas.class));
            }
        });
/*
        separador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Productos.this, MostrarVentas.class));
            }
        });


        cuentaProductosC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Productos.this, MostrarVentas.class));
            }
        });


        cuentaProductos3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Productos.this, MostrarVentas.class));
            }
        });
*/

        cargarClasificaciones();

        // getJSON("https://www.nextcom.com.mx/webserviceapp/koonol/Consulta_clasificacion.php");


//        sq.contabilizaLosProdAgreCarr(getApplicationContext());
//        recuperarFolio(getApplicationContext());


        fol= findViewById(R.id.fol);
        doc= findViewById(R.id.fol2);
        fol.setText(Globales.getInstance().idFolio);
        doc.setText(Globales.getInstance().idDocUl);
        sq.contabilizaLosProdAgreCarr(getApplicationContext());

    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadEST(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }



    //OBTENCION DE LOS ESTABLECIMIENTOS EN MYSQL
    private void loadEST(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] sucursalSelect = new String[jsonArray.length()];
        //final Blob[] imagenesClas = new Blob[jsonArray.length()];
        final String[] imagenesClas = new String[jsonArray.length()];
        final String[] clasificacion = new String[jsonArray.length()];
        final Ingresarsql sq = new Ingresarsql();
        List<ClasificacionDatos> listproductos = new ArrayList<ClasificacionDatos>();
        System.out.println(" jsonArray.length()  " + jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            sucursalSelect[i] = obj.getString("clas_id");
            clasificacion[i] = obj.getString("clas_nombre");
            //  imagenesClas[i] = (Blob) obj.getJSONObject("clas_imagen");
            imagenesClas[i]=   obj.getString("clas_nombre");
            sq.registrarClasificacion(clasificacion[i],imagenesClas[i],getApplicationContext());

            System.out.println(" indez " + sucursalSelect[i]+clasificacion[i]+ imagenesClas);
            //  listproductos.add(new ClasificacionDatos(sucursalSelect[i], clasificacion[i] ));
        }
        //ArrayAdapter adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sucursalSelect);
        //recyclerViewClasificacionProductos.setAdapter(new RecyclerViewClasificacion((ArrayList<ClasificacionDatos>) listproductos));
//        cargarClasificaciones();

    }

    private void cargarClasificaciones() {

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
                            String identificadorClasificacion= listclientes.get(elemen).getIdentificador();
                            Intent intencion = new Intent(getApplication(), clasificacionSeleccionada.class);
                            intencion.putExtra(clasificacionSeleccionada.nombreclasificacion, nombreclasificacion);
                            intencion.putExtra(clasificacionSeleccionada.identificadorClasificacion, identificadorClasificacion);
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
    }

    public void onBackPressed() {
     /*   finish();
        Intent intencion2 = new Intent(getApplication(), disponibilidadmesas.class);
        startActivity(intencion2);*/
        if(Globales.getInstance().regresarOrdenes==1){
            finish();
            startActivity(new Intent(getApplicationContext(),ordenes.class));
            Globales.getInstance().regresarOrdenes=0;
        }else {
            Intent intencion2 = new Intent(getApplication(), disponibilidadmesas.class);
            startActivity(intencion2);
            finish();
        }
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if (id==R.id.opcion1){
            Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
        startActivity(intencion2);
    }
        if (id==R.id.opcion2) {
            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(Productos.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(Productos.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //sq.limpiarVariablesGlobales();
                            finish();
                            Intent intencion2 = new Intent(getApplication(), MainActivity.class);
                            startActivity(intencion2);
                            Toast.makeText(Productos.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
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

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
        //sq.consultarNombreUsuario(getApplicationContext());
        menu.getItem(0).setTitle(Globales.getInstance().nombreUsuario);
        return true;
    }


    private void buscarProducto() {
        sq.contabilizaLosProdAgreCarr(getApplicationContext());
        SQLiteDatabase db = conn.getReadableDatabase();
        List<ProductosDatos> listclientes = new ArrayList<ProductosDatos>();
        String query = busqueda.getText().toString();
        listclientes.clear();
        Globales.getInstance().listclientes.clear();
        Cursor cursor2 =db.rawQuery("SELECT prepro_id, prepro_precompra, prst_descripcion, prd_nombre,prd_imagen, prd_id FROM producto INNER JOIN prest_prod ON producto.prd_id = prest_prod.prd_fk\n" +
                "               INNER JOIN presentacion ON presentacion.prst_id = prest_prod.prst_fk    WHERE prd_nombre LIKE '%"+query+"%'" , null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                Bitmap bitmap = null;
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_id")));
                    byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                    String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_nombre")));
                    String precio= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                    ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(bais);
                    String presentacion="";
                    Globales.getInstance().listclientes.add(new ProductosDatos(id,nombre,bitmap, precio,presentacion));
                    listclientes.add(new ProductosDatos(id,nombre,bitmap, precio,presentacion));
                    index++;
                    cursor2.moveToNext();
                }


                if (index != 0) {
                    final RecyclerViewLL adaptador = new RecyclerViewLL((ArrayList<ProductosDatos>)  Globales.getInstance().listclientes);
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // Log.i("DemoRecView", "Pulsado el elemento " + recyclerViewClasificacionProductos.getChildAdapterPosition(v));
                            Log.i("DemoRecView", "Pulsado el elemento " + recyclerViewClasificacionProductos.getChildAdapterPosition(v));


                            int elemen=   recyclerViewClasificacionProductos.getChildAdapterPosition(v);
                            System.out.println(" Pulsado el elemento    " +  Globales.getInstance().listclientes.get(elemen).getNombreProducto());
                            String nombreclasificacion=  Globales.getInstance().listclientes.get(elemen).getNombreProducto();
                            String identificadorClasificacion=  Globales.getInstance().listclientes.get(elemen).getClasificacion();
                        }
                    });

                    recyclerViewClasificacionProductos.setAdapter(adaptador);
                    recyclerViewClasificacionProductos.setLayoutManager(
                            new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
                    recyclerViewClasificacionProductos.addItemDecoration(
                            new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
                }
                else { Toast.makeText(Productos.this,"No hay concidencias5",Toast.LENGTH_SHORT).show(); }
            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage()); }
    }




    public  void  recuperarFolio(Context context){
        ConexionSQLiteHelper conn;
        conn=new ConexionSQLiteHelper(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        sq.consultaestatus(context);
        int estatus= Globales.getInstance().idEstatusLau;
        //cambiar
        //String idu=  Globales.getInstance().id_usuario;
        String idu=  Globales.getInstance().usuario;


//        sq.consultaEmpresaEstableCaja(getApplicationContext(),idu);
        String est= Globales.getInstance().idEstablecimientoLau;

        Cursor cursor2 =db.rawQuery("SELECT doc_id,fol_folio from folio  inner JOIN documento ON documento.doc_folio= folio.fol_folio WHERE  esta_fk='"+estatus+"' and est_fk='"+est+"'" , null);
        String folio = "";
        String documento = "";
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {

                    folio= String.valueOf( cursor2.getString(cursor2.getColumnIndex("fol_folio")));
                    documento= String.valueOf( cursor2.getString(cursor2.getColumnIndex("doc_id")));
                    index++;
                    cursor2.moveToNext();
                    break;
                }
                if (index != 0) {
                    //String fo1=  Globales.getInstance().idFolio;
                    //if(folio.equals(fo1)){
                    Globales.getInstance().idFolio = folio;
                    Globales.getInstance().idDocUl = documento;
                    //si el folioo ya existe aunq este en proceso   q lo elimine q cambie

                }
                else
                {
                    String f1=sq.generarFolio(context);
                    Globales.getInstance().idFolio= f1;
                }
            }

        }catch(Exception e){
            Log.println(Log.ERROR,"Null167 ",e.getMessage());
        }


    }


}