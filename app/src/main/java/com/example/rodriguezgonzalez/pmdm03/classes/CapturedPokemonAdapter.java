package com.example.rodriguezgonzalez.pmdm03.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodriguezgonzalez.pmdm03.activities.MainActivity;
import com.example.rodriguezgonzalez.pmdm03.databinding.CapturedPokemonCardviewBinding;

import java.util.ArrayList;

/**
 * Esta clase actúa como adaptador para la implementación de nuestro RecyclerView
 * de Pokémon capturados.
 * Nos ayuda a conectar nuestros datos (Pokémon capturados) con la vista (CardView)
 */
public class CapturedPokemonAdapter extends RecyclerView.Adapter<CapturedPokemonViewHolder> {
    //Variables de la clase
    private ArrayList<CapturedPokemon> capturedPokemonList; // Lista de datos a mostrar
    private Context context;

    //Constructor
    public CapturedPokemonAdapter(ArrayList<CapturedPokemon> capturedPokemonList, Context context) {
        this.context = context;
        this.capturedPokemonList = capturedPokemonList;
    }

    /**
     * Crea un nuevo objeto de tipo CapturedPokemonViewHolder
     * y lo infla en base al diseño del xml captured_pokemon_cardview.
     * Este método es invocado por el RecyclerView.Adapter cuando necesita crear una nueva vista
     * para mostrar un elemento en el RecyclerView.
     *
     * @param parent   El ViewGroup que será el contenedor de la vista creada.
     * @param viewType Un entero que representa el tipo de vista.
     * @return Un nuevo objeto CapturedPokemonViewHolder, que contiene la vista inflada.
     */
    @NonNull
    @Override
    public CapturedPokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CapturedPokemonCardviewBinding binding = CapturedPokemonCardviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CapturedPokemonViewHolder(binding);
    }

    /**
     * Método encargado de vincular los datos del Pokémon en una posición específica con el `ViewHolder`.
     * Este método es invocado por el RecyclerView.Adapter para actualizar la vista de un elemento en la lista
     * en función de la posición del mismo.
     *
     * @param holder   El CapturedPokemonViewHolder que contiene las vistas del Pokémon a mostrar.
     * @param position La posición del elemento dentro del conjunto de datos que debe ser mostrada.
     */
    @Override
    public void onBindViewHolder(@NonNull CapturedPokemonViewHolder holder, int position) {
        CapturedPokemon capturedPokemon = capturedPokemonList.get(position);
        holder.bind(capturedPokemon);
        //Acciones al hacer clic en un pokemon de los pokemon capturados
        holder.itemView.setOnClickListener(view -> getCapturedPokemonDetails(capturedPokemon, view));
    }

    /**
     * Evento de clic que se ejecuta cuando se hace clic en un elemento de la lista.
     * Este evento se maneja en el Main Activity.
     */
    private void getCapturedPokemonDetails(CapturedPokemon capturedPokemon, View view) {
        ((MainActivity) context).getCapturedPokemonDetails(capturedPokemon, view);
    }


    /**
     * Método que devuelve el total de elementos en la lista.
     *
     * @return el número total de elementos en la lista.
     */
    @Override
    public int getItemCount() {
        return capturedPokemonList.size();
    }
}
