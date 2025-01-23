package com.example.rodriguezgonzalez.pmdm03.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rodriguezgonzalez.pmdm03.R;
import com.example.rodriguezgonzalez.pmdm03.classes.Types;
import com.example.rodriguezgonzalez.pmdm03.interfaces.MyApiService;
import com.example.rodriguezgonzalez.pmdm03.classes.Pokemon;
import com.example.rodriguezgonzalez.pmdm03.classes.PokemonListAdapter;
import com.example.rodriguezgonzalez.pmdm03.classes.PokemonListResponse;
import com.example.rodriguezgonzalez.pmdm03.databinding.FragmentPokedexBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Esta clase implementa la lógica del fragment que contiene
 * la lista de Pokémon en la Pokédex que se cargará en
 * un recyclerView.
 */
public class FragmentPokedex extends Fragment {
    //Variables de clase
    private FragmentPokedexBinding binding;
    private Retrofit retrofit; //Con retrofit gestionamos las llamadas a la API
    private PokemonListAdapter pokemonListAdapter; //Adaptador el recycler view
    private ArrayList<Pokemon> pokemonList;


    //Método que se ejecuta al crear el fragmento por primera vez
    //Configura el Retrofit con la URL base de la API
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Configuramos Retrofit con la URL base de la API
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    /**
     * Método que se ejecuta al crear la vista del fragmento.
     * Infla el diseño con un FragmentPokedexBinding.
     *
     * @param inflater           Objeto utilizado para inflar la vista del fragmento.
     * @param container          Contenedor al que se añadirá la vista del fragmento.
     * @param savedInstanceState Si no es null, contiene el estado previamente guardado del fragmento.
     * @return La raíz de la vista inflada para este fragmento, o null si no se puede crear.
     */
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el layout del fragmento
        binding = FragmentPokedexBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Método llamado después de que la vista del fragmento ha sido creada.
     * Aquí se inicializan los componentes de la interfaz.
     * Se cargan los datos de la lista de Pokémon en la Pokédex y se configura el RecyclerView.
     *
     * @param view               Vista asociada a este fragmento.
     * @param savedInstanceState Si no es null, contiene el estado previamente guardado del fragmento.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Variables
        super.onViewCreated(view, savedInstanceState);
        //Inicializamos el RecyclerView y el adaptador
        pokemonList = new ArrayList<>();
        pokemonListAdapter = new PokemonListAdapter(pokemonList, this);
        binding.pokemonRecyclerView.setAdapter(pokemonListAdapter);
        //Vamos a hacer que los elementos se vean en un grid layout para que puedan aparecer dos Pokémon por fila
        binding.pokemonRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        //Cargamos los datos iniciales desde la API
        loadPokedex();
    }


    /**
     * Método que se ejecuta antes de que el fragento se haga visible al usuario.
     * En este caso, se utiliza para cambiar el título de la Action Bar.
     */
    private void loadPokedex() {
        //Creamos el servicio de la API
        MyApiService service = retrofit.create(MyApiService.class);

        //Solicitamos los Pokémon del 0 al 150
        Call<PokemonListResponse> pokemonCallResponse = service.getPokemonList(150, 0);

        pokemonCallResponse.enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                //Si la respuesta es positiva obtenemos la lista de Pokémon y la añadimos al adaptador
                if (response.isSuccessful() && response.body() != null) {
                    pokemonList.clear();
                    pokemonList.addAll(response.body().getResults());
                    //Leemos los Pokemon cargados y los comparados con los que ya tenemos en la base de datos
                    loadCapturedPokemon();
                } else {
                    //Si la respuesta no es positiva mostramos un mensaje de error
                    Toast.makeText(requireContext(), R.string.error_retrieving_the_data, Toast.LENGTH_SHORT).show();
                }
            }

            //Si falla la llamada mostramos un mensaje de error
            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                Toast.makeText(requireContext(), R.string.error_in_the_api_call, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método que se ejecuta antes de que el fragento se haga visible al usuario.
     * En este caso, se utiliza para cambiar el título de la Action Bar.
     */
    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.pokedex);
        }
    }


    /**
     * Método con el que leemos los Pokémon que tenemos en la base de datos al iniciar la aplicación.
     * Si el Pokémon ha sido capturado se manda el valor true a la variable isCaptured.
     */
    private void loadCapturedPokemon() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("capturedPokemons")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //Creamos un set para almacenar los nombres de Pokémon capturados
                        Set<String> capturedPokemonNames = new HashSet<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            capturedPokemonNames.add(name);
                        }
                        //Recorremos la lista de pokemon y comparamos el nombre con el set de nombres
                        for (Pokemon pokemon : pokemonList) {
                            //Si el pokemon está en la lista se le da el valor true a la variable
                            //"isCaptured" del pokemon
                            pokemon.setCaptured(capturedPokemonNames.contains(pokemon.getName()));
                        }
                        //Notificamos al adaptador de los cambios
                        pokemonListAdapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, getString(R.string.error_reading_the_database), task.getException());
                    }
                });
    }


    /**
     * Método que se ejecuta cuando se hace clic sobre alguno de los Pokemon de la pokedex
     * Obtiene los datos de ese pokemon en base a su nombre con una nueva llamada a la API
     * desde la que se solicitan los datos del pokemon y se almacenan en la base de datos de firebase.
     *
     * @param pokemon El pokemon que se ha pulsado.
     */
    public void pokemonWasCaptured(Pokemon pokemon) {
        MyApiService service = retrofit.create(MyApiService.class);
        //Llamada a la API para obtener los detalles del Pokémon capturado
        //Como el método de "getName()" de la clase Pokemon hace que la primera letra se ponga en mayúscula
        //Aqui debemos de introducir el nombre del pokemon en minúsculas para que la llamada
        //a la API no nos dé fallos
        Call<Pokemon> pokemonDetails = service.getPokemonDetails(pokemon.getName().toLowerCase());

        pokemonDetails.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //Obtenemos el Pokémon con los detalles que necesitamos
                    Pokemon capturedPokemon = response.body();

                    //Se pasan los datos a Firebase y se guardan en la base de datos
                    savePokemonToFirebase(capturedPokemon);

                    // Actualiza el estado del Pokémon a capturado para actualizar la vista
                    int position = pokemonList.indexOf(pokemon);
                    if (position != -1) {
                        pokemonList.get(position).setCaptured(true); // Cambia el estado a capturado
                        pokemonListAdapter.notifyItemChanged(position); // Notifica al adaptador
                    }
                    //Informamos al usuario de que se ha capturado el pokemon
                    Toast.makeText(requireContext(), getString(R.string.excl) + capturedPokemon.getName() + getString(R.string.captured), Toast.LENGTH_SHORT).show();
                } else {
                    //Indicamos al usuario que se ha producido un error al capturar el Pokémon
                    Toast.makeText(requireContext(), R.string.error_capturing_the_pokemon, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                //Se manda un mensaje indicando que ha habido un error de acceso a la API
                Toast.makeText(requireContext(), R.string.connection_error_capturing_the_pokemon, Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * Método que nos permite almacenar un pokemon en la base de datos de firebase
     * tras la llamada a la información de la API.
     *
     * @param pokemon El pokemon que se quiere almacenar.
     */
    private void savePokemonToFirebase(Pokemon pokemon) {
        //Conectamos con la base de datos de Firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> pokemonData = new HashMap<>();
        //Obtenemos los datos que necesitamos del pokemon
        pokemonData.put("name", pokemon.getName()); //Nombre
        pokemonData.put("order", pokemon.getOrder()); //índice en la Pokedex
        pokemonData.put("sprites", pokemon.getSprites().getFront_default()); //URL de la imagen
        pokemonData.put("height", pokemon.getHeight()); //ALtura
        pokemonData.put("weight", pokemon.getWeight()); //Peso

        //Comprobamos si el pokemon tiene varios tipos
        List<String> typeNames = new ArrayList<>();
        if (pokemon.getTypes() != null) {
            for (Types type : pokemon.getTypes()) {
                typeNames.add(type.getType().getName());
            }
        }
        //Los añadimos al mapa
        pokemonData.put("types", typeNames);

        //Guardamos el mapa en la colección "capturedPokemons"
        db.collection("capturedPokemons")
                .add(pokemonData) //Utiliza el método add() para que Firestore genere el ID automáticamente
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    //Si va bien, se notifica al usuario de que el Pokemon se ha guardado
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), R.string.pokemon_saved_to_the_database, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, getString(R.string.saved_with_id) + documentReference.getId() + getString(R.string.reverse_exc));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    //Si hay ido mal se notifica con un mensaje al usuario
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), R.string.error_saving_pok, Toast.LENGTH_SHORT).show();
                        Log.w(TAG, getString(R.string.error_saving_to_the_database), e);
                    }
                });
    }
}
