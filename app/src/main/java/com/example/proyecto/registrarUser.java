package com.example.proyecto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class registrarUser extends AppCompatActivity {

    EditText edt_Usuario, edt_correo, edt_contraseña, edt_birthday;
    private Button btn_registrar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_user);

        edt_Usuario.findViewById(R.id.edt_Usuario);
        edt_correo.findViewById(R.id.edt_correo);
        edt_contraseña.findViewById(R.id.edt_contraseña);
        edt_birthday.findViewById(R.id.edt_birthday);
        btn_registrar.findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edt_correo.getText().toString().trim();
                final String username = edt_Usuario.getText().toString().trim();
                final String password = edt_contraseña.getText().toString().trim();
                final String birthdate = edt_birthday.getText().toString().trim(); // Espera formato ISO yyyy-MM-dd
                // Validaciones básicas (puedes agregar más si quieres)
                if (email.isEmpty() || username.isEmpty() || password.isEmpty() || birthdate.isEmpty()) {
                    Toast.makeText(registrarUser.this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Ejecutar la petición en un hilo separado
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://:3000/register");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json");
                            conn.setDoOutput(true);

                            JSONObject json = new JSONObject();
                            json.put("email", email);
                            json.put("username", username);
                            json.put("password", password);
                            json.put("birthdate", birthdate);

                            OutputStream os = conn.getOutputStream();
                            os.write(json.toString().getBytes("UTF-8"));
                            os.close();
                            int responseCode = conn.getResponseCode();
                            InputStream is;
                            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                                is = conn.getInputStream();
                            } else {
                                is = conn.getErrorStream();
                            }

                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            reader.close();
                            final String responseMsg = response.toString();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (responseCode == HttpURLConnection.HTTP_CREATED) {
                                        Toast.makeText(registrarUser.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                                        // Aquí puedes limpiar los campos o ir a otra actividad
                                    } else {
                                        // Mostrar mensaje de error recibido del servidor
                                        Toast.makeText(registrarUser.this, "Error: " + responseMsg, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(registrarUser.this, "Error en la conexión", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();

            }
        });
    }
}