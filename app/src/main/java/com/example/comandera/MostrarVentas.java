package com.example.comandera;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandera.modelo.ConexionSQLiteHelper;
import com.example.comandera.modelo.ProductoAdapter;
import com.example.comandera.modelo.Productoss;
import com.example.comandera.modelo.consultas;
import com.example.comandera.ui.login.InicioSesion;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MostrarVentas extends AppCompatActivity implements ProductoAdapter.MiListener {
    consultas consul= new consultas();
    RecyclerView recyclerView;
    ConexionSQLiteHelper conn;
    TextView OMesa;
    ProductoAdapter adaptaProdsLista;
    ArrayList<Productoss> productos;
    int[] ids= new int[100];
    int[] productsID= new int[100];
    String nomb, folioVenta;
    int indexList, cant=0, cant2=0;
    double precioUnitario=0.0, precioUnitario2=0.0;
    Double TotalVenta=0.0, totalCobrar=0.0, subTot=0.0, toTal=0.0, toTal2=0.0;
    Button btnMontoTotal, agregar, cancelar;
    int bandera=0;
    final Context context = this;
    private ProgressDialog progressDialog;
    Parcelable recyclerViewState;
    String nuevaVari="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas_tacos);

        conn=new ConexionSQLiteHelper(getApplicationContext());
        productos = new ArrayList<Productoss>();
        //
        // progressDialog = new ProgressDialog(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnMontoTotal = (Button) findViewById(R.id.btnCobrar); // boton de cobrar
        agregar = (Button) findViewById(R.id.btnenviarOrden); //boton de agregar los producto
        cancelar = (Button) findViewById(R.id.btnCancelar); // boton de cancelar lo de los productos

        OMesa= findViewById(R.id.OMesa);

        String nommesa=Globales.getInstance().ordenN;
        OMesa.setText(nommesa);

        adaptaProdsLista = new ProductoAdapter(MostrarVentas.this, productos);
        consultaSQL2();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                finish();

              //  String ord_folio=consul.generarFolio();

               // String s="Mesa";
              //  System.out.println(s.substring(0,4));
               // startActivity(new Intent(MostrarVentas.this, Productos.class));

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
             /*   AlertDialog.Builder builder = new AlertDialog.Builder(MostrarVentas.this);
                builder.setCancelable(false);
                builder.setMessage("¿Está seguro de cancelar la captura?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        SQLiteDatabase db = conn.getReadableDatabase();
                        Cursor cursorC = db.rawQuery("DELETE FROM orden_det WHERE orden_det.ord_fk ="+Globales.getInstance().idDetalle, null);
                        Cursor cursorC2 = db.rawQuery("DELETE FROM orden WHERE ord.ord_id="+Globales.getInstance().idDetalle, null);

                        cursorC.moveToFirst();
                        cursorC2.moveToFirst();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MenuAdministrador.class));
                        Toast.makeText(MostrarVentas.this, "Captura cancelado...", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();*/
            }
        });
