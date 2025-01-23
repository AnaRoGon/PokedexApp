package com.example.rodriguezgonzalez.pmdm03.classes;

import android.text.TextUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rodriguezgonzalez.pmdm03.databinding.CapturedPokemonCardviewBinding;
import com.squareup.picasso.Picasso;

/**
 * Esta clase sirve de contenedor
 * para guardar cada uno de los elementos visibles
 * de nuestra lista de Pokémon capturados.
 */
public class CapturedPokemonViewHolder extends RecyclerView.ViewHolder {
    private final CapturedPokemonCardviewBinding binding;

    //Constructor para el bindeo de los datos
    public CapturedPokemonViewHolder(CapturedPokemonCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Método para vincular los datos del objeto CapturedPokemon al ViewHolder.
     *
     * @param capturedPokemon Instancia del objeto CapturedPokemon que contiene los datos a vincular.
     */
    public void bind(CapturedPokemon capturedPokemon) {
        //Cargamos la imagen del Pokémon
        Picasso.get()
                .load(capturedPokemon.getSpriteUrl())
                .into(binding.capturedPokemonImgview);
        //Asignamos el nombre del Pokémon
        binding.capturedPokemonName.setText(capturedPokemon.getName());
        //Combinamos los tipos en un solo String separado por comas
        String types = TextUtils.join(", ", capturedPokemon.getTypes());
        binding.capturedPokemonTypes.setText(types);
        //Nos aseguramos de que queden reflejados los cambios en la vista
        binding.executePendingBindings();
    }
}
