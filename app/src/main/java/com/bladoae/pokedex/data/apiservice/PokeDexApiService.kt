package com.bladoae.pokedex.data.apiservice

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.requestmanager.model.EffectDto
import com.bladoae.pokedex.requestmanager.model.PokemonDto
import com.bladoae.pokedex.requestmanager.model.PokemonListResponse
import com.bladoae.pokedex.requestmanager.model.encounter.EncounterDto
import kotlinx.coroutines.flow.Flow

interface PokeDexApiService {
    suspend fun getPokemonList(limit: Int, offSet: Int) : Flow<Resource<PokemonListResponse>>
    suspend fun getPokemonDetail(name: String) : Flow<Resource<PokemonDto>>
    suspend fun getEffects(id: Int): Flow<Resource<EffectDto>>
    suspend fun getEncounters(id: Int): Flow<Resource<List<EncounterDto>>>
}