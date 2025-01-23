package com.example.rodriguezgonzalez.pmdm03.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rodriguezgonzalez.pmdm03.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/**
 * Esta clase implementa la lógica de la pantalla de inicio o Login de la aplicación,
 * que se realizará mediante la Autenticación con Firebase.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Método llamado al crear la actividad.
     * Comprueba si hay un usuario iniciado en la app con anterioridad y en función del resultado
     * redirecciona a la actividad principal o a la pantalla de inicio de sesión.
     *
     * @param savedInstanceState Si no es null, contiene el estado previamente guardado de la actividad.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Debemos verificar si el usuario está autentificado
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            goToMainActivity();
        } else {
            //Iniciamos el método para iniciar sesión
            startSignIn();
        }

    }

    /**
     * Método para ir a la actividad principal.
     */
    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Método para iniciar sesión
     */
    private void startSignIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.pkchu_log_pkmon) // Establecemos el logo de la app
                .setTheme(R.style.AuthUI_CustomTheme) // Establecemos el tema
                .build();
        signInLauncher.launch(signInIntent);
    }

    /**
     * Método para manejar el resultado la autenticación con Firebase Authentication
     * Recibe un objeto FirebaseAuthUIAuthenticationResult y lo procesa en onSignInResult.
     */
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result); //Mandamos el resultado al método para manejarlo
                }
            }
    );

    /**
     * Método que, en función del resultado de la autenticación obtiene el usuario de Firebase o
     * muestra un mensaje toast avisando de que la conexión ha fallado.
     */
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Si se ha podido iniciar sesión correctamente almacenamos la instancia del usuario
            // y vamos a la actividad principal
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            goToMainActivity();

        } else {
            //Si no se ha podido iniciar sesión se muestra un mensaje toast avisando al usuario
            Toast.makeText(this, R.string.sign_in_declined, Toast.LENGTH_SHORT).show();
        }
    }
}
