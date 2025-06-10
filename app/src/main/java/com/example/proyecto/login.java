package com.example.proyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL("http://10.0.2.2:3000/login");
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                conn.setRequestProperty("Content-Type", "application/json");
                                conn.setDoOutput(true);

                                JSONObject json = new JSONObject();
                                json.put("username", user);
                                json.put("password", pws);
                                OutputStream os = conn.getOutputStream();
                                os.write(json.toString().getBytes("UTF-8"));
                                os.close();

                                int responseCode = conn.getResponseCode();
                                InputStream is;
                                if (responseCode == HttpURLConnection.HTTP_OK) {
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
                                runOnUiThread(() -> {
                                    try {
                                        if (responseCode == HttpURLConnection.HTTP_OK) {
                                            JSONObject jsonResponse = new JSONObject(responseMsg);
                                            JSONObject userJson = jsonResponse.getJSONObject("user");

                                            SharedPreferences preferences = getSharedPreferences("dataUser", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("user", userJson.getString("username"));
                                            editor.putString("correo", userJson.getString("email"));
                                            editor.putString("fechaNacimiento", userJson.getString("fechaFormateada"));
                                            editor.apply();

                                            Intent intent = new Intent(login.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(login.this, "Credenciales incorrectas: " + responseMsg, Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(login.this, "Error procesando la respuesta del servidor", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Registro", "Error en la conexión: " + e.getMessage());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(login.this, "Error en la conexión", Toast.LENGTH_LONG).show();
                                    }
                                });
                          }
                        }
                    }).start();
                } else {
                    Toast.makeText(login.this, "Por favor llenar todos los campos", Toast.LENGTH_LONG).show();
                }
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