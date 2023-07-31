package com.bladoae.pokedex.domain.usecase

import com.bladoae.pokedex.base.common.Resource
import com.bladoae.pokedex.domain.model.pokemon.Pokemon
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single

class GetPokemonDetailedListUseCaseImpl @Inject constructor(
    private val pokeDexRepository: PokeDexRepository,
    private val dispatcher: CoroutineContext
) {

    companion object {
        private const val LIMIT_POKEMON = 151
        private const val OFFSET_POKEMON = 0
    }

    suspend operator fun invoke(): Flow<Resource<List<Pokemon?>?>> {
        return flow {
            pokeDexRepository.getPokemonList(LIMIT_POKEMON, OFFSET_POKEMON)
                .map { response ->
                    if(response is Resource.Success) {
                        val detailedList = response.data?.map { pokemon ->
                            pokeDexRepository.getPokemonDetail(pokemon.name ?: "")
                                .flowOn(dispatcher)
                                .map { detail ->
                                    if(detail is Resource.Success) {
                                        detail.data
                                    } else {
                                        Pokemon()
                                    }
                                }
                        }
                        val pokemonList = detailedList?.map { flow -> flow.single() }
                        Resource.Success(pokemonList)
                    } else {
                        Resource.Error(message = response.message ?: "", data = listOf())
                    }
                }
                .onEach { response ->
                    if(response is Resource.Success) {
                        response.data?.let { list ->
                            val data = list.filterNotNull()
                            pokeDexRepository.savePokemonList(data)
                        }
                    }
                }
                .collect { response: Resource<List<Pokemon?>?> ->
                    emit(response)
                }
        }.flowOn(dispatcher)
    }
}
