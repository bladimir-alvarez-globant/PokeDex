package com.bladoae.pokedex.data.apiservice

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.requestmanager.model.PokeDexDto
import kotlinx.coroutines.flow.Flow

interface PokeDexApiService {
    suspend fun getPokemonList(limit: Int, offSet: Int) : Flow<Resource<PokeDexDto>>
}