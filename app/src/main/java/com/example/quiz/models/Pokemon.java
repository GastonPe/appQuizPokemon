package com.example.quiz.models;

public class Pokemon {

    private String name;
    private String url;
    private int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        //Separa la URL en partes para conseguir el numero del pokemon
        // URL ejemplo: "https://pokeapi.co/api/v2/pokemon/1/"
        String[] urlPartes = url.split("/");

        //Accedemos a la ultima posición
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
