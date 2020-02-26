package com.example.comandera;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comandera.modelo.GridAdapter;
import com.example.comandera.ui.login.InicioSesion;

import java.util.ArrayList;

public class MenuMesero extends AppCompatActivity {
    GridAdapter adapter;
    GridView gridView;
    String selectItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mesero);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Disponibilidad de mesas");
        arrayList.add("Ordenes");

        int images[] ={R.drawable.mesa, R.drawable.orden};

        adapter = new GridAdapter(this,images, arrayList);
        gridView = (GridView) findViewById(R.id.menumesero);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nombreProducto = view.findViewById(R.id.ig_tv_titulo);
                selectItem = nombreProducto.getText().toString();
                if (selectItem.equals("Disponibilidad de mesas")){
                    startActivity(new Intent(MenuMesero.this, disponibilidadmesas.class));
                }if (selectItem.equals("Ordenes")){
                    startActivity(new Intent(MenuMesero.this, ordenes.class));
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

            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(MenuMesero.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuMesero.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                            Toast.makeText(MenuMesero.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
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
