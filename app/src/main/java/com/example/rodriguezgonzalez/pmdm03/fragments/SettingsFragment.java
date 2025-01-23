package com.example.rodriguezgonzalez.pmdm03.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.rodriguezgonzalez.pmdm03.activities.LoginActivity;
import com.example.rodriguezgonzalez.pmdm03.R;
import com.firebase.ui.auth.AuthUI;

import java.util.Locale;


/**
 * Esta clase infla el diseño y muestra la información que contiene
 * el fragment de los ajustes de la aplicación.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    /**
     * Método que configura las preferencias del fragmento al cargarlo.
     * Infla las preferencias desde el archivo XML y define los listeners para manejar
     * las acciones en las opciones disponibles.
     *
     * @param savedInstanceState Estado previamente guardado, si existe.
     * @param rootKey            Clave raíz de las preferencias (puede ser null).
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        //Configuración del listener para cerrar sesión
        Preference logOutPreference = findPreference("logout");
        if (logOutPreference != null) {
            logOutPreference.setOnPreferenceClickListener(preference -> {
                logOut();
                return true;
            });
        }

        //Configuración del listener para mostrar información "Acerca de"
        Preference aboutPreference = findPreference("about");
        if (aboutPreference != null) {
            aboutPreference.setOnPreferenceClickListener(preference -> {
                aboutMessage();
                return true;
            });
        }

        //Configuración del listener para habilitar o deshabilitar la opción "Eliminar Pokémon"
        Preference deletePokemonPreference = findPreference("releasePokemon");
        if (deletePokemonPreference != null) {
            deletePokemonPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                //Obtenemos informacion de si el botón está activado o no
                boolean isChecked = (boolean) newValue;
                //Guardamos el estado en SharedPreferences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                sharedPreferences.edit().putBoolean("deletePokemonEnabled", isChecked).apply();
                return true;
            });
        }

        //Configuración del listener para cambiar el idioma
        Preference languagePreference = findPreference("preferenceLanguage");
        if (languagePreference != null) {
            languagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String selectedLanguage = (String) newValue; // "es" o "en"
                changeLanguage(selectedLanguage); //Método para cambiar el idioma
                return true;
            });
        }
    }

    /**
     * Método que cambia el idioma de la aplicación.
     *
     * @param languageCode Código del idioma ("en" o "es").
     */
    private void changeLanguage(String languageCode) {
        //Configuración del idioma en las preferencias
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.edit().putString("appLanguage", languageCode).apply();

        //Cambiamos el idioma configurado
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        //Recrea la actividad para aplicar los cambios
        requireActivity().recreate();
    }

    /**
     * Método que muestra un cuadro de diálogo "Acerca de" con información de la aplicación.
     */
    private void aboutMessage() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.about_app)
                .setMessage(R.string.app_info_autor_version)
                .setIcon(R.drawable.pkchu_con)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    /**
     * Método que cierra la sesión del usuario actual utilizando Firebase Authentication.
     * Redirige al usuario a la pantalla de inicio de sesión.
     */
    private void logOut() {
        // Cerrar sesión con Firebase
        AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener(task -> {
                    // Redirigir a la pantalla de login o actividad principal
                    goToLogin();
                });
    }

    /**
     * Método para redirigir al usuario a la actividad de inicio de sesión.
     * Finaliza la actividad actual para evitar que el usuario regrese a ella.
     */
    private void goToLogin() {
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    /**
     * Método que se ejecuta antes de que el fragento se haga visible al usuario.
     * En este caso, se utiliza para cambiar el título de la Action Bar.
     */
    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.settings);
        }
    }
}