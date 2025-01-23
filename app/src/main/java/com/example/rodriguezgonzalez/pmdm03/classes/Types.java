package com.example.rodriguezgonzalez.pmdm03.classes;


/**
 * Clase para poder obtener el campo "Types" desde la clase
 * Pokemon al hacer la llamada a la API con el nombre del Pokémon.
 */
public class Types {
    //Variable de clase para acceder al campo anidado de Types en la API
    private Type type;

    //Constructor sin parámetros
    public Types() {
    }

    //Getter y Setter
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
