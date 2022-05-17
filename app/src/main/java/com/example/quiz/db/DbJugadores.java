package com.example.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.quiz.models.Jugador;

import java.util.ArrayList;

public class DbJugadores extends DbHelper{

    Context context;

    public DbJugadores(@Nullable Context context){
        super(context);
        this.context = context;
    }

    public long insertarJugador(String name, int puntuacion){
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("puntuacion", puntuacion);

             id = db.insert(TABLE_PUNTUACIONES, null, values);
        }catch(Exception e){
            e.getMessage();
        }

        return id;
    }

    public ArrayList<Jugador> mostrarJugadores(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        Jugador jugador;
        Cursor cursorJugadores;

        cursorJugadores = db.rawQuery("SELECT * FROM " + TABLE_PUNTUACIONES + " ORDER BY puntuacion DESC", null);

        if (cursorJugadores.moveToFirst()){
            do{
                jugador = new Jugador();
                jugador.setId(cursorJugadores.getInt(0));
                jugador.setName(cursorJugadores.getString(1));
                jugador.setPuntuacion(cursorJugadores.getInt(2));
                listaJugadores.add(jugador);
            } while (cursorJugadores.moveToNext());
        }
        cursorJugadores.close();

        return  listaJugadores;
    }
}
