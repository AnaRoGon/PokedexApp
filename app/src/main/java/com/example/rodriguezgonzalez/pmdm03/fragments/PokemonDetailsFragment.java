package com.example.rodriguezgonzalez.pmdm03.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodriguezgonzalez.pmdm03.R;
import com.example.rodriguezgonzalez.pmdm03.databinding.FragmentPokemonDetailsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase infla el diseño y muestra la información que contiene
 * el fragment de los detalles de un Pokémon capturado.
 */
public class PokemonDetailsFragment extends Fragment {
    //Variables
    private FragmentPokemonDetailsBinding binding;

    /**
     * Método que se ejecuta al crear la vista del fragmento.
     * Infla el diseño con un FragmentPokemonDetailsBinding.
     *
     * @param inflater           Objeto utilizado para inflar la vista del fragmento.
     * @param container          Contenedor al que se añadirá la vista del fragmento.
     * @param savedInstanceState Si no es null, contiene el estado previamente guardado del fragmento.
     * @return La raíz de la vista inflada para este fragmento, o null si no se puede crear.
     */
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Método llamado después de que la vista del fragmento ha sido creada.
     * Aquí se inicializan los componentes de la interfaz.
     * Se cargan los datos de los detalles del Pokémon.
     *
     * @param view               Vista asociada a este fragmento.
     * @param savedInstanceState Si no es null, contiene el estado previamente guardado del fragmento.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String name = getArguments().getString("name");
            int order = getArguments().getInt("order");
            String sprites = getArguments().getString("sprites");
            double height = getArguments().getDouble("height");
            double weight = getArguments().getDouble("weight");
            ArrayList<String> types = getArguments().getStringArrayList("types");
            //Asignamos la imagen
            Picasso.get()
                    .load(sprites)
                    .into(binding.pokemonDetailsImageView);

            //Asignamos los datos a los elementos
            binding.pokemonDetailsTextViewName.setText(name);
            binding.pokemonDetailsTextViewOrder.setText(getString(R.string.order_in_the_pokedex) + String.valueOf(order));
            binding.pokemonDetailsTextViewWeight.setText(String.valueOf(weight) + getString(R.string.cm));
            binding.pokemonDetailsTextViewHeight.setText(String.valueOf(height) + getString(R.string.kg));
            //Traducción de los tipos combinándolos en un solo String separado por comas
            List<String> translatedTypes = new ArrayList<>();
            for (String type : types) {
                translatedTypes.add(mapPokemonType(type));
            }
            String typesString = TextUtils.join(getString(R.string.comma), translatedTypes);
            binding.pokemonDetailsTextViewTypes.setText(getString(R.string.is_type) + typesString);
        }
    }

    /**
     * Función para mapear los tipos de Pokémon que obtenemos de la base de datos.
     * Se le pasa el tipo de Pokémon obtenido y devuelve el tipo traducido
     * según el idioma de la aplicación.
     *
     * @param type El tipo de Pokémon obtenido de la base de datos.
     */
    private String mapPokemonType(String type) {
        switch (type.toLowerCase()) {
            case "normal":
                return getString(R.string.type_normal);
            case "grass":
                return getString(R.string.type_grass);
            case "fire":
                return getString(R.string.type_fire);
            case "water":
                return getString(R.string.type_water);
            case "electric":
                return getString(R.string.type_electric);
            case "ice":
                return getString(R.string.type_ice);
            case "fighting":
                return getString(R.string.type_fighting);
            case "poison":
                return getString(R.string.type_poison);
            case "ground":
                return getString(R.string.type_ground);
            case "flying":
                return getString(R.string.type_flying);
            case "psychic":
                return getString(R.string.type_psychic);
            case "bug":
                return getString(R.string.type_bug);
            case "rock":
                return getString(R.string.type_rock);
            case "ghost":
                return getString(R.string.type_ghost);
            case "dragon":
                return getString(R.string.type_dragon);
            case "dark":
                return getString(R.string.type_dark);
            case "steel":
                return getString(R.string.type_steel);
            case "fairy":
                return getString(R.string.type_fairy);
            case "stellar":
                return getString(R.string.type_stellar);
            case "unknown":
                return getString(R.string.type_unknown);
            default:
                return type; //Si no se encuentra coincidencia, devuelve el tipo obtenido por defecto
        }
    }

    /**
     * Método que se ejecuta antes de que el fragento se haga visible al usuario.
     * En este caso, se utiliza para cambiar el título de la Action Bar.
     */
    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.pokemon_details);
        }
    }

}
