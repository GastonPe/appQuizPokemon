package com.example.quiz.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.R;
import com.example.quiz.adapters.ListaPokemonAdapter;
import com.example.quiz.models.Pokemon;
import com.example.quiz.models.PokemonRespuesta;
import com.example.quiz.pokeapi.PokeapiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final String TAG = "POKEDEX";
    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;
    private SearchView searchPokemon;
    private ImageView ivAtras;

    //private int offset;
    //private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivAtras = findViewById(R.id.ivAtras);
        searchPokemon = findViewById(R.id.searchPokemon);
        recyclerView = findViewById(R.id.recyclerView);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        //Escucha al hacer scroll
        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount){
                            Log.i(TAG, " Final de la lista");
                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });*/

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //aptoParaCargar = true;
        //offset = 0;
        obtenerDatos();

        searchPokemon.setOnQueryTextListener(this);

        ivAtras.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), InicioActivity.class);
            startActivity(i);
        });
    }

    private void obtenerDatos() {
        PokeapiService service = retrofit.create(PokeapiService.class);

        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon(1126);

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<PokemonRespuesta> call, @NonNull Response<PokemonRespuesta> response) {
                //aptoParaCargar = true;
                if (response.isSuccessful()) {
                    PokemonRespuesta pokemonRespuesta = response.body();
                    if (pokemonRespuesta != null) {
                        ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();
                        listaPokemonAdapter.agregarPokemon(listaPokemon);
                    }
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonRespuesta> call, @NonNull Throwable t) {
                //aptoParaCargar = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onQueryTextChange(String s) {
        //aptoParaCargar = true;
        listaPokemonAdapter.filtrado(s);
        if (s.isEmpty()) obtenerDatos();
        return false;
    }
}