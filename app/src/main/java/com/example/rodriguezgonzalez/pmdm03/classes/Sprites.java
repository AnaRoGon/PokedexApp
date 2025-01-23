package com.example.rodriguezgonzalez.pmdm03.classes;

/**
 * Esta clase se utiliza para poder obtener el campo que almacena la imagen del pokemon
 * en la clase Pokemon.
 */
public class Sprites {
    //Variable de clase
    private String front_default;

    //Constructor vacío de clase
    public Sprites() {
    }

    //Constructor que recibe un string con la url de la imagen
    public Sprites(String spriteUrl) {
    }

    //Getters y Setters de la variable que almacenará la url de la imagen
    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }
}


