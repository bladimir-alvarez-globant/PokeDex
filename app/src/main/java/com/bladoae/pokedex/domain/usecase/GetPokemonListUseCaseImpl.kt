package com.bladoae.pokedex.domain.usecase

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.PokeDex
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class GetPokemonListUseCaseImpl @Inject constructor(
    private val pokeDexRepository: PokeDexRepository,
    private val dispatcher: CoroutineContext
) : GetPokemonListUseCase {

    companion object {
        private const val LIMIT_POKEMON = 151
        private const val OFFSET_POKEMON = 0
    }

    override suspend fun invoke(): Flow<Resource<PokeDex>> {
        return flow {
            pokeDexRepository.getPokemonList(LIMIT_POKEMON, OFFSET_POKEMON)
                .onEach {
                    //save information on Room
                }
                .flowOn(dispatcher)
                .collect {
                    emit(it)
                }
        }
    }
}
