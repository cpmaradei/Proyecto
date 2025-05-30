package com.example.proyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class registrarUser extends AppCompatActivity {

    EditText edt_Nombre, edt_correo, edt_contraseña,edt_birthday;
    Button btn_registrar;
    private static final String pref_name = "UserPrefs";
    private static final int modo_private = Context.MODE_PRIVATE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_user);

        edt_Nombre = findViewById(R.id.edt_Nombre);
        edt_correo= findViewById(R.id.edt_correo);
        edt_contraseña = findViewById(R.id.edt_contraseña);
        edt_birthday = findViewById(R.id.edt_birthday);
        btn_registrar = findViewById(R.id.btn_registrar);

        SharedPreferences sharedPreferences = getSharedPreferences(pref_name, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesarRegistrar();

            }
            private void procesarRegistrar() {
                String nombre = edt_Nombre.getText().toString().trim();
                String correo = edt_correo.getText().toString().trim();
                String contraseña = edt_contraseña.getText().toString().trim();
                String fechaNacimiento = edt_birthday.getText().toString().trim();

                if (nombre.isEmpty()){
                    Toast.makeText(registrarUser.this, "Por favor ingresa tu nombre", Toast.LENGTH_LONG).show();

                }
                if (correo.isEmpty()){
                    Toast.makeText(registrarUser.this, "Por favor ingresa tu correo", Toast.LENGTH_LONG).show();

                }
                if (contraseña.isEmpty()){
                    Toast.makeText(registrarUser.this, "Por favor ingresa tu contraseña", Toast.LENGTH_LONG).show();

                }
                if (fechaNacimiento.isEmpty()){
                    Toast.makeText(registrarUser.this, "Por favor ingresa tu fecha de nacimiento", Toast.LENGTH_LONG).show();

                }

                editor.putString("nombre", nombre);
                editor.putString("correo", correo);
                editor.putString("contraseña", contraseña);
                editor.putString("fechaNacimiento", fechaNacimiento);
                editor.apply();

                Toast.makeText(registrarUser.this, "Registro exitoso", Toast.LENGTH_LONG).show();

            }
            private void cargardatos(){
                SharedPreferences sharedPref = getSharedPreferences(pref_name, MODE_PRIVATE);
                String savedname = sharedPref.getString("nombre", "");
                String savedEmail = sharedPref.getString("correo", "");
                String savedpws = sharedPref.getString("contraseña", "");
                String savedhbd = sharedPref.getString("fechaNacimiento", "");

            }






        });










    }
}