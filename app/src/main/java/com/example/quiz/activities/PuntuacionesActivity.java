package com.example.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.R;
import com.example.quiz.adapters.PuntuacionesAdapter;
import com.example.quiz.db.DbJugadores;

public class PuntuacionesActivity extends AppCompatActivity {

    private ImageView ivAtras;
    private TextView tvNohay;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaciones);

        ivAtras = findViewById(R.id.ivAtras);
        recyclerView = findViewById(R.id.recyclerView);
        tvNohay = findViewById(R.id.tvNohay);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DbJugadores dbJugadores = new DbJugadores(PuntuacionesActivity.this);

        PuntuacionesAdapter adapter = new PuntuacionesAdapter(dbJugadores.mostrarJugadores());
        recyclerView.setAdapter(adapter);

        if (dbJugadores.mostrarJugadores().size() == 0){
            tvNohay.setVisibility(View.VISIBLE);
        } else{
            tvNohay.setVisibility(View.GONE);
        }

        ivAtras.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), InicioActivity.class);
            startActivity(i);
        });
    }
}