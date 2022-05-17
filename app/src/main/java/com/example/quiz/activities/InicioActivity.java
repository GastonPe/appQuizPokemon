package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.quiz.R;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        Button btnEmpezar = findViewById(R.id.btnEmpezar);
        Button btnPuntuaciones = findViewById(R.id.btnPuntuaciones);
        Button btnlista = findViewById(R.id.btnlista);

        btnEmpezar.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), QuizActivity.class);
            startActivity(i);
        });

        btnPuntuaciones.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), PuntuacionesActivity.class);
            startActivity(i);
        });

        btnlista.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });
    }
}