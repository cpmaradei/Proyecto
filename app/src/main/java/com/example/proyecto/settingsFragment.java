package com.example.proyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class settingsFragment extends Fragment {

    TextView txt_usuario, txt_correo, txt_fecha_nacimiento;
    Button btn_cerrar_sesion;
    private static final String dataUserCache = "dataUser";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public settingsFragment() {
        // Required empty public constructor
    }

    public static settingsFragment newInstance(String param1, String param2) {
        settingsFragment fragment = new settingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        txt_usuario= view.findViewById(R.id.txt_usuario);
        txt_correo= view.findViewById(R.id.txt_correo);
        txt_fecha_nacimiento= view.findViewById(R.id.txt_fecha_nacimiento);
        btn_cerrar_sesion= view.findViewById(R.id.btn_cerrar_sesion);
        SharedPreferences preferences = getActivity().getSharedPreferences(dataUserCache, Context.MODE_PRIVATE);

        String usuario = preferences.getString("user", "no registrado");
        String correo = preferences.getString("correo", "no registrado");
        String fechaNacimiento = preferences.getString("fechaNacimiento", "no registrado");

        txt_usuario.setText("Usuario: " + usuario);
        txt_correo.setText("Correo: " + correo);
        txt_fecha_nacimiento.setText("Fecha de nacimiento: " + fechaNacimiento);

        return view;
    }
}