package com.example.comandera;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.comandera.modelo.ConexionSQLiteHelper;
import com.example.comandera.modelo.RecyclerViewBP;
import com.example.comandera.modelo.consultas;
import com.example.comandera.modelo.datosmesa;
import com.example.comandera.ui.login.InicioSesion;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
consultas consul= new consultas();
    //Button btnaceptar;
    //TextView txt1,txt2,txt3,txt4;
      Button cancelar;
      Button btnnuevaorden;
      EditText editText;
     // TextView txt1, txt4;
    //  ImageView txt2;

        GridView gridView;
        RecyclerView recyclerView;
       // String num=Globales.getInstance().numeroM;
       // String Button =findViewById(R.id.buttonnuevaorden);
       // arrayList.setText(num);
       private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        editText=findViewById(R.id.editText);
        String nombre=consul.ConsultarNomEstablecimiento(getApplicationContext(), "1");
        editText.setText(nombre);



        final int num= Integer.parseInt(Globales.getInstance().numeroM);
        //  listaingreso.setText(num);
        final List<datosmesa> arrayList = new ArrayList<datosmesa>();
//listclientes.add(new ProductosDatos(id,nombre,bitmap, precio));

            String var="",var2="",var3;
            Drawable imagen= ContextCompat.getDrawable(getApplicationContext(), R.drawable.mesita);
       /*      for(int i=1; i<=num; i++){

            var= String.valueOf(i);
            var2="Mesa "+var;
            arrayList.add(new datosmesa(var2, imagen));
        }*/
        validarNumeroMesa();
        arrayList.clear();
        int iin=Globales.getInstance().numeroInicio;
        if(iin==1) {
            for(int i=1; i<num+1; i++) {
                String cad=String.valueOf(i);
                var3="Mesa "+cad;
                arrayList.add(new datosmesa(var3, imagen));
            }
        }else {
            if (iin != 1) {
                int hasta = iin + num + 1;
                for (int i = iin + 1; i < hasta; i++) {
                    String cad= String.valueOf(i);
                    var3="Mesa "+cad;
                    arrayList.add(new datosmesa(var3, imagen));
                }
            }
        }


      // final RecyclerViewLL adaptador = new RecyclerViewLL((ArrayList<ProductosDatos>)  Globales.getInstance().listclientes);
        RecyclerViewBP adapter   = new RecyclerViewBP( (ArrayList<datosmesa>)arrayList);
        adapter.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
///CAMBIO DE COLOR PARA EL MAIN CONFIGURACION DE MESAS
              /*  int elemen=   recyclerView.getChildAdapterPosition(v);
               //cambio de color
                    if (!v.isSelected()) {
                        v.setSelected(true);
                        v.setBackgroundColor(Color.parseColor("#F04B27"));
                    } else {
                        v.setSelected(false);
                        v.setBackgroundColor(Color.parseColor("#7EF172"));
                    }
                System.out.println(" Pulsado el elemento    " + arrayList.get(elemen).getNombre());
                Toast.makeText(MainActivity.this,"Clic en "+ arrayList.get(elemen).getNombre(),Toast.LENGTH_LONG).show();*/
            }
        }));

        recyclerView.setAdapter(adapter);
        cancelar = (Button) findViewById(R.id.botoncancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btncancelar = new Intent(MainActivity.this, ConfiguracionMesa.class);
                startActivity(btncancelar);
            }
        });
        //CAMBIAR EL BOTÓN
        btnnuevaorden = (Button) findViewById(R.id.buttonnuevaorden);
        btnnuevaorden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent btncancelar = new Intent(MainActivity.this, Productos.class);
             //   startActivity(btncancelar);
                ///////////////Checar lo de las consultas de intercion en la tabla mesas con el boton guardar +1
                String nombre=consul.ConsultarNomEstablecimiento(getApplicationContext(), "1");
                 validarNumeroMesa();

                 int iin=Globales.getInstance().numeroInicio;
                 if(iin==1) {
                     for(int i=1; i<num+1; i++) {
                    consul.GuardarDatoMesa(getApplicationContext(), i, Globales.getInstance().IdEstablecimiento, Globales.getInstance().IdEstatusMa);
                    }
                 }else{
                     if(iin!=1){

                         int hasta=iin+num+1;
                         for(int i=iin+1; i<hasta; i++) {
                             consul.GuardarDatoMesa(getApplicationContext(), i, Globales.getInstance().IdEstablecimiento, Globales.getInstance().IdEstatusMa);
                         }
                     }
                 }
                 Toast.makeText(MainActivity.this,"Datos Almacenados",Toast.LENGTH_LONG).show();
             }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
        return true;
    }
/////////////////////////////////////////////////////Función para no salir de la pantalla deseada ////////////////////
   /* public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
// Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
// para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }*/
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public  boolean onOptionsItemSelected(MenuItem item){

        int id= item.getItemId();
        if (id==R.id.opcion1){
            Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
            startActivity(intencion2);

        }if (id==R.id.opcion2){
            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(MainActivity.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                            Toast.makeText(MainActivity.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
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





    private void validarNumeroMesa() {
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(getApplicationContext());
        SQLiteDatabase db = conn.getWritableDatabase();
        final List<datosmesa> arrayList = new ArrayList<datosmesa>();
        Drawable imagen = ContextCompat.getDrawable(getApplicationContext(), R.drawable.mesita);
        Cursor cursor2 =db.rawQuery("select * from mesa", null);

        String mesa="";
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;

                while (!cursor2.isAfterLast()) {
                     mesa= String.valueOf( cursor2.getString(cursor2.getColumnIndex("mesa_num")));
                    index++;
                    cursor2.moveToNext();

                }
                if (index != 0) {
                    int numero= Integer.parseInt(mesa);
                    Globales.getInstance().numeroInicio=numero;
                }
                else{
                   Globales.getInstance().numeroInicio=1;
                }

            }


        }catch(Exception e){
            //
        }
    }
}
