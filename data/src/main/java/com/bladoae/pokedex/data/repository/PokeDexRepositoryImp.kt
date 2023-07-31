package com.bladoae.pokedex.data.repository

import com.bladoae.pokedex.base.common.Resource
import com.bladoae.pokedex.data.apiservice.PokeDexApiService
import com.bladoae.pokedex.data.mappers.fromListEntityToPokemonList
import com.bladoae.pokedex.data.mappers.toEffect
import com.bladoae.pokedex.data.mappers.toEncounterList
import com.bladoae.pokedex.data.mappers.toPokemon
import com.bladoae.pokedex.data.mappers.toPokemonEntityList
import com.bladoae.pokedex.data.mappers.toPokemonList
import com.bladoae.pokedex.domain.model.detail.Effect
import com.bladoae.pokedex.domain.model.encounter.Encounter
import com.bladoae.pokedex.domain.model.pokemon.Pokemon
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import com.bladoaepokedex.databasemanager.daos.PokemonDao
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PokeDexRepositoryImp @Inject constructor(
    private val pokeDexApiService: PokeDexApiService,
    private val pokemonDao: PokemonDao,
    private val dispatcher: CoroutineContext
) : PokeDexRepository {
    override suspend fun getPokemonList(limit: Int, offSet: Int): Flow<Resource<List<Pokemon>>> {
        return flow {
            pokeDexApiService.getPokemonList(limit, offSet)
                .map { response ->
                    if(response is Resource.Success) {
                        return@map Resource.Success(
                            data = response.data?.results?.toPokemonList() ?: listOf()
                        )
                    } else {
                        return@map Resource.Error<List<Pokemon>>(response.message ?: "")
                    }
                }
                .collect {
                    emit(it)
                }
        }.flowOn(dispatcher)
    }

    override suspend fun getPokemonDetail(name: String): Flow<Resource<Pokemon>> {
        return flow {
            pokeDexApiService.getPokemonDetail(name)
                .map { response ->
                    if(response is Resource.Success) {
                        return@map Resource.Success(
                            data = response.data?.toPokemon() ?: Pokemon()
                        )
                    } else {
                        return@map Resource.Error<Pokemon>(response.message ?: "")
                    }
                }
                .collect {
                    emit(it)
                }
        }.flowOn(dispatcher)
    }

    override suspend fun savePokemonList(items: List<Pokemon>) {
        pokemonDao.insertPokemon(items.toPokemonEntityList())
    }

    override suspend fun getPokemonByName(name: String): Flow<List<Pokemon?>?> {
        return flow {
            val response = pokemonDao.selectPokemon(name)
            val pokemon = if(response.isNotEmpty()) {
                response.fromListEntityToPokemonList()
            } else {
                null
            }
            emit(pokemon)
        }.flowOn(dispatcher)
    }

    override suspend fun getEffects(id: Int): Flow<Resource<Effect>> {
        return flow {
            pokeDexApiService.getEffects(id)
                .map { response ->
                    if(response is Resource.Success) {
                        return@map Resource.Success(
                            data = response.data?.toEffect() ?: Effect()
                        )
                    } else {
                        return@map Resource.Error<Effect>(response.message ?: "")
                    }
                }
                .collect {
                    emit(it)
                }
        }.flowOn(dispatcher)
    }

    override suspend fun getEncounters(id: Int): Flow<Resource<List<Encounter>>> {
        return flow {
            pokeDexApiService.getEncounters(id)
                .map { response ->
                    if(response is Resource.Success) {
                        return@map Resource.Success(
                            data = response.data?.toEncounterList() ?: listOf()
                        )
                    } else {
                        return@map Resource.Error<List<Encounter>>(response.message ?: "")
                    }
                }
                .collect {
                    emit(it)
                }
        }.flowOn(dispatcher)
    }
}