package com.example.proyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;


public class solicitarFragment extends Fragment {
    Spinner Opciones;
    Button btn_seleccionar;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static final String dataUserCache = "dataUser";
    private static final int modo_priv = Context.MODE_PRIVATE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public solicitarFragment() {
        // Required empty public constructor
    }


    public static solicitarFragment newInstance(String param1, String param2) {
        solicitarFragment fragment = new solicitarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solicitar, container, false);
        btn_seleccionar.findViewById(R.id.btn_seleccionar);
        Opciones.findViewById(R.id.spinner_comics);

        String[] opciones = {"Avengers", "Xmen", "Spiderman", "Fantastic Four"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Opciones.setAdapter(adapter);

        btn_seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitar();
            }

            private void solicitar() {
                String seleccion = Opciones.getSelectedItem().toString();
                if (seleccion.isEmpty()) { // Asegúrate de que esta opción esté en tu array
                    Toast.makeText(getContext(), "Por favor, seleccione un cómic", Toast.LENGTH_SHORT).show();
                } else {
                    // Aquí puedes manejar la selección válida
                    Toast.makeText(getContext(), "Has seleccionado: " + seleccion, Toast.LENGTH_SHORT).show();
                    // Aquí puedes agregar la lógica para lo que deseas hacer con la selección
                }

            }
        });


        return view;
    }
}