package com.example.quiz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.R;
import com.example.quiz.models.Jugador;

import java.util.ArrayList;

public class PuntuacionesAdapter extends RecyclerView.Adapter<PuntuacionesAdapter.ViewHolder> {

    private ArrayList<Jugador> listaJugadores;

    public PuntuacionesAdapter(ArrayList<Jugador> listaJugadores){
        this.listaJugadores = listaJugadores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_usuario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvUsuario.setText(listaJugadores.get(position).getName().toUpperCase());
        holder.tvPuntuacion.setText(String.valueOf(listaJugadores.get(position).getPuntuacion()));

    }

    @Override
    public int getItemCount() {
        return listaJugadores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsuario, tvPuntuacion;

        public ViewHolder(View itemView){
            super(itemView);

            tvUsuario = itemView.findViewById(R.id.tvUsuario);
            tvPuntuacion = itemView.findViewById(R.id.tvPuntuacion);
        }
    }
}
