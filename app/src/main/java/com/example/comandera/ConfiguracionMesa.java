package com.example.comandera;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comandera.modelo.GridAdapter;
import com.example.comandera.ui.login.InicioSesion;

import java.util.ArrayList;

public class ConfiguracionMesa extends AppCompatActivity {
    Button btncancelar;
   // EditText txt1;
   // TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_mesa);
        LayoutInflater autoriza = LayoutInflater.from(getApplicationContext());
        View prompstsAutoriza = autoriza.inflate(R.layout.ingresomesas, null);
        final AlertDialog.Builder builderAutoriza = new AlertDialog.Builder(ConfiguracionMesa.this);
        builderAutoriza.setView(prompstsAutoriza);
        builderAutoriza.setCancelable(false);
        final EditText usua =  prompstsAutoriza.findViewById(R.id.ingreso);
        usua.setInputType(InputType.TYPE_CLASS_NUMBER );
       // final EditText ingre =  prompstsAutoriza.findViewById(R.id.tx1);
        builderAutoriza.setTitle("Ingresa el numero de mesa: ");
       // android:text="Ingrese el no. de mesas "
      //  builderAutoriza.setMessage(" ");
        builderAutoriza.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(usua.getText().toString().length()!=0) {
                    Globales.getInstance().numeroM = usua.getText().toString();
                    finish();
                    Intent intencion2 = new Intent(getApplication(), MainActivity.class);//se pase a la pantalla que se quiere
                    startActivity(intencion2);
                }else {
                    LayoutInflater imagenadvertencia_alert =LayoutInflater.from(ConfiguracionMesa.this);
                    final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConfiguracionMesa.this);
                    alerta.setMessage("¿Desea cerrar la ventana?")
                            .setCancelable(true)
                            .setCustomTitle(vista)
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
                                    startActivity(intencion2);
                                }
                            });

                    alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intencion2 = new Intent(getApplication(), ConfiguracionMesa.class);
                            startActivity(intencion2);
                           // dialog.cancel();
                        }
                    });
                    alerta.show();
                }

              //  Toast.makeText(ConfiguracionMesa.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
            }
        });
       builderAutoriza.show();
       // txt1=(EditText)findViewById(R.id.ingreso);

    /*    for(i=0; i<idArray.length; i++){
            button[i]=(Button)findViewById(idArray[i]);
            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(getApplicationContext(), "clic", Toast.LENGTH_SHORT).show();
                    switch (v.getId()){
                        case R.id.btn1:
                            Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.btn2:
                            Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.btn3:
                            Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.btn4:
                            Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.btn5:
                            Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.btn6:
                            Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.btn7:
                            Toast.makeText(getApplicationContext(), "7", Toast.LENGTH_SHORT).show();
                        case R.id.btn8:
                            Toast.makeText(getApplicationContext(), "8", Toast.LENGTH_SHORT).show();
                            break;

                    }

                }
            });
        }*/
/*
       btncancelar=(Button)findViewById(R.id.btncancelar);
        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btncancelar = new Intent(ConfiguracionMesa.this, MainActivity.class);
                startActivity(btncancelar);
            }
        });*/
    }

  /*  private static final int[] idArray = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8};
    private Button[] button = new Button[idArray.length];
    int i;*/
  /*  public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflowcasita, menu);
        return true;
    }
    public  boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if (id==R.id.opcion1){
                            Intent intencion2 = new Intent(getApplication(), MenuAdministrador.class);
                            startActivity(intencion2);

        }if (id==R.id.opcion2){
            LayoutInflater imagenadvertencia_alert =LayoutInflater.from(ConfiguracionMesa.this);
            final View vista = imagenadvertencia_alert.inflate(R.layout.preca, null);
            AlertDialog.Builder alerta = new AlertDialog.Builder(ConfiguracionMesa.this);
            alerta.setMessage("¿Desea cerrar las sesión?")
                    .setCancelable(true)
                    .setCustomTitle(vista)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intencion2 = new Intent(getApplication(), InicioSesion.class);
                            startActivity(intencion2);
                            Toast.makeText(ConfiguracionMesa.this,"Sesión  Cerrada",Toast.LENGTH_LONG).show();
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
*/
    }






