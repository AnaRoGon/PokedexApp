package com.example.rodriguezgonzalez.pmdm03.fragments;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodriguezgonzalez.pmdm03.R;
import com.example.rodriguezgonzalez.pmdm03.classes.CapturedPokemon;
import com.example.rodriguezgonzalez.pmdm03.classes.CapturedPokemonAdapter;

import com.example.rodriguezgonzalez.pmdm03.databinding.FragmentCapturedPokemonBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase implementa la lógica del fragment que contiene
 * la lista de Pokémon capturados que se cargará en
 * un recyclerView.
 */
public class FragmentCapturedPokemon extends Fragment {
    //Variables de clase
    private FragmentCapturedPokemonBinding binding;
    private ArrayList<CapturedPokemon> pokemons;
    private CapturedPokemonAdapter adapter; //Necesitamos el adaptador para el recycler view

    /**
     * Método que se ejecuta al crear la vista del fragmento
     * Crea y devuelve la vista asociada a este fragmento.
     *
     * @param inflater           Objeto utilizado para inflar la vista del fragmento.
     * @param container          Contenedor al que se añadirá la vista del fragmento.
     * @param savedInstanceState Si no es null, contiene el estado previamente guardado del fragmento.
     * @return La raíz de la vista inflada para este fragmento, o null si no se puede crear.
     */
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCapturedPokemonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Método llamado después de que la vista del fragmento ha sido creada.
     * Aquí se inicializan los componentes de la interfaz de usuario,
     * se cargan los datos de la lista de Pokémon capturados y se configura el RecyclerView.
     *
     * @param view               Vista asociada a este fragmento.
     * @param savedInstanceState Si no es null, contiene el estado previamente guardado del fragmento.
     */
    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Inicializa la lista de pokemons capturados
        LoadPokemon();
        //Configuramos el adaptador del recycler view
        adapter = new CapturedPokemonAdapter(pokemons, getActivity());
        binding.capturedPokemonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.capturedPokemonRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //Leemos el estado de la preferencia de la opción de eliminar Pokémon.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean deletePokemonEnabled = sharedPreferences.getBoolean("deletePokemonEnabled", false);
        if (deletePokemonEnabled) { //Si está habilitada se activa el swipe del recycler view
            swipeAbailable();
        }
    }

    /**
     * Método privado de la clase para habilitar el swipe del recycler view
     */
    private void swipeAbailable() {
        //Configuramos el ItemTouchHelper para manejar el swipe del recycler view
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; //No se soporta el movimiento de arrastre
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Mostramos al usuario un mensaje de confirmación para eliminar el pokemon del recyclerView
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Delete Pokemon");
                builder.setMessage(R.string.release_pokemon_option);

                //Nos aseguramos de que el usuario quiere borrar el pokemon arrastrado
                //con un mensaje de confirmación
                builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                    //Si la respuesta es positiva eliminamos el Pokemon de la base de datos
                    //Obtenemos la posición del elemento deslizado
                    int position = viewHolder.getAdapterPosition();

                    //Obtenemos el Pokémon capturado seleccionado
                    CapturedPokemon pokemon = pokemons.get(position);

                    //Referencia a la base de datos
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    //Borramos el documento en Firestore con el ID del Pokémon.
                    db.collection("capturedPokemons").document(pokemon.getDocumentId())
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                Log.d(TAG, getString(R.string.pokemon_removed_from_firestore) + pokemon.getName());
                                //Se elimina el Pokémon de la lista local y notifica al adaptador
                                pokemons.remove(position);
                                adapter.notifyItemRemoved(position);
                            })
                            .addOnFailureListener(e -> {
                                Log.w(TAG, getString(R.string.error_deleting_the_pokemon), e);
                                //Si falla, restaura el elemento deslizado
                                adapter.notifyItemChanged(position);
                            });
                });

                //Opción de cancelar eliminación
                builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
                    //Si se selecciona cancelar, restauramos el elemento deslizado al RecyclerView
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                });
                builder.show();
            }
        };

        //Se tiene que adjuntar el ItemTouchHelper al RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.capturedPokemonRecyclerView);
    }

    /**
     * Método para cargar los datos de los Pokémon capturados desde la base de datos de Firestore.
     */
    private void LoadPokemon() {
        //Se crea una instancia de FirebaseFirestore con la que nos conectamos a la base de datos
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Inicializamos la lista de Pokémon capturados
        pokemons = new ArrayList<>();
        //Recuperamos todos los documentos almacenados de la base de datos de Pokémon capturados
        db.collection("capturedPokemons")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //Si el acceso tiene éxito obtenemos los datos de cada Pokémon
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String name = document.getString("name");
                                int order = document.getLong("order").intValue();
                                String sprites = document.getString("sprites");
                                double height = document.getDouble("height");
                                double weight = document.getDouble("weight");
                                List<String> types = (List<String>) document.get("types");
                                List<String> translatedTypes = new ArrayList<>();
                                for (String type : types) {
                                    translatedTypes.add(mapPokemonType(type));//Aqui mapeamos el nombre del Pokemon según el idioma de la app
                                }
                                //Creamos una nueva instancia de Pokemon con los datos recuperados
                                CapturedPokemon pokemon = new CapturedPokemon(name, order, height, weight, sprites, translatedTypes, document.getId());
                                //Añadimos el pokemon a la lista de pokemons
                                pokemons.add(pokemon);
                                //Notificamos al adaptador que los datos han cambiado
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, getString(R.string.error_reading_the_document), task.getException());
                        }
                    }
                });
    }


    /**
     * Función para mapear los tipos de Pokémon que obtenemos de la base de datos.
     * Se le pasa el tipo de Pokémon obtenido de la base de datos y devuelve el tipo
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
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.captured_pokemon);
        }
    }
}