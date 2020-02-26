package com.example.comandera;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.comandera.modelo.GridAdapter;
import com.example.comandera.modelo.RuntimePermissionUtil;
import com.example.comandera.ui.login.InicioSesion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
public class MenuAdministrador extends AppCompatActivity {

    GridAdapter adapter;
    GridView gridView;
    String selectItem;
  //  Button cerrar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuadministrador);

        Globales.getInstance().usuario="1";


                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add("Disponibilidad de mesas");
                arrayList.add("Ordenes");
                arrayList.add("Producción");
                arrayList.add("Configuración");
                //arrayList.add("Ayuda");

                int images[] ={R.drawable.mesa, R.drawable.orden,
                        R.drawable.cosinero, R.drawable.configuracion};
                adapter = new GridAdapter(this,images, arrayList);
                gridView = (GridView) findViewById(R.id.menuadmi);
                gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               TextView nombreProducto = view.findViewById(R.id.ig_tv_titulo);
                selectItem = nombreProducto.getText().toString();
                if (selectItem.equals("Disponibilidad de mesas")){
                    startActivity(new Intent(MenuAdministrador.this, disponibilidadmesas.class));

                }if (selectItem.equals("Ordenes")){
                    startActivity(new Intent(MenuAdministrador.this, ordenes.class));

                }if (selectItem.equals("Producción")){
                    startActivity(new Intent(MenuAdministrador.this, ConfiguracionMesa.class));

                }if (selectItem.equals("Configuración")){
                    startActivity(new Intent(MenuAdministrador.this, ConfiguracionMesa.class));

                }

            }
        });

            }





    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflow, menu);
        return true;
    }
    public  boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if (id==R.id.opcion1){

            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(MenuAdministrador.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuAdministrador.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                            Toast.makeText(MenuAdministrador.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
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
