package com.example.proyecto;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class registrarUser extends AppCompatActivity {

    EditText edt_Usuario, edt_correo, edt_contraseña, edt_birthday;
    private Button btn_registrar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_user);

        edt_Usuario = findViewById(R.id.edt_Usuario);
        edt_correo = findViewById(R.id.edt_correo);
        edt_contraseña = findViewById(R.id.edt_contraseña);
        edt_birthday = findViewById(R.id.edt_birthday);
        btn_registrar = findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edt_Usuario.getText().toString().trim();
                String email = edt_correo.getText().toString().trim();
                String password = edt_contraseña.getText().toString().trim();
                String birthdate = edt_birthday.getText().toString().trim();

                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                formatoFecha.setLenient(false);


                try {
                    Date fechaNacimiento = formatoFecha.parse(birthdate);
                    registrarUsuario(username, email, password, fechaNacimiento); // Enviar la fecha validada
                } catch (ParseException e) {
                    Toast.makeText(registrarUser.this, "Fecha inválida. Usa formato YYYY-MM-DD", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

            private void registrarUsuario(final String username, final String email, final String password, final Date birthdate) {
                // Validaciones básicas
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String fechaFormateada = formatoFecha.format(birthdate);

                if (email.isEmpty() || username.isEmpty() || password.isEmpty() || birthdate == null) {
                    Toast.makeText(registrarUser.this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://10.0.2.2:3000/register");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json");
                            conn.setDoOutput(true);

                            JSONObject json = new JSONObject();
                            json.put("email", email);
                            json.put("username", username);
                            json.put("password", password);
                            json.put("birthdate", fechaFormateada);

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
                                        Toast.makeText(registrarUser.this, "Registro exitoso. Inicia sesión.", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(registrarUser.this, login.class);
                                        startActivity(intent);
                                        finish();

                                    } else {

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

        }