/*
        //  Botón para imprensión la venta que se realizó
        btnMontoTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reimprimiendo taskimp;
                taskimp = new Reimprimiendo();
                taskimp.execute((Void) null);

            }
        });*/

    }



    //CONSULTA A BD PARA OBTENER LOS PRODUCTOS DEL X VENTA
    private void consultaSQL2() {
        productos.clear();
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor2 =db.rawQuery("SELECT ord_id, ord_folio,ordet_cant, prst_descripcion,ordet_precio, ord_total,ordet_observa, prd_nombre, prd_imagen, prepro_precompra, orden_det.prepro_fk\n" +
                "FROM estatus, orden, orden_det, producto, prest_prod\n" +
                "INNER JOIN presentacion ON presentacion.prst_id = prest_prod.prst_fk   \n" +
                "WHERE estatus.esta_estatus='por preparar' AND orden.esta_fk=estatus.esta_id  \n" +
                "AND orden.ord_id = orden_det.ord_fk \n" +
                "AND orden_det.prepro_fk = prest_prod.prepro_id   \n" +
                "AND prest_prod.prd_fk = producto.prd_id", null);

        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                Bitmap bitmap = null;
                while (!cursor2.isAfterLast()) {
/*
                    folioVenta = cursor2.getString(cursor2.getColumnIndex("doc_folio"));
                    Globales.getInstance().idVentaPrevia = folioVenta;
                    precioUnitario = Double.parseDouble(cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                    nomb= cursor2.getString(cursor2.getColumnIndex("prd_nombre"));
                    cant= cursor2.getInt(cursor2.getColumnIndex("docd_cantprod"));
                    subTot= cursor2.getDouble(cursor2.getColumnIndex("docd_precven"));
                    toTal= cursor2.getDouble(cursor2.getColumnIndex("doc_total"));

                    Globales.getInstance().idDetalle = cursor2.getInt(cursor2.getColumnIndex("doc_id"));
                    productsID[index] = cursor2.getInt(cursor2.getColumnIndex("prepro_fk"));

                    //Se obtiene la imagen del producto seleccionado
                    byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                    ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(bais);
                    //var para presentacion
                    String presentacion = cursor2.getString(cursor2.getColumnIndex("prst_descripcion"));*/

                    folioVenta = cursor2.getString(cursor2.getColumnIndex("ord_folio"));
                    Globales.getInstance().idVentaPrevia = folioVenta;
                    precioUnitario = Double.parseDouble(cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                    nomb= cursor2.getString(cursor2.getColumnIndex("prd_nombre"));
                    cant= cursor2.getInt(cursor2.getColumnIndex("ordet_cant"));///////////////////////////////////
                    //subTot= cursor2.getDouble(cursor2.getColumnIndex("docd_precven"));
                    toTal= cursor2.getDouble(cursor2.getColumnIndex("ord_total"));

                    Globales.getInstance().idDetalle = cursor2.getInt(cursor2.getColumnIndex("ord_id"));
                    productsID[index] = cursor2.getInt(cursor2.getColumnIndex("prepro_fk"));

                    //Se obtiene la imagen del producto seleccionado
                    byte[] blob = cursor2.getBlob(cursor2.getColumnIndex("prd_imagen"));
                    ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(bais);
                    //var para presentacion
                    String presentacion = cursor2.getString(cursor2.getColumnIndex("prst_descripcion"));
                    String comentario = cursor2.getString(cursor2.getColumnIndex("ordet_observa"));
//////////DUDA
                    //Asignación de la información

                    Productoss producto = new Productoss(bitmap,nomb,cant,precioUnitario,subTot,presentacion, comentario);
                    productos.add(producto);

                    btnMontoTotal.setText("Registrar venta \t\n\t $ " +String.format("%.2f", toTal));
                    Globales.getInstance().TotVenta =  toTal;

                    index++;
                    cursor2.moveToNext();
                }

                if (index != 0) {
                    recyclerView.setAdapter(adaptaProdsLista);
                }else {
                    btnMontoTotal.setEnabled(false);
                    btnMontoTotal.setBackgroundColor(0xFFe6e6e6);
                }

                bandera++;
            }

        }catch(Exception e){
        }
    }


    //Si se retrocede.. este método se encargará de cancelar la venta si se presiona el botón si
    @Override
    public void onBackPressed() {
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("¿Está seguro de cancelar la captura?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application

                SQLiteDatabase db = conn.getReadableDatabase();
                Cursor cursorC=db.rawQuery("DELETE FROM orden_det WHERE orden_det.ord_fk ="+Globales.getInstance().idDetalle, null);
                Cursor cursorC2 =db.rawQuery("DELETE FROM orden WHERE orden.ord_id="+Globales.getInstance().idDetalle, null);
                cursorC.moveToFirst();
                cursorC2.moveToFirst();

                finish();
                startActivity(new Intent(getApplicationContext(), MenuAdministrador.class));
                Toast.makeText(MostrarVentas.this, "Captura cancelada...", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();*/
    }

    @Override
    public void onItemClick(Productoss productos, EditText cantidadescribir) {
        productos.cantidad=0;
        cantidadescribir.requestFocus();
        cantidadescribir.setCursorVisible(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(MostrarVentas.this.INPUT_METHOD_SERVICE);
        imm.showSoftInput(cantidadescribir, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onItemClickMenos(Productoss productos) {
        productos.cantidad=0;
    }

    @Override
    public void cambiacant(String cadena, int posicion) {

        if (Globales.getInstance().operacion == 0){
            if(cadena.trim().equals("")){
                cadena = "0";

            }
            Globales.getInstance().cantiOperacion = Integer.valueOf(cadena);
            consultaSQL3("cambia");
        }
        Globales.getInstance().operacion = 0;
    }


    @Override
    public void elimi( int posicion) {
        if (productos.size() == 1){
            //if (posicion == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(MostrarVentas.this);
            builder.setCancelable(false);
            builder.setMessage("¿Está seguro de cancelar la captura?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    SQLiteDatabase db = conn.getReadableDatabase();
                    Cursor cursorC = db.rawQuery("DELETE FROM orden_det WHERE orden_det.ordet_id ="+Globales.getInstance().idDetalle, null);
                    Cursor cursorC2 = db.rawQuery("DELETE FROM orden WHERE orden.ord_id="+Globales.getInstance().idDetalle, null);

                    cursorC.moveToFirst();
                    cursorC2.moveToFirst();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MenuAdministrador.class));
                    Toast.makeText(MostrarVentas.this, "Captura cancelado...", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else{
            Globales.getInstance().idProducto = productsID[posicion];
            consultaSQL3("eliminar");
            Globales.getInstance().operacion = 1;
        }
    }

    @Override
    public void aumentar(int posicion) {
        Globales.getInstance().idProducto = productsID[posicion];
        consultaSQL3("aumentar");
        Globales.getInstance().operacion = 1;
    }

    @Override
    public void disminuir( int posicion) {

        Globales.getInstance().idProducto = productsID[posicion];
        consultaSQL3("disminuir");
        Globales.getInstance().operacion = 1;

    }
   /* @Override
    public void escribe( int posicion) {

        Globales.getInstance().idProducto = productsID[posicion];
        consultaSQL3("escribe");
        Globales.getInstance().operacion = 1;

    }*/

    private void consultaSQL3(String tipo) {
        int cantotal=0;
        SQLiteDatabase db = conn.getReadableDatabase();
        SQLiteDatabase db2 = conn.getWritableDatabase();

        Cursor cursor2 =db.rawQuery("SELECT ordet_cant, prepro_precompra FROM orden \n" +
                "INNER JOIN orden_det ON orden.ord_id = orden_det.ord_fk \n" +
                "INNER JOIN producto ON orden_det.prepro_fk =  prest_prod.prepro_id \n" +
                "INNER JOIN prest_prod ON prest_prod.prd_fk = producto.prd_id  WHERE orden_det.ord_fk ="+Globales.getInstance().idDetalle+" AND orden_det.prepro_fk="+Globales.getInstance().idProducto, null);

        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;

                while (!cursor2.isAfterLast()) {
                    precioUnitario2 = Double.parseDouble(cursor2.getString(cursor2.getColumnIndex("prepro_precompra")));
                    cant2= cursor2.getInt(cursor2.getColumnIndex("ordet_cant"));
                    //cant2= cursor2.getInt(cursor2.getColumnIndex("docd_cantprod"));

                    index++;
                    cursor2.moveToNext();
                }

                if (tipo == "aumentar"){
                    cantotal = cant2+1;

                }else if (tipo == "disminuir"){
                    cantotal = cant2-1;

                }/*else if(tipo == "cambia"){
                    cantotal = Globales.getInstance().cantiOperacion; DELETE FROM orden_det WHERE orden_det.ord_fk ='3' AND orden_det.prepro_fk='1'
                    //Cursor cursorE=db.rawQuery("DELETE FROM documento_det WHERE documento_det.doc_fk ="+Globales.getInstance().idDetalle+" AND documento_det.prepro_fk="+Globales.getInstance().idProducto, null);
                }*/else if (tipo == "eliminar"){
                    Cursor cursorE=db.rawQuery("DELETE FROM orden_det WHERE orden_det.ord_fk ="+Globales.getInstance().idDetalle+" AND orden_det.prepro_fk="+Globales.getInstance().idProducto, null);
                    cursorE.moveToNext();
                    Toast.makeText(MostrarVentas.this, "Producto cancelado...", Toast.LENGTH_SHORT).show();

                }
                if (cantotal <= 0){
                    cantotal = 0;
                }
                Globales.getInstance().canTotal = cantotal;

                toTal2 = precioUnitario2*cantotal;

                Globales.getInstance().subTotal=toTal2;

                db2.execSQL("UPDATE orden_det SET ordet_cant = "+cantotal+", prepro_precompra = "+toTal2+" WHERE ord_fk ="+Globales.getInstance().idDetalle+" AND orden_det.prepro_fk="+Globales.getInstance().idProducto);
               //db2.execSQL("UPDATE documento_det SET docd_cantprod = "+cantotal+", docd_precven = "+toTal2+" WHERE doc_fk ="+Globales.getInstance().idDetalle+" AND documento_det.prepro_fk="+Globales.getInstance().idProducto);

              //  Cursor cursor3 = db.rawQuery("SELECT SUM(docd_precven) as suma FROM documento_det WHERE documento_det.doc_fk ="+Globales.getInstance().idDetalle, null);
                Cursor cursor3 = db.rawQuery("SELECT SUM(prepro_precompra) as suma FROM orden_det WHERE orden_det.ord_fk ="+Globales.getInstance().idDetalle, null);
                cursor3.moveToFirst();

                TotalVenta = Double.parseDouble(cursor3.getString(cursor3.getColumnIndex("suma")));
                String v =  String.format("%.2f", TotalVenta);
                totalCobrar = Double.valueOf(v);
                btnMontoTotal.setText("Registrar venta \t\n\t $ " + Double.toString(totalCobrar));
              //  db2.execSQL("UPDATE documento SET doc_subtotal = "+TotalVenta+", doc_total  = "+TotalVenta+" WHERE doc_id ="+Globales.getInstance().idDetalle);
                db2.execSQL("UPDATE orden SET prepro_precompra = "+TotalVenta+", ord_total  = "+TotalVenta+" WHERE ord_id ="+Globales.getInstance().idDetalle);
                // Restore state

                //recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                consultaSQL2();
                //recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);


            }

        }catch(Exception e){
        }
    }

    public class CustomScrollListener extends RecyclerView.OnScrollListener {
        public CustomScrollListener() {
        }
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            switch (newState) {
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    View view = MostrarVentas.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(MostrarVentas.this.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    break;
            }
        }
    }

    //METODO PARA LA IMPRESION
    /*
    public class
    Reimprimiendo extends AsyncTask<Void, Void, Boolean> {

        ProgressDialog pd;
        private Context mContext;
        PrinterConnect impresora;

        Reimprimiendo() {
            mContext = MostrarVentas.this;
            impresora = new PrinterConnect(mContext);
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(mContext);
            pd.setTitle("Imprimiendo");

            pd.setMessage("Aguarde mientras se imprime ");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setIndeterminate(false);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.show();
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            String cadenota = "";
            String cadenotaDatos = "";

            try {
                for (Productoss temp : productos) {
                    cadenotaDatos = cadenotaDatos + "\n" +temp.nombre + "\n $ " + temp.precio +"  X " + temp.cantidad +" = $ " + temp.subtotal;
                }

                impresora.conecta();

                cadenota =  Globales.getInstance().establecimientoo2+" \n" + "Folio de la venta: "+ Globales.getInstance().idVentaPrevia +" \n" +
                        "Fecha:" + fecha() +"   Hora:"+hora()+"\n-------------------------------\n"+cadenotaDatos+"\n \n -------------------------------\n"+"Subtotal: $"+Globales.getInstance().TotVenta+" \n" +"IVA 16%: $0.00"+" \n"+ "Total: $"+Globales.getInstance().TotVenta+" \n"+"* Cuenta por pagar *";

                impresora.SendDataString(cadenota);
                impresora.SendDataString("\n----------------------------------------------------------------\n\n");
                cadenota = "";
                impresora.detieneImpresion();
                //finish();
                //startActivity(new Intent(MostrarVentas.this, MenuGeneral.class));
            } catch (android.database.SQLException e) {

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            LayoutInflater ticket  = LayoutInflater.from(context);
            View prompstsView2 = ticket.inflate(R.layout.dialog_imprimir, null);
            final AlertDialog.Builder builder2 = new AlertDialog.Builder(MostrarVentas.this);
            builder2.setView(prompstsView2);
            builder2.setCancelable(false);

            final TextView textoo = (TextView) prompstsView2.findViewById(R.id.textView5);
            textoo.setText("¿Se imprimió correctamente su ticket ?");

            builder2.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    consultaEstatus();
                    finish();
                    startActivity(new Intent(MostrarVentas.this, MenuGeneral.class));
                }
            });

            builder2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // SI EL TICKET NO SE IMPRIMIÓ VUELVE A LLAMAR EL MÉTODO PARA VOLVER A IMPRIMIR
                    Reimprimiendo taskimp;
                    taskimp = new Reimprimiendo();
                    taskimp.execute((Void) null);
                    pd.dismiss();
                }
            });
            AlertDialog alert = builder2.create();
            alert.show();
        }

    }

*/
    //METODO PARA OBTENER FECHA ACTUAL
    private String fecha() {
        final SimpleDateFormat fe = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        return fe.format(calendar.getTime());
    }

    //METODO PARA OBTENER HORA ACTUAL
    private String hora() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String datetime = dateFormat.format(c.getTime());
        return datetime;
    }


    //CONSULTA A BD PARA OBTENER ID DEL ESTATUS POR PAGAR
    private void consultaEstatus() {
        SQLiteDatabase db = conn.getReadableDatabase();
        SQLiteDatabase dbUpdate= conn.getWritableDatabase();
        Cursor cursorEstatus =db.rawQuery("SELECT * FROM estatus WHERE estatus.esta_estatus='Por pagar'",null);

        try {
            if (cursorEstatus != null) {
                cursorEstatus.moveToFirst();

                Globales.getInstance().idStatus = cursorEstatus.getInt(cursorEstatus.getColumnIndex("esta_id"));
                String sql="UPDATE documento SET esta_fk ="+Globales.getInstance().idStatus+" WHERE doc_id ="+Globales.getInstance().idDetalle;
                dbUpdate.execSQL("UPDATE documento SET esta_fk ="+Globales.getInstance().idStatus+" WHERE doc_id ="+Globales.getInstance().idDetalle);

            }

        }catch(Exception e){
        }
    }

    //IMPLEMENTACION DEL MENÚ
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if (id==R.id.opcion1){
            Intent intencion2 = new Intent(getApplication(), Productos.class);
            startActivity(intencion2);
        }
        if (id== R.id.opcion2){

            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(MostrarVentas.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(MostrarVentas.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                           // Toast.makeText(MostrarVentas.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
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

        /*if (id== R.id.opcion1) {

            finish();
            Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
            startActivity(intencion2);
        }*/

        return super.onOptionsItemSelected(item);
    }

}