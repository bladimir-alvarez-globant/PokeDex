package com.bladoae.pokedex.requestmanager

import com.bladoae.pokedex.requestmanager.model.PokemonListResponse
import com.bladoae.pokedex.requestmanager.model.PokemonDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offSet") offSet: Int
    ) : PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDto
}