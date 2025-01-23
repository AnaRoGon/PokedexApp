package com.example.rodriguezgonzalez.pmdm03.interfaces;

import com.example.rodriguezgonzalez.pmdm03.classes.Pokemon;
import com.example.rodriguezgonzalez.pmdm03.classes.PokemonListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Interfaz para manejar las solicitudes a la API de Pokémon.
 * Define los métodos necesarios para realizar llamadas HTTP a los endpoints de la API.
 * Utiliza Retrofit para gestionar las solicitudes y respuestas.
 */
public interface MyApiService {

    /**
     * Realiza una solicitud GET al endpoint "pokemon" para obtener una lista de Pokémon.
     * Implementa el método getPokemonList que nos permite
     * definir el rango de Pokémon que queremos obtener de la consulta.
     *
     * @param limit  El número máximo de resultados que se deben devolver.
     * @param offset El comienzo de la lista.
     * @return Devuelve una lista de objetos de tipo PokemonListResponse,
     * el cual contiene la lista de nombres de los Pokémon.
     */
    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

    /**
     * Realiza una solicitud GET al endpoint "pokemon/{name}" para obtener los detalles
     * de un Pokémon específico.
     * Implementa el método getPokemonDetails que nos permite
     * obtener los detalles de un Pokémon en concreto.
     *
     * @param name El nombre del Pokémon del que se quiere hacer la consulta.
     * @return Devuelve un objeto de tipo Pokemon con los detalles del Pokémon.
     */
    @GET("pokemon/{name}")
    Call<Pokemon> getPokemonDetails(@Path("name") String name);

}
