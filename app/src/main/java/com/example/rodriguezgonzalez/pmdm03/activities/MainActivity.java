package com.example.rodriguezgonzalez.pmdm03.activities;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.example.rodriguezgonzalez.pmdm03.classes.CapturedPokemon;
import com.example.rodriguezgonzalez.pmdm03.R;
import com.example.rodriguezgonzalez.pmdm03.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Esta clase implementa la lógica principal de la aplicación.
 * Se trata de la Activity principal que se nos abrirá con la sesión iniciada y contendrá
 * los fragmentos de la aplicación.
 * DAM. Curso 2024-2025
 * Módulo: PMDM
 * Unidad 3: Gestión de datos.
 * Título de la tarea: Aplicación Android de Pokémon.
 *
 * @author Ana Rodríguez González
 * @version 1.0 Fecha: 22-01-2025
 */
public class MainActivity extends AppCompatActivity {
    //Variables de la actividad
    private ActivityMainBinding binding; //Bindeo de la vista
    private Retrofit retrofit; //Con la clase Retrofit gestionaremos las llamadas a la API
    private NavController navController; //Para manejar la navegación entre fragmentos

    /**
     * Método llamado al crear la actividad.
     * Configura los elementos iniciales de la actividad, como la actualización del idioma,
     * la navegación de la action bar, del menú contextual y establece el contenido de la vista.
     *
     * @param savedInstanceState Si no es null, contiene el estado previamente guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtenemos el idioma por defecto desde SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Hasta que se produzca alguna modificación de idioma por defecto será el inglés
        String selectedLanguage = sharedPreferences.getString("preferenceLanguage", "en");
        // Aplica el cambio de idioma si es necesario
        updateLanguage(selectedLanguage);

        //Inflamos la actividad principal
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Consiguración para la toolbar
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        //Configuración para el NavController con el BottomNavigationView
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        //Conectar el BottomNavigationView con el NavController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        //Configuración para la flecha de retroceso
        NavigationUI.setupActionBarWithNavController(this, navController);

        //Configuración para Retrofit con la URL base de la API
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Función para que no aparezca la flecha atrás en los fragment que no nos interesa
        initializeAppBAr();
    }

    /**
     * Método para manejar la navegación hacia atrás.     *
     *
     * @return true si se ha realizado la navegación hacia atrás, false en caso contrario.
     */
    @Override
    public boolean onSupportNavigateUp() {
        //Utilizamos el método nagigateUp del NavCOntroller para incluir la flecha atrás
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    /**
     * Método para que las pantallas que definamos como principales no nos las meta en la pila
     * y no aparezca la flecha atrás en la toolbar.
     */
    private void initializeAppBAr() {
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragmentCapturedPokemon,
                R.id.fragmentPokedex,
                R.id.settingsFragment)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }


    /**
     * Método para manejar cuando un elemento del recyclerView del fragment_captured_pokemon es seleccionado.
     * Cuando un pokemon capturado es seleccionado se navega al fragment_pokemon_details.
     *
     * @param capturedPokemon Pokemon capturado seleccionado.
     * @param view            Vista del pokemon seleccionado.
     */
    public void getCapturedPokemonDetails(CapturedPokemon capturedPokemon, View view) {
        Bundle bundle = new Bundle();
        bundle.putString("name", capturedPokemon.getName());
        bundle.putInt("order", capturedPokemon.getOrder());
        bundle.putString("sprites", capturedPokemon.getSpriteUrl());
        bundle.putDouble("height", capturedPokemon.getHeight());
        bundle.putDouble("weight", capturedPokemon.getWeight());
        // Añadimos los tipos como un arrayList
        List<String> typeNames = new ArrayList<>();
        if (capturedPokemon.getTypes() != null) {
            for (String type : capturedPokemon.getTypes()) {
                typeNames.add(type);
            }
        }
        bundle.putStringArrayList("types", (ArrayList<String>) typeNames);
        //Navegamos al fragmento de pokemon details con el bundle
        Navigation.findNavController(view).navigate(R.id.pokemonDetailsFragment, bundle);
    }

    /**
     * Método para actualizar la configuración de idioma de la aplicación
     * una vez recuperado el código de la preferencia de idioma almacenador
     * en SharedPreferences.
     *
     * @param languageCode Código del idioma seleccionado.
     */
    private void updateLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
