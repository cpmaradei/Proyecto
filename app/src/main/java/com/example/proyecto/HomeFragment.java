package com.example.proyecto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Adaptadores.HeroesAdaptador;
import com.example.proyecto.ProyectoApp.SuperHeroes;
import com.example.proyecto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    Button btn_ver_detalle, btn_buscar;
    EditText edt_buscar;

    private RecyclerView rcv_Heroes;
    private HeroesAdaptador adapter;
    private List<SuperHeroes> superHeroesList;

    private final String publicKey = "be976077e534b19bcd4a2840fa3eba7f";
    private final String hash = "ec79668fd48c5ad4b02e917abb9839c7";
    private final String ts = "1";
    private final String url = "https://gateway.marvel.com/v1/public/characters?ts=" + ts + "&apikey=" + publicKey + "&hash=" + hash;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rcv_Heroes = view.findViewById(R.id.rcv_Heroes);
        rcv_Heroes.setLayoutManager(new LinearLayoutManager(getContext()));

        superHeroesList = new ArrayList<>();
        adapter = new HeroesAdaptador(superHeroesList);
        rcv_Heroes.setAdapter(adapter);
        btn_buscar = view.findViewById(R.id.btn_buscar);
        edt_buscar = view.findViewById(R.id.edt_buscar);

        btn_ver_detalle = view.findViewById(R.id.btn_ver_detalle);
        btn_ver_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), detalleheroe.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btn_ver_detalle = view.findViewById(R.id.btn_ver_detalle);
        btn_ver_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), detalleheroe.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        cargarSuperHeroes();

        return view;
    }

    private void cargarSuperHeroes() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> procesarRespuesta(response),
                error -> Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }

    private void procesarRespuesta(JSONObject response) {
        try {
            JSONArray results = response.getJSONObject("data").getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject personaje = results.getJSONObject(i);
                String nombre = personaje.getString("name");
                String descripcion = personaje.getString("description");

                JSONObject thumbnail = personaje.getJSONObject("thumbnail");
                String imagenPath = thumbnail.getString("path");
                String extension = thumbnail.getString("extension");
                String imagenUrl = imagenPath + "." + extension;

                superHeroesList.add(new SuperHeroes(imagenUrl, nombre, descripcion));
            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            Toast.makeText(getContext(), "Error en el servidor, intente más tarde", Toast.LENGTH_SHORT).show();
        }
    }
}
