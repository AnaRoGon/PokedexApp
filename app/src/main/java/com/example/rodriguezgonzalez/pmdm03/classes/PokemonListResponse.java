package com.example.rodriguezgonzalez.pmdm03.classes;


import java.util.ArrayList;

/**
 * Esta clase se utiliza para obtener los datos de la llamada a la API de Pokémon
 * en la que necesitamos todos los nombres de Pokémon almacenados.
 */
public class PokemonListResponse {
    //Lista de objetos de tipo Pokemon donde se reciben los datos de la API.
    private ArrayList<Pokemon> results;

    //Getter y Setter de la clase
    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
