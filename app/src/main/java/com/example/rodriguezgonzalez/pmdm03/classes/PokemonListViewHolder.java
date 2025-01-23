package com.example.rodriguezgonzalez.pmdm03.classes;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodriguezgonzalez.pmdm03.R;
import com.example.rodriguezgonzalez.pmdm03.databinding.PokedexCardviewBinding;
import com.squareup.picasso.Picasso;

/**
 * Esta clase sirve de contenedor
 * para guardar cada uno de los elementos visibles
 * de nuestra lista de Pokémon en la Pokedex.
 */
public class PokemonListViewHolder extends RecyclerView.ViewHolder {
    private final PokedexCardviewBinding binding;

    // Constructor para el bindeo de datos
    public PokemonListViewHolder(PokedexCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Método para vincular los datos del objeto Pokemon al ViewHolder.
     *
     * @param pokemon Instancia del objeto Pokemon que contiene los datos a vincular.
     */
    public void bind(Pokemon pokemon) {
        // Asignamos el nombre del Pokémon
        binding.pokemonName.setText(pokemon.getName());
        //Usamos la librería picasso para cargar las imágenes desde la URL
        Picasso.get()
                .load(pokemon.getImageUrl())// URL para la imagen
                .into(binding.pokemonImgview); // Establecemos la imagen cargada en el ImageView
        //Si el pokemon está capturado cambiamos el color del cardview
        if (pokemon.isCaptured()) {
            binding.pokemonCardView.setCardBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.PokemonRed));
            binding.pokemonCardView.setEnabled(false);
        } else { //Si deja de estar capturado se vuelve a su color original y se activa nuevamente
            binding.pokemonCardView.setCardBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.DirtyWhite));
            binding.pokemonCardView.setEnabled(true);
        }
        // Asegurar que los cambios se reflejen
        binding.executePendingBindings();
    }
}


