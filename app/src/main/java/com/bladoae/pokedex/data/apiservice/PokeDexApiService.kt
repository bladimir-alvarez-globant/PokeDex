package com.bladoae.pokedex.data.apiservice

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.requestmanager.model.PokemonListResponse
import com.bladoae.pokedex.requestmanager.model.PokemonDto
import kotlinx.coroutines.flow.Flow

interface PokeDexApiService {
    suspend fun getPokemonList(limit: Int, offSet: Int) : Flow<Resource<PokemonListResponse>>
    suspend fun getPokemonDetail(name: String) : Flow<Resource<PokemonDto>>
}