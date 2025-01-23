package com.example.rodriguezgonzalez.pmdm03.classes;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodriguezgonzalez.pmdm03.databinding.PokedexCardviewBinding;
import com.example.rodriguezgonzalez.pmdm03.fragments.FragmentPokedex;

import java.util.ArrayList;

/**
 * Esta clase actúa como adaptador para la implementación de nuestro RecyclerView del fragment_pokedex.
 * Nos ayuda a conectar nuestros datos (Pokémon) con la vista (CardView).
 */
public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListViewHolder> {
    // Variables de clase
    private ArrayList<Pokemon> pokemonList; // Lista de datos a mostrar
    private final FragmentPokedex fragmentPokedex; // Referencia al fragmento Pokedex

    //Constructor que recibe una lista de Pokémon y el fragmento Pokedex.
    public PokemonListAdapter(ArrayList<Pokemon> pokemonList, FragmentPokedex fragmentPokedex) {
        this.fragmentPokedex = fragmentPokedex;
        this.pokemonList = pokemonList;  // Usar el pokemonList proporcionado
    }

    /**
     * Crea un nuevo objeto de tipo PokemonListViewHolder
     * y lo infla basándose en el diseño del xml captured_pokemon_cardview.
     * Este método es invocado por el RecyclerView.Adapter cuando necesita crear una nueva vista
     * para mostrar un elemento en el RecyclerView.
     *
     * @param parent   El ViewGroup que será el contenedor de la vista creada.
     * @param viewType Un entero que representa el tipo de vista.
     * @return Un nuevo objeto PokemonListViewHolder, que contiene la vista inflada.
     */
    @NonNull
    @Override
    public PokemonListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PokedexCardviewBinding binding = PokedexCardviewBinding.inflate(
                LayoutInflater.from(fragmentPokedex.getContext()), parent, false);
        return new PokemonListViewHolder(binding);
    }

    /**
     * Método encargado de vincular los datos del Pokémon en una posición específica con el `ViewHolder`.
     * Este método es invocado por el RecyclerView.Adapter para actualizar la vista de un elemento en la lista
     * en función de la posición del mismo.
     *
     * @param holder   El PokemonListViewHolder que contiene las vistas del Pokémon a mostrar.
     * @param position La posición del elemento dentro del conjunto de datos que debe ser mostrada.
     */
    @Override
    public void onBindViewHolder(@NonNull PokemonListViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.bind(pokemon);
        //Aqui podemos manejar el evento de clicks en el recycler view
        holder.itemView.setOnClickListener(view -> pokemonClicked(pokemon));
    }

    /**
     * Método que devuelve el total de elementos en la lista.
     *
     * @return el número total de elementos en la lista.
     */
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    /**
     * Evento de clic que se ejecuta cuando se hace clic en un elemento de la lista.
     * Este evento se maneja en el FragmentPokedex.
     */
    private void pokemonClicked(Pokemon pokemon) {
        (fragmentPokedex).pokemonWasCaptured(pokemon);

    }
}

