package com.example.rodriguezgonzalez.pmdm03.classes;

/**
 * Subclase para poder obtener el campo "Type" anidado en "Types"
 * al hacer la llamada a la API con el nombre del Pokémon.
 */
public class Type {
    //Anidacion de Json de la API de types para obtener el nombre del tipo
    String name;

    //Constructor sin parámetros
    public Type() {
    }

    //Getter y Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
