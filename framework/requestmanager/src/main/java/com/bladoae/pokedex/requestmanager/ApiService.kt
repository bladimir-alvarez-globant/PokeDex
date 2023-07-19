package com.bladoae.pokedex.requestmanager

import com.bladoae.pokedex.requestmanager.model.PokeDexDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("pokemon?limit={limit}&offset={offSet}")
    suspend fun getPokedex(
        @Path("limit") limit: Int,
        @Path("offSet") offSet: Int
    ) : PokeDexDto
}