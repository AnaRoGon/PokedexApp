package com.example.rodriguezgonzalez.pmdm03.classes;

import java.util.List;

/**
 * Esta clase separa la lógica para la obtención de los datos desde la API (clase Pokemon) y la base de datos.
 * En esta clase almacenamos los datos que obtenemos al conectarnos a la base de datos de Firebase.
 * Nos ayuda a gestionar los datos que se cargaran en el recyclerView del fragment_captured_pokemon.
 * y el pokemon_details_fragment.
 */
public class CapturedPokemon {
    //Variables de clase
    private String name;
    private int order;
    private double height; // Almacenado en metros directamente
    private double weight; // Almacenado en kilogramos directamente
    private String spriteUrl; // URL de la imagen
    private List<String> types; // Lista con los tipos del Pokémon
    private String documentId; // Variable para guardar el ID del documento

    // Constructor de clase vacío
    public CapturedPokemon() {
    }

    /**
     * Constructor para crear una instancia de la clase CapturedPokemon con los
     * datos proporcionados.
     *
     * @param name       El nombre del Pokémon.
     * @param order      El orden del Pokémon en la Pokedex.
     * @param height     La altura del Pokémon en metros.
     * @param weight     El peso del Pokémon en kilogramos.
     * @param spriteUrl  La URL de la imagen del Pokémon.
     * @param types      La lista de tipos del Pokémon.
     * @param documentId El ID del documento en la base de datos.
     */
    public CapturedPokemon(String name, int order, double height, double weight, String spriteUrl, List<String> types, String documentId) {
        this.name = name;
        this.order = order;
        this.height = height;
        this.weight = weight;
        this.spriteUrl = spriteUrl;
        this.types = types;
        this.documentId = documentId;
    }

    // Getters y setters de la clase
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getSpriteUrl() {
        return spriteUrl;
    }

    public void setSpriteUrl(String spriteUrl) {
        this.spriteUrl = spriteUrl;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getDocumentId() {
        return documentId;
    }
}

