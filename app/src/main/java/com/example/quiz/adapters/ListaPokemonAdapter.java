package com.example.quiz.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.quiz.R;
import com.example.quiz.models.Pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> data;
    private ArrayList<Pokemon> dataOriginal;
    private Context context;

    public ListaPokemonAdapter(Context context){
        data = new ArrayList<>();
        this.context = context;
        dataOriginal = new ArrayList<>();
        dataOriginal.addAll(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon p = data.get(position);
        holder.nombrePokemon.setText(p.getName());

        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ p.getNumber() +".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imagenPokemon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarPokemon(ArrayList<Pokemon> listaPokemon) {
        data.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagenPokemon;
        private TextView nombrePokemon;

        public ViewHolder(View itemView){
            super(itemView);

            imagenPokemon = itemView.findViewById(R.id.imagenPokemon);
            nombrePokemon = itemView.findViewById(R.id.nombrePokemon);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filtrado (String filtro){
        int longitud = filtro.length();
        if (longitud == 0){
            data.clear();
            data.addAll(dataOriginal);
        } else {
            List<Pokemon> colec = data.stream().filter(i -> i.getName().toLowerCase().contains(filtro.toLowerCase())).collect(Collectors.toList());
        data.clear();
        data.addAll(colec);
        }
        notifyDataSetChanged();
    }
}
