package com.example.proyecto.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.ProyectoApp.SuperHeroes;
import com.example.proyecto.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HeroesAdaptador extends RecyclerView.Adapter<HeroesAdaptador.ViewHolder>{
    private List<SuperHeroes> superHeroesList;
    private List<SuperHeroes> superHeroesListFull;
    public HeroesAdaptador(List<SuperHeroes> superHeroesList) {
        this.superHeroesList = superHeroesList;
        superHeroesListFull = new ArrayList<>(superHeroesList);
    }



    @NonNull
    @Override
    public HeroesAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_superheroes, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HeroesAdaptador.ViewHolder holder, int position) {
        SuperHeroes superHero = superHeroesList.get(position);
        holder.bind(superHero);

    }

    @Override
    public int getItemCount() {
        return superHeroesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_hero;
        TextView txt_hero, txt_descripcion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_hero= itemView.findViewById(R.id.txt_hero);
            txt_descripcion = itemView.findViewById(R.id.txt_descripcion);
            img_hero= itemView.findViewById(R.id.img_hero);
        }
        public void bind(SuperHeroes superHero) {
            txt_hero.setText(superHero.getNombre());
            txt_descripcion.setText(superHero.getDescripcion());
            Picasso.get().load(superHero.getImagen()).into(img_hero);

        }
    }
    public void filter(String text) {
        superHeroesList.clear();
        if (text.isEmpty()) {
            superHeroesList.addAll(superHeroesListFull);
        } else {
            for (SuperHeroes hero : superHeroesListFull) {
                if (hero.getNombre().toLowerCase().contains(text.toLowerCase())) {
                    superHeroesList.add(hero);
                }
            }
        }
        notifyDataSetChanged();
    }


}
