package com.example.comandera;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandera.modelo.ClasificacionDatos;
import com.example.comandera.modelo.ConexionSQLiteHelper;
import com.example.comandera.modelo.GridAdapterProductos;
import com.example.comandera.modelo.Ingresarsql;
import com.example.comandera.modelo.ProductosDatos;
import com.example.comandera.modelo.RecyclerViewProductos;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class clasificacionSeleccionada extends AppCompatActivity {

    public static final String nombreclasificacion="nombreclasificacion";
    public static final String identificadorClasificacion="identificadorClasificacion";
    private GridView gridView;
    GridAdapterProductos adapter;
    EditText busqueda;
    ImageButton  botonTeclado,botonTecladoOculto;
    Spinner spinnerlistaCaracteristica,valor;
    TextView txtCarac,txtIden,cuentaProductos2,ocuMesa;
    ImageButton botonBuscar;
    ImageView imageView4;
    View separador;
    ConexionSQLiteHelper conn;
    TextView fol,doc;
    int contador=0, contador2=0;
    public static TextView cuentaProductos;
    Ingresarsql sq = new Ingresarsql();
    RecyclerView recyclerViewClasificacionProductos;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion_seleccionada);
        busqueda= findViewById(R.id.busqueda3);
        botonBuscar= findViewById(R.id.btnBuscar3cs);
        cuentaProductos= findViewById(R.id.cuentaProductos);
        imageView4= findViewById(R.id.imageView4);
        botonTeclado= findViewById(R.id.btnTeclado);
        botonTecladoOculto= findViewById(R.id.btnTecladoOculto);
        gridView= findViewById(R.id.vistaUsuario2);
        spinnerlistaCaracteristica= (Spinner)findViewById(R.id.caracteristica);
        valor= (Spinner)findViewById(R.id.valor);
        txtCarac= findViewById(R.id.txtCarac);
        txtIden= findViewById(R.id.txtIden);

        cuentaProductos2= findViewById(R.id.cuentaProductos2);
        separador= findViewById(R.id.separador);


        ocuMesa= findViewById(R.id.ocuMesa);

        String nommesa=Globales.getInstance().ordenN;
        ocuMesa.setText(nommesa);

      //   recyclerViewClasificacionProductos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
 //       recyclerViewClasificacionProductos.setLayoutManager(layoutManager);
        conn=new ConexionSQLiteHelper(getApplicationContext());

        final String nombreclasificacion = getIntent().getStringExtra("nombreclasificacion");
        txtCarac.setText(nombreclasificacion);
        final String identificadorClasificacion = getIntent().getStringExtra("identificadorClasificacion");
        txtIden.setText(identificadorClasificacion);




        busqueda.setVisibility(View.VISIBLE);
        busqueda.setFocusable(false);

        txtIden.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                finish();
                startActivity(new Intent(clasificacionSeleccionada.this, Productos.class));

            }
        });

        busqueda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                busqueda.setFocusable(false);
                busqueda.setEnabled(false);
                Intent intencion2 = new Intent(getApplication(), Productos.class);
                finish();
                startActivity(intencion2);
            }
        });


        botonTeclado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion2 = new Intent(getApplication(), Productos.class);
                finish();
                startActivity(intencion2);
            }
        });

        botonTecladoOculto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        cargarCaracteristicas();
        cargarValor();

    /*    spinnerlistaCaracteristica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cargarCaracteristicas();
                System.out.print(" 11111111111 ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                  Toast.makeText(clasificacionSeleccionada.this, "No esta funcionando ",Toast.LENGTH_LONG).show();
                System.out.print(" 3333 ");
            }


        });*/

      /*  busqueda.addTextChangedListener(new TextWatcher() {
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
                contabilizaLosProdAgreCarr();
            }
        });*/

      //  cargarClasificaciones(clasificacion);

         //mostrarProductos();
        cargarProductosAlI();
       // cargarProductosTodos();

        spinnerlistaCaracteristica.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn, View v, int posicion, long id) {
                        Toast.makeText(spn.getContext(), "Caracteristica: " + spn.getItemAtPosition(posicion).toString(), Toast.LENGTH_LONG).show();

                        if(contador!=0) {
                             cargarProductosConCaracteristica();
                        }
                        if (contador==0){
                            contador++;
                        }
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });


      //  cargarProductosConCaracteristica();
        spinnerlistaCaracteristica.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn, View v, int posicion, long id) {
                        Toast.makeText(spn.getContext(), "Caracteristica: " + spn.getItemAtPosition(posicion).toString(), Toast.LENGTH_LONG).show();

                        if(contador!=0) {
                            cargarProductosConCaracteristica();
                        }
                        if (contador==0){
                            contador++;
                        }
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion2 = new Intent(getApplication(), Productos.class);
                finish();
                startActivity(intencion2);
                contabilizaLosProdAgreCarr();
                buscarProducto();
                String query = busqueda.getText().toString();
                if(query.equals("")){
                  //  cargarClasificaciones();
                    //cargarProductosTodos();
                }
                else {
                    //buscarProducto();
                }
                buscarProducto();
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
               startActivity(new Intent(clasificacionSeleccionada.this, MostrarVentas.class));
            }
        });

        separador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(clasificacionSeleccionada.this, MostrarVentas.class));
            }
        });


        cuentaProductos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(clasificacionSeleccionada.this, MostrarVentas.class));
            }
        });


        cuentaProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(clasificacionSeleccionada.this, MostrarVentas.class));
            }
        });


        contabilizaLosProdAgreCarr();

      //  fol= findViewById(R.id.fol);
      //  doc= findViewById(R.id.fol2);
//        fol.setText(Globales.getInstance().idFolio);
       // doc.setText(Globales.getInstance().idDocUl);


    }

    public  void  cargarCaracteristicas(){
        contabilizaLosProdAgreCarr();
        String query = txtIden.getText().toString();
        SQLiteDatabase db = conn.getReadableDatabase();
        List<String> listaCaracteristica = new ArrayList<String>();
        listaCaracteristica.clear();
        Cursor cursor2 =db.rawQuery("select crts_id, crts_nombre, clas_fk from caracteristica  WHERE clas_fk='"+query+"'", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    String id= String.valueOf(cursor2.getColumnIndex("crts_id"));
                    String carac= String.valueOf( cursor2.getString(cursor2.getColumnIndex("crts_nombre")));
                    String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("clas_fk")));
                    listaCaracteristica.add(carac);
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaCaracteristica);
                    spinnerlistaCaracteristica.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(clasificacionSeleccionada.this,"No hay concidencias",Toast.LENGTH_SHORT).show();
                    listaCaracteristica.clear();
                    spinnerlistaCaracteristica.setAdapter(null);
                }


            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }

    public  void  cargarValor(){
        contabilizaLosProdAgreCarr();
        String query = txtIden.getText().toString();
        SQLiteDatabase db = conn.getReadableDatabase();
        List<String> listaValor = new ArrayList<String>();
        listaValor.clear();
        Cursor cursor2 =db.rawQuery("SELECT crtd_id, crts_fk, crts_id,ctrd_descripcion,clas_fk FROM caract_det INNER JOIN caracteristica ON caract_det.crts_fk= caracteristica.crts_id INNER JOIN clasificacion ON clasificacion.clas_id= caracteristica.clas_fk WHERE clas_id='"+query+"'", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    String id= String.valueOf(cursor2.getColumnIndex("crtd_id"));
                    String carac= String.valueOf( cursor2.getString(cursor2.getColumnIndex("ctrd_descripcion")));
                    String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("crtd_id")));
                    listaValor.add(carac);
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaValor);
                    valor.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(clasificacionSeleccionada.this,"No hay concidencias",Toast.LENGTH_SHORT).show();
                    listaValor.clear();
                    valor.setAdapter(null);
                }


            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }

    public void onBackPressed() {
        Intent intencion2 = new Intent(getApplication(), Productos.class);
        startActivity(intencion2);
        finish();
    }
    public  boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if (id==R.id.opcion2){

            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(clasificacionSeleccionada.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(clasificacionSeleccionada.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            finish();
                            Intent intencion2 = new Intent(getApplication(), MainActivity.class);
                            startActivity(intencion2);
                            Toast.makeText(clasificacionSeleccionada.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
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

        if (id==R.id.opcion1) {

            finish();
            Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
            startActivity(intencion2);
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
        Ingresarsql sq = new Ingresarsql();
       // sq.consultarNombreUsuario(getApplicationContext());
        menu.getItem(0).setTitle(Globales.getInstance().nombreUsuario);
        return true;
    }

    private void cargarClasificaciones(String clasificacion) {
        SQLiteDatabase db = conn.getReadableDatabase();
        List<ProductosDatos> listclientes = new ArrayList<ProductosDatos>();
        listclientes.clear();
            Cursor cursor2 =db.rawQuery("select * from producto WHERE clas_fk="+clasificacion+"", null);

        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                Bitmap bitmap = null;
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                    String clas= String.valueOf( cursor2.getString(cursor2.getColumnIndex("clas_fk")));
                    String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_nombre")));
                    String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_id")));
                    ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(bais);
                    String presentacion="";
                    listclientes.add(new ProductosDatos(id, nombre, bitmap, clas, presentacion));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    recyclerViewClasificacionProductos.setAdapter(new RecyclerViewProductos((ArrayList<ProductosDatos>) listclientes));
                    System.out.println(" list 3    " + listclientes.toString());
                }  else
                {
                    Toast.makeText(clasificacionSeleccionada.this,"No hay concidencias",Toast.LENGTH_SHORT).show();
                    listclientes.clear();
                    recyclerViewClasificacionProductos.setAdapter(null);
                } }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }

    private void cargarProductosTodos() {
        contabilizaLosProdAgreCarr();
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        final ArrayList<ProductosDatos> listProductos = new ArrayList<ProductosDatos>();
        listProductos.clear();
        Cursor cursor2 =db.rawQuery("SELECT prd_id, prd_imagen, prd_nombre, prepro_precompra, crts_id,crts_nombre,  producto.clas_fk,prd_fk  FROM producto INNER JOIN clasificacion ON producto.clas_fk= clasificacion.clas_id " +
                "INNER JOIN caracteristica ON caracteristica.clas_fk= clasificacion.clas_id INNER JOIN prest_prod ON prest_prod.prd_fk= producto.prd_id", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                Bitmap bitmap = null;
                while (!cursor2.isAfterLast()) {

                    byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                    String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_nombre")));
                    String precio= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                    String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_id")));
                    ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(bais);
                    String presentacion="";
                    listProductos.add(new ProductosDatos(id,nombre,bitmap, precio, presentacion));
                    arrayList.add(nombre);
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    adapter = new GridAdapterProductos(this, listProductos);
                    gridView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(clasificacionSeleccionada.this,"No hay concidencias",Toast.LENGTH_SHORT).show();
                    listProductos.clear();
                    gridView.setAdapter(null);
                }

            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
            System.out.println("  ERROR  "+e.getMessage());
            Toast.makeText(clasificacionSeleccionada.this,"NERROR"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

   /*
    private void cargarProductosAlI() {
        contabilizaLosProdAgreCarr();
        String clasificacion = txtIden.getText().toString();
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        final ArrayList<ProductosDatos> listProductos = new ArrayList<ProductosDatos>();
        listProductos.clear();
        Cursor cursor2 =db.rawQuery("SELECT prd_id, prd_imagen, prd_nombre, prepro_precompra, crts_id,crts_nombre,  producto.clas_fk,prd_fk  FROM producto INNER JOIN clasificacion ON producto.clas_fk= clasificacion.clas_id " +
                "INNER JOIN caracteristica ON caracteristica.clas_fk= clasificacion.clas_id INNER JOIN prest_prod ON prest_prod.prd_fk= producto.prd_id WHERE  producto.clas_fk='"+clasificacion+"'", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                Bitmap bitmap = null;
                while (!cursor2.isAfterLast()) {

                    byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                    String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_nombre")));
                    String precio= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                    String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_id")));
                    ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(bais);
                    listProductos.add(new ProductosDatos(id,nombre,bitmap, precio));
                    arrayList.add(nombre);
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    adapter = new GridAdapterProductos(this, listProductos);
                    gridView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(clasificacionSeleccionada.this,"No hay concidencias",Toast.LENGTH_SHORT).show();
                    listProductos.clear();
                    gridView.setAdapter(null);
                }
            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }
*/
   private void cargarProductosAlI() {
       contabilizaLosProdAgreCarr();
       String clasificacion = txtIden.getText().toString();
       SQLiteDatabase db = conn.getReadableDatabase();
       ArrayList<String> arrayList = new ArrayList<>();
       final ArrayList<ProductosDatos> listProductos = new ArrayList<ProductosDatos>();
       listProductos.clear();
       // Cursor cursor2 =db.rawQuery("SELECT prd_id, prd_imagen, prd_nombre, prepro_precompra, crts_id,crts_nombre,  producto.clas_fk,prd_fk  FROM producto INNER JOIN clasificacion ON producto.clas_fk= clasificacion.clas_id " +
       //        "INNER JOIN caracteristica ON caracteristica.clas_fk= clasificacion.clas_id INNER JOIN prest_prod ON prest_prod.prd_fk= producto.prd_id WHERE  producto.clas_fk='"+clasificacion+"'", null);
       Cursor cursor2 =db.rawQuery("SELECT prepro_id, prd_imagen, prd_nombre, prepro_precompra, prst_fk, prst_descripcion \n" +
               "FROM prest_prod \n" +
               "INNER JOIN producto On producto.prd_id= prest_prod.prd_fk \n" +
               "INNER JOIN presentacion ON  presentacion.prst_id= prest_prod.prst_fk \n" +
               "WHERE  producto.clas_fk='"+clasificacion+"'", null);

       try {
           if (cursor2 != null) {
               cursor2.moveToFirst();
               int index = 0;
               Bitmap bitmap = null;
               while (!cursor2.isAfterLast()) {

                   /* byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                    String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_nombre")));
                    String precio= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                    String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_id")));*/

                   byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                   String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_nombre")));
                   String precio= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                   String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prepro_id")));
                   String presentacion= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prst_descripcion")));


//String presentacion="hOLA HOLA";
                   ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                   bitmap = BitmapFactory.decodeStream(bais);

                   listProductos.add(new ProductosDatos(id,nombre,bitmap, precio,presentacion));
                   arrayList.add(nombre);
                   index++;
                   cursor2.moveToNext();
               }
               if (index != 0) {
                   adapter = new GridAdapterProductos(getApplicationContext(), listProductos);
                   gridView.setAdapter(adapter);
                   Toast.makeText(clasificacionSeleccionada.this,"No hay coincidencias",Toast.LENGTH_SHORT).show();
               }
               else
               {
                   Toast.makeText(clasificacionSeleccionada.this,"No hay concidencias",Toast.LENGTH_SHORT).show();
                   listProductos.clear();
                   gridView.setAdapter(null);
               }
           }
       }catch(Exception e){
           Log.println(Log.ERROR,"",e.getMessage());
           System.out.println("  ERROR  "+e.getMessage());
           Toast.makeText(clasificacionSeleccionada.this,"NERROR"+e.getMessage(),Toast.LENGTH_SHORT).show();
       }
   }
    private void cargarProductosConCaracteristica() {
        contabilizaLosProdAgreCarr();
        String clasificacion = txtIden.getText().toString();
        String  spinnerlistaCaracteristica1;
        spinnerlistaCaracteristica1 = spinnerlistaCaracteristica.getSelectedItem().toString();
        final ArrayList<ProductosDatos> listProductos = new ArrayList<ProductosDatos>();
        listProductos.clear();
        SQLiteDatabase db = conn.getReadableDatabase();
        final List<ClasificacionDatos> listclientes = new ArrayList<ClasificacionDatos>();
        listclientes.clear();

        Cursor cursor2 =db.rawQuery("SELECT prd_id, prd_imagen, prd_nombre, prepro_precompra, crts_id,crts_nombre,  producto.clas_fk,prd_fk  FROM producto INNER JOIN clasificacion ON producto.clas_fk= clasificacion.clas_id " +
                "INNER JOIN caracteristica ON caracteristica.clas_fk= clasificacion.clas_id INNER JOIN prest_prod ON prest_prod.prd_fk= producto.prd_id WHERE  crts_nombre= '"+spinnerlistaCaracteristica1 +"' and producto.clas_fk='"+clasificacion+"' ", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                Bitmap bitmap = null;
                while (!cursor2.isAfterLast()) {

                    byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                    String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_nombre")));
                    String precio= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                    String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_id")));


                    System.out.println(" precio    " + precio);

                    ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(bais);
                    String presentacion="";
                    listProductos.add(new  ProductosDatos(id,nombre,bitmap, precio, presentacion));
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    adapter = new GridAdapterProductos(this, listProductos);
                    gridView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(clasificacionSeleccionada.this,"No hay concidencias",Toast.LENGTH_SHORT).show();
                    listProductos.clear();
                    gridView.setAdapter(null);
                }



            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }

    private void cargarProductosConCaracteristicaYValor() {
        contabilizaLosProdAgreCarr();
        String clasificacion = txtIden.getText().toString();
        String  spinnerlistaCaracteristica1,varValor;
        spinnerlistaCaracteristica1 = spinnerlistaCaracteristica.getSelectedItem().toString();
        varValor = valor.getSelectedItem().toString();
        final ArrayList<ProductosDatos> listProductos = new ArrayList<ProductosDatos>();
        listProductos.clear();
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor2 =db.rawQuery("SELECT prd_id, prd_imagen, prd_nombre, prepro_precompra, crts_id,crts_nombre,  producto.clas_fk,prd_fk,clas_nombre  FROM producto INNER JOIN clasificacion ON producto.clas_fk= clasificacion.clas_id " +
                "INNER JOIN caracteristica ON caracteristica.clas_fk= clasificacion.clas_id INNER JOIN prest_prod ON prest_prod.prd_fk= producto.prd_id WHERE  crts_nombre= '"+spinnerlistaCaracteristica1 +"' and producto.clas_fk='"+clasificacion+"' and prd_nombre  LIKE '%"+varValor+"%'", null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                Bitmap bitmap = null;
                while (!cursor2.isAfterLast()) {

                    byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                    String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_nombre")));
                    String precio= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                    String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_id")));
                    ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(bais);
                    String presentacion="";
                    listProductos.add(new  ProductosDatos(id,nombre,bitmap, precio,presentacion));
                    // arrayList.add(nombre);

                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    //adapter = new GridAdapterProductos(this, listProductos);
                    //gridView.setAdapter(adapter);

                    final GridAdapterProductos adaptador = new GridAdapterProductos(this, listProductos);
                    gridView.setAdapter(adaptador);


                }
                else
                {
                    Toast.makeText(clasificacionSeleccionada.this,"No hay concidencias",Toast.LENGTH_SHORT).show();
                    listProductos.clear();
                    gridView.setAdapter(null);
                }

            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }

    private void buscarProducto() {
        contabilizaLosProdAgreCarr();
        SQLiteDatabase db = conn.getReadableDatabase();
        //List<ProductosDatos> listclientes = new ArrayList<ProductosDatos>();

        final ArrayList<ProductosDatos> listProductos = new ArrayList<ProductosDatos>();
        // final List<ClasificacionDatos> listclientes = new ArrayList<ClasificacionDatos>();
        //   final ArrayList<ProductosDatos> listProductos = new ArrayList<ProductosDatos>();
        String query = busqueda.getText().toString();
        System.out.println(" query nombre"+query);
        Cursor cursor2 =db.rawQuery("SELECT prepro_id, prepro_precompra, prst_descripcion, prd_nombre,prd_imagen, prd_id FROM producto INNER JOIN prest_prod ON producto.prd_id = prest_prod.prd_fk\n" +
                "               INNER JOIN presentacion ON presentacion.prst_id = prest_prod.prst_fk    WHERE prd_nombre LIKE '%"+query+"%'" , null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                Bitmap bitmap = null;
                int index = 0;
                while (!cursor2.isAfterLast()) {

                    byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                    String nombre= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_nombre")));
                    String precio= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                    String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("prd_id")));


                    System.out.println(" precio    " + precio);

                    ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(bais);
                    String presentacion="";
                    listProductos.add(new  ProductosDatos(id,nombre,bitmap, precio,presentacion));
                    index++;
                    cursor2.moveToNext();
                }

                if (index != 0) {
                    adapter = new GridAdapterProductos(this, listProductos);
                    gridView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(clasificacionSeleccionada.this,"No hay concidencias",Toast.LENGTH_SHORT).show();
                    listProductos.clear();
                    gridView.setAdapter(null);
                }
            }

        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }


    }


    private void contabilizaLosProdAgreCarr() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Globales.getInstance().listclientes.clear();
        //sq.consultaestatus(getApplicationContext());
        //CAMBIAR
       // String idu=  Globales.getInstance().id_usuario;
        String idu=  Globales.getInstance().usuario;
       // sq.consultaEmpresaEstableCaja(getApplicationContext(),idu);
        String est= Globales.getInstance().idEstablecimientoLau;
       // String doc=  Globales.getInstance().idDocUl;
        //Cursor cursor2 =db.rawQuery("select sum(docd_cantprod) as cantidadProductos FROM documento_det" , null);
        ///Cursor cursor2 =db.rawQuery("select sum(docd_cantprod) as cantidadProductos FROM documento_det  INNER JOIN documento ON documento_det.doc_fk=documento.doc_id WHERE  doc_fk ="+doc+" and  esta_fk='4'", null);
      // lau  Cursor cursor2 =db.rawQuery("select sum(docd_cantprod) as cantidadProductos FROM documento_det  INNER JOIN documento ON documento_det.doc_fk=documento.doc_id WHERE    esta_fk='"+Globales.getInstance().idEstatusLau+"'  and est_fk='"+est+"'", null);
       /*LAU MODIFICADO por marilu*/
//      Cursor cursor2 =db.rawQuery("select sum(docd_cantprod) as cantidadProductos FROM documento_det  INNER JOIN documento ON documento_det.doc_fk=documento.doc_id WHERE    esta_fk='"+Globales.getInstance().idEstatusLau+"'  and est_fk='"+est+"'", null);
        Cursor cursor2 =db.rawQuery("select sum(ordet_cant) as cantidadProductos FROM orden_det  INNER JOIN orden ON orden_det.ord_fk=orden.ord_id  INNER JOIN mesa ON mesa.mesa_id= orden.mesa_fk WHERE    orden_det.esta_fk='1'  and mesa.est_fk='1'", null);

        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("cantidadProductos")));
                    if(id.equals("null")){ cuentaProductos.setText("0"); }
                    else { cuentaProductos.setText(id); }
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    System.out.println("CLPAC " );
                }
            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }
}
