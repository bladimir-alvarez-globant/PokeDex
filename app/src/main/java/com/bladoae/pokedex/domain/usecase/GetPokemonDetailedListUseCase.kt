package com.bladoae.pokedex.domain.usecase

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.pokemon.Pokemon
import kotlinx.coroutines.flow.Flow

interface GetPokemonDetailedListUseCase {
    suspend operator fun invoke(): Flow<Resource<List<Pokemon?>?>>
}