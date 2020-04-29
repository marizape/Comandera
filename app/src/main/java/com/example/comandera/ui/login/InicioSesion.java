package com.example.comandera.ui.login;

import android.Manifest;
import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comandera.ConfiguracionMesa;
import com.example.comandera.Globales;
import com.example.comandera.MenuAdministrador;
import com.example.comandera.MenuCocinero;
import com.example.comandera.MenuMesero;
import com.example.comandera.R;
import com.example.comandera.modelo.RuntimePermissionUtil;
import com.example.comandera.modelo.consultas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InicioSesion extends AppCompatActivity {
    consultas c = new consultas();
    Button loginButton;
    private LoginViewModel loginViewModel;
    private final static String[] requestWritePermission = { Manifest.permission.WRITE_EXTERNAL_STORAGE };
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        try {
            deployDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
//Borrar
       /* loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);*/
        final EditText usuario = findViewById(R.id.username);
        final EditText pass = findViewById(R.id.password);
         loginButton= findViewById(R.id.login);
     /*   usernameEditText.setText("Administrador@hotmail.com");
        passwordEditText.setText("123456");*/


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usuario.getText().toString().length()!=0) {
                    String usu = usuario.getText().toString();
                    String pas = pass.getText().toString();
                    IniciarSesion(getApplicationContext(), usu, pas);
                    IniciarSesionCocinero(getApplicationContext(), usu, pas);
                    IniciarSesionAdministrador(getApplicationContext(), usu, pas);
                }else {
                    //Toast.makeText(InicioSesion.this, "Lo sentimos, usted no tiene acceso", Toast.LENGTH_SHORT).show();
                }

            }
        });

/*
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario=((EditText) findViewById(R.id.username)).getText().toString();
                String pass=((EditText) findViewById(R.id.password)).getText().toString();



                if(usuario.equals("Administrador@hotmail.com")&&pass.equals("123456")){
                    Globales.getInstance().usuario=usuario;
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    startActivity(new Intent(InicioSesion.this, MenuAdministrador.class));
                }else
                {
                    if(usuario.equals("Cocinero@hotmail.com")&&pass.equals("123456")){
                        Globales.getInstance().usuario=usuario;
                        loadingProgressBar.setVisibility(View.VISIBLE);
                        loginViewModel.login(usernameEditText.getText().toString(),
                                passwordEditText.getText().toString());
                        startActivity(new Intent(InicioSesion.this, MenuCocinero.class));
                    }else
                    {
                        if(usuario.equals("Mesero@hotmail.com")&&pass.equals("123456")){
                            Globales.getInstance().usuario=usuario;
                            loadingProgressBar.setVisibility(View.VISIBLE);
                            loginViewModel.login(usernameEditText.getText().toString(),
                                    passwordEditText.getText().toString());
                            startActivity(new Intent(InicioSesion.this, MenuMesero.class));
                        }else
                        {
                            Toast.makeText(getApplicationContext(),"Los datos ingresados son incorrectos",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });*/
////////////////////
    }
    public void IniciarSesion(Context context,String email, String password){
        int var,variCocinero;
        var= c.loginM(context, email, password);
       // variCocinero=c.loginCocinero(context,email,password);
        if(var==1){
            //startActivity(new Intent(InicioSesion.this, MenuAdministrador.class));
            startActivity(new Intent(InicioSesion.this, MenuMesero.class));
        }
        /*if(var==2){
            startActivity(new Intent(InicioSesion.this, MenuMesero.class));
        }if(var==3){
            startActivity(new Intent(InicioSesion.this, MenuCocinero.class));
        }*/
        else {
        //   Toast.makeText(InicioSesion.this, "Lo sentimos, usted no tiene acceso", Toast.LENGTH_SHORT).show();
        }

       /* if(variCocinero==2){
            startActivity(new Intent(InicioSesion.this, MenuCocinero.class));
        }
        else {
            Toast.makeText(InicioSesion.this, "Lo sentimos, usted no tiene acceso", Toast.LENGTH_SHORT).show();
        }*/

    }

    public void IniciarSesionCocinero(Context context,String email, String password){
        int variCocinero;
        variCocinero=c.loginCocinero(context,email,password);
        if(variCocinero==2){
            startActivity(new Intent(InicioSesion.this, MenuCocinero.class));
        }
        else {
           // Toast.makeText(InicioSesion.this, "Lo sentimos, usted no tiene acceso", Toast.LENGTH_SHORT).show();
        }
    }

    public void IniciarSesionAdministrador(Context context,String email, String password){
        int variMesero;
        variMesero=c.loginAdministrador(context,email,password);
        if(variMesero==3){
            startActivity(new Intent(InicioSesion.this, MenuAdministrador.class));
        }
        else {
          // Toast.makeText(InicioSesion.this, "Lo sentimos, usted no tiene acceso", Toast.LENGTH_SHORT).show();
        }
    }





    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void deployDatabase() throws IOException {
        //Open your local db as the input stream

        final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        String packageName = getApplicationContext().getPackageName();
        String DB_PATH = "/data/data/" + packageName + "/databases/";

        //Create the directory if it does not exist
        File directory = new File(DB_PATH);
        File archivo = new File(DB_PATH+"/BDcomandera.db");
       if(!archivo.exists()) {
            if (!directory.exists()) {
                if (hasWritePermission) {
                    directory.mkdirs();
                } else {
                    RuntimePermissionUtil.requestPermission(InicioSesion.this, requestWritePermission, 100);
                    directory.mkdirs();
                }
            }
            RuntimePermissionUtil.requestPermission(InicioSesion.this, requestWritePermission, 100);
            String DB_NAME = "BDcomandera.db"; //The name of the source sqlite file

            InputStream myInput = getAssets().open(
                    "BDcomandera.db");

            // Path to the just created empty db
            String outFileName = DB_PATH + DB_NAME;

            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        //}/*else {
            //Toast.makeText(MenuGeneral.this, "Base de datos encontrada", Toast.LENGTH_LONG).show();
        }
    }
}
