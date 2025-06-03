package com.example.proyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class login extends AppCompatActivity {

    EditText edt_Usuario, edt_contraseña;
    Button btn_Login, btn_regis;
    private static final String dataUserCache = "dataUser";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final int modo_private = Context.MODE_PRIVATE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        edt_Usuario = findViewById(R.id.edt_Usuario);
        edt_contraseña = findViewById(R.id.edt_contraseña);
        btn_Login = findViewById(R.id.btn_Login);
        btn_regis = findViewById(R.id.btn_regis);

        sharedPreferences = getSharedPreferences(dataUserCache, modo_private);
        editor = sharedPreferences.edit();


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesar();
            }

            private void procesar() {
                String user = edt_Usuario.getText().toString();
                String pws = edt_contraseña.getText().toString();

                if (!user.isEmpty() && !pws.isEmpty()) {

                    editor.putString("user", user);
                    editor.putString("password", pws);
                    editor.apply();

                    if (usuarioExiste(user, pws)) {

                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(login.this, "Por favor llenar todos los campos", Toast.LENGTH_LONG).show();
                }
            }

            private boolean usuarioExiste(String user, String pws) {
                SharedPreferences prefs = getSharedPreferences("dataUser", MODE_PRIVATE);
                String savedUser = prefs.getString("user", "");
                String savedPws = prefs.getString("password", "");


                return user.equals(savedUser) && pws.equals(savedPws);
            }
        });

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, registrarUser.class);
                startActivity(intent);
            }
        });
    }
}





