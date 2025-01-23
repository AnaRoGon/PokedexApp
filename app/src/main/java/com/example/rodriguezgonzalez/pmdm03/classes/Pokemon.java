package com.example.rodriguezgonzalez.pmdm03.classes;

import java.util.List;

/**
 * Esta clase se utiliza para obtener los datos de la API de pokemon y añadirlos a la pokedex.
 * También la utilizamos para almacenar los datos de un Pokemon en la base de datos de Firebase,
 * cuando el usuario lo captura.
 */
public class Pokemon {
    //Variables de clase
    private String name;
    private String url;
    private double height;
    private double weight;
    private int order;
    //Variable para obtener la imagen del pokemon en la llamada a la API.
    //con el nombre del Pokemon
    private Sprites sprites;
    //Variable de tipo Types para obtener los tipos de pokemon en la llamada a la API.
    private List<Types> types;
    //Esta variable nos sirve para identificar los Pokemon que han sido capturados y actualizar la vista del recyclerView.
    private boolean isCaptured;

    // Constructor vacío de clase
    // Firebase lo utiliza para obtener la información anidada de la API
    public Pokemon() {
    }

    /**
     * Constructor para crear una instancia de la clase Pokemon con los
     * datos obtenidos.
     *
     * @param name      El nombre del Pokémon.
     * @param order     El orden del Pokémon en la Pokedex.
     * @param height    La altura del Pokémon en metros.
     * @param weight    El peso del Pokémon en kilogramos.
     * @param sprite    La URL de la imagen del Pokémon.
     * @param typeNames La lista de tipos del Pokémon.
     */
    public Pokemon(String name, int order, double height, double weight, Sprites sprite, List<String> typeNames) {
        this.name = name;
        this.order = order;
        this.height = height;
        this.weight = weight;
        this.sprites = sprite;
        this.isCaptured = false;
    }


    //Getters y Setters de la clase
    public String getName() {
        //Para obtener la primera letra del nombre en mayúscula
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //La altura de los pokemon la obtenemos en decímetros de la API, por lo que la pasamos a metros
    public double getHeight() {
        return height / 10.0;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    //El peso de los pokemons se obtiene en hectogramos, por lo que lo pasamos a kilos
    public double getWeight() {
        return weight / 10.0;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public List<Types> getTypes() {
        return types;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }

    /**
     * Método que nos ayuda a obtener el numero de pokemon
     * para añadir la imagenes a la pokedex a partir de una URL de nuestra elección
     * que encaja con la que obtendremos en la API. Así mantenemos la estética de
     * nustra app.
     */
    public int getImageNumber() {
        int imageNumber;
        String[] urlPart = url.split("/");
        String numberPart = urlPart[urlPart.length - 1].split("\\.")[0];
        // Convertir el número limpio a entero
        imageNumber = Integer.parseInt(numberPart);
        return imageNumber;
    }

    /**
     * Método que nos ayuda a obtener la imagen de cada pokemon en base a su orden en la pokedex.
     */
    public String getImageUrl() {
        String url;
        //Tenemos que hacer una conversion a string para que no de error
        url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + getImageNumber() + ".png";
        return url;
    }

}
