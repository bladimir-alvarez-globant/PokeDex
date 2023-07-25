package com.bladoae.pokedex.domain.repository

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokeDexRepository {
    suspend fun getPokemonList(limit: Int, offSet: Int) : Flow<Resource<List<Pokemon>>>

    suspend fun getPokemonDetail(name:String) : Flow<Resource<Pokemon>>
}