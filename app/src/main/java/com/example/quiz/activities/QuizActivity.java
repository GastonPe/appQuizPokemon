package com.example.quiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.quiz.R;
import com.example.quiz.db.DbJugadores;
import com.example.quiz.models.Pokemon;
import com.example.quiz.models.PokemonRespuesta;
import com.example.quiz.pokeapi.PokeapiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;
    private ImageView ivAtras, imagenPokemon;
    private Button btnOpc1, btnOpc2, btnOpc3;
    ArrayList<Pokemon> listaPokemon = null;
    private int totalPokemons = 151;
    private String respuestaUsuario;
    private TextView tvPuntuacion, tvTiempo;
    private int puntuacion = 0;
    private boolean dialogoAbierto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ivAtras = findViewById(R.id.ivAtras);
        btnOpc1 = findViewById(R.id.btnOpc1);
        btnOpc2 = findViewById(R.id.btnOpc2);
        btnOpc3 = findViewById(R.id.btnOpc3);
        imagenPokemon = findViewById(R.id.imagenPokemon);
        tvPuntuacion = findViewById(R.id.tvPuntuacion);
        tvTiempo = findViewById(R.id.tvTiempo);

        tvPuntuacion.setText(String.valueOf(puntuacion));

        new CountDownTimer(30000, 1000){
            @Override
            public void onTick(long l) {
                if (!dialogoAbierto){
                    tvTiempo.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(l)));
                } else{
                    cancel();
                }
            }
            @Override
            public void onFinish() {
                abrirDialogo();
            }
        }.start();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos();

        ivAtras.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), InicioActivity.class);
            startActivity(i);
        });


    }

    private void rellenarQuiz() {

        ArrayList<Pokemon> pokemonsQuiz = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int pokemonAleatorio = (int) (Math.random() * listaPokemon.size());
            pokemonsQuiz.add(listaPokemon.get(pokemonAleatorio));
        }

        //Pokemon elegido en imagen
        Glide.with(QuizActivity.this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ pokemonsQuiz.get(0).getNumber() +".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagenPokemon);

        //Nombre del pokemon elegido
        respuestaUsuario = pokemonsQuiz.get(0).getName();

        Collections.shuffle(pokemonsQuiz); //Mezclar array

        btnOpc1.setText(pokemonsQuiz.get(0).getName());
        btnOpc2.setText(pokemonsQuiz.get(1).getName());
        btnOpc3.setText(pokemonsQuiz.get(2).getName());
    }

    //Obtener datos de Api
    private void obtenerDatos() {
        PokeapiService service = retrofit.create(PokeapiService.class);

        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon(totalPokemons);

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<PokemonRespuesta> call, @NonNull Response<PokemonRespuesta> response) {

                if (response.isSuccessful()) {
                    PokemonRespuesta pokemonRespuesta = response.body();
                    if(pokemonRespuesta != null){
                        listaPokemon = pokemonRespuesta.getResults();
                        rellenarQuiz();
                    }
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonRespuesta> call, @NonNull Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnOpc1){
            if (btnOpc1.getText().toString().equals(respuestaUsuario)){
                tvPuntuacion.setText(String.valueOf(++puntuacion));
                obtenerDatos();
                btnOpc1.setBackgroundColor(getResources().getColor(R.color.amber_500));
            }else{
                btnOpc1.setBackgroundColor(getResources().getColor(R.color.rojo));

                if (btnOpc2.getText().toString().equals(respuestaUsuario)){
                    btnOpc2.setBackgroundColor(getResources().getColor(R.color.verde));
                } else {
                    btnOpc3.setBackgroundColor(getResources().getColor(R.color.verde));
                }
                dialogoAbierto = true;
                abrirDialogo();
            }
        } else if (view.getId() == R.id.btnOpc2){
            if (btnOpc2.getText().toString().equals(respuestaUsuario)){
                tvPuntuacion.setText(String.valueOf(++puntuacion));
                obtenerDatos();
                btnOpc2.setBackgroundColor(getResources().getColor(R.color.amber_500));
            }else{
                btnOpc2.setBackgroundColor(getResources().getColor(R.color.rojo));

                if (btnOpc1.getText().toString().equals(respuestaUsuario)){
                    btnOpc1.setBackgroundColor(getResources().getColor(R.color.verde));
                } else {
                    btnOpc3.setBackgroundColor(getResources().getColor(R.color.verde));
                }
                dialogoAbierto = true;
                abrirDialogo();
            }
        } else if (view.getId() == R.id.btnOpc3){
            if (btnOpc3.getText().toString().equals(respuestaUsuario)){
                tvPuntuacion.setText(String.valueOf(++puntuacion));
                obtenerDatos();
                btnOpc3.setBackgroundColor(getResources().getColor(R.color.amber_500));
            }else{
                btnOpc3.setBackgroundColor(getResources().getColor(R.color.rojo));

                if (btnOpc1.getText().toString().equals(respuestaUsuario)){
                    btnOpc1.setBackgroundColor(getResources().getColor(R.color.verde));
                } else {
                    btnOpc2.setBackgroundColor(getResources().getColor(R.color.verde));
                }
                dialogoAbierto = true;
                abrirDialogo();
            }
        }
/*
        switch (view.getId()){
            case R.id.btnOpc1:
                if (btnOpc1.getText().toString().equals(respuestaUsuario)){
                    tvPuntuacion.setText(String.valueOf(++puntuacion));
                    obtenerDatos();
                    btnOpc1.setBackgroundColor(getResources().getColor(R.color.amber_500));
                }else{
                    btnOpc1.setBackgroundColor(getResources().getColor(R.color.rojo));

                    if (btnOpc2.getText().toString().equals(respuestaUsuario)){
                        btnOpc2.setBackgroundColor(getResources().getColor(R.color.verde));
                    } else {
                        btnOpc3.setBackgroundColor(getResources().getColor(R.color.verde));
                    }
                    dialogoAbierto = true;
                    abrirDialogo();
                }
                break;
            case R.id.btnOpc2:
                if (btnOpc2.getText().toString().equals(respuestaUsuario)){
                    tvPuntuacion.setText(String.valueOf(++puntuacion));
                    obtenerDatos();
                    btnOpc2.setBackgroundColor(getResources().getColor(R.color.amber_500));
                }else{
                    btnOpc2.setBackgroundColor(getResources().getColor(R.color.rojo));

                    if (btnOpc1.getText().toString().equals(respuestaUsuario)){
                        btnOpc1.setBackgroundColor(getResources().getColor(R.color.verde));
                    } else {
                        btnOpc3.setBackgroundColor(getResources().getColor(R.color.verde));
                    }
                    dialogoAbierto = true;
                    abrirDialogo();
                }
                break;
            case R.id.btnOpc3:
                if (btnOpc3.getText().toString().equals(respuestaUsuario)){
                    tvPuntuacion.setText(String.valueOf(++puntuacion));
                    obtenerDatos();
                    btnOpc3.setBackgroundColor(getResources().getColor(R.color.amber_500));
                }else{
                    btnOpc3.setBackgroundColor(getResources().getColor(R.color.rojo));

                    if (btnOpc1.getText().toString().equals(respuestaUsuario)){
                        btnOpc1.setBackgroundColor(getResources().getColor(R.color.verde));
                    } else {
                        btnOpc2.setBackgroundColor(getResources().getColor(R.color.verde));
                    }
                    dialogoAbierto = true;
                    abrirDialogo();
                }
                break;
        }*/
    }

    public void abrirDialogo(){
        Dialog dialogo = new Dialog(this);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_nombre_puntuacion);

        EditText tvUsuario = dialogo.findViewById(R.id.tvUsuario);
        Button btnAgregarPuntuacion = dialogo.findViewById(R.id.btnAgregarPuntuacion);

        dialogo.show();

        btnAgregarPuntuacion.setOnClickListener(view -> {

            DbJugadores dbJugadores = new DbJugadores(QuizActivity.this);
            dbJugadores.insertarJugador(tvUsuario.getText().toString(), puntuacion);

            /*if (id > 0){
                Toast.makeText(QuizActivity.this, "Registro Agregado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(QuizActivity.this, "Registro No Agregado", Toast.LENGTH_SHORT).show();
            }*/
            puntuacion = 0;
            dialogoAbierto = false;
            dialogo.dismiss();
            Intent i = new Intent(getApplicationContext(), InicioActivity.class);
            startActivity(i);
        });
    }




}