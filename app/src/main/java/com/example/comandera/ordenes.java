package com.example.comandera;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comandera.modelo.consultas;
import com.example.comandera.ui.login.InicioSesion;

public class ordenes extends AppCompatActivity {
    consultas consul= new consultas();
    Spinner spinnerlistaEstatus;
    int contador=0;
    Button btnordennueva;
   //Button btnproductos;
    Button btnordencuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);
        spinnerlistaEstatus=(Spinner)findViewById(R.id.estatus);
        btnordennueva = (Button) findViewById(R.id.btnordennueva);
        btnordencuenta=(Button) findViewById(R.id.btnordencuenta);


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

                        Toast.makeText(spn.getContext(), "Esatus: " + spn.getItemAtPosition(posicion).toString(), Toast.LENGTH_LONG).show();
                        if(contador!=0) {
                           // cargarProductosConCaracteristica();
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
}
