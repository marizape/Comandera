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

public class MenuCocinero extends AppCompatActivity {
    GridAdapter adapter;
    GridView gridView;
    String selectItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cocinero);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Producción");
        //arrayList.add("Orden");
        int images[] ={R.drawable.cosinero};
        adapter = new GridAdapter(this,images, arrayList);
        gridView = (GridView) findViewById(R.id.menucosinero);
        gridView.setAdapter(adapter);
       // gridView.setAccessibilityLiveRegion(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nombreProducto = view.findViewById(R.id.ig_tv_titulo);
                selectItem = nombreProducto.getText().toString();
                if (selectItem.equals("Producción")){
                    startActivity(new Intent(MenuCocinero.this, ordenes_a_preparar.class));
                    finish();
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
            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(MenuCocinero.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuCocinero.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                            Toast.makeText(MenuCocinero.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
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
