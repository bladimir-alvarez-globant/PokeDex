package com.bladoae.pokedex.requestmanager

import com.bladoae.pokedex.requestmanager.model.detail.EffectDto
import com.bladoae.pokedex.requestmanager.model.pokemon.PokemonListResponse
import com.bladoae.pokedex.requestmanager.model.pokemon.PokemonDto
import com.bladoae.pokedex.requestmanager.model.encounter.EncounterDto
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

    @GET("ability/{id}")
    suspend fun getEffects(@Path("id") id: Int): EffectDto

    @GET("pokemon/{id}/encounters")
    suspend fun getEncounters(@Path("id") id: Int): List<EncounterDto>
}