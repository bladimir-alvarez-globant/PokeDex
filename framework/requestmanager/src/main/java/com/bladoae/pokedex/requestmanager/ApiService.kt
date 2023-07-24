package com.bladoae.pokedex.requestmanager

import com.bladoae.pokedex.requestmanager.model.PokeDexDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offSet") offSet: Int
    ) : PokeDexDto
}