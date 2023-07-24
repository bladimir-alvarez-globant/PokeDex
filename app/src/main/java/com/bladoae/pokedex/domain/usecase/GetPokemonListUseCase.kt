package com.bladoae.pokedex.domain.usecase

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.PokeDex
import kotlinx.coroutines.flow.Flow

interface GetPokemonListUseCase {
    suspend operator fun invoke(): Flow<Resource<PokeDex>>
}