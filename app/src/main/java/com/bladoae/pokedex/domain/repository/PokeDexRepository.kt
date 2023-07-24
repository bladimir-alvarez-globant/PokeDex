package com.bladoae.pokedex.domain.repository

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.PokeDex
import com.bladoae.pokedex.requestmanager.model.PokeDexDto
import kotlinx.coroutines.flow.Flow

interface PokeDexRepository {
    suspend fun getPokemonList(limit: Int, offSet: Int) : Flow<Resource<PokeDex>>
}