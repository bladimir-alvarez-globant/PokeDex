package com.bladoae.pokedex.domain.usecase

import com.bladoae.pokedex.domain.model.pokemon.Pokemon
import kotlinx.coroutines.flow.Flow

interface GetPokemonByNameUseCase {
    suspend operator fun invoke(name: String): Flow<List<Pokemon?>?>
}