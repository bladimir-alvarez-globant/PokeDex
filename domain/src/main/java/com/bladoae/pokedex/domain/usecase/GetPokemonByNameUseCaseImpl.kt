package com.bladoae.pokedex.domain.usecase

import com.bladoae.pokedex.domain.model.pokemon.Pokemon
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetPokemonByNameUseCaseImpl @Inject constructor(
    private val pokeDexRepository: PokeDexRepository,
    private val dispatcher: CoroutineContext
) {

    suspend operator fun invoke(name: String): Flow<List<Pokemon?>?> {
        return flow {
            pokeDexRepository.getPokemonByName(name)
                .collect { pokemon ->
                    emit(pokemon)
                }
        }.flowOn(dispatcher)
    }

}