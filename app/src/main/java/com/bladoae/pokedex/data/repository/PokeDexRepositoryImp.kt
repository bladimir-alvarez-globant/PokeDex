package com.bladoae.pokedex.data.repository

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.data.apiservice.PokeDexApiService
import com.bladoae.pokedex.domain.model.PokeDex
import com.bladoae.pokedex.domain.model.toPokeDex
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PokeDexRepositoryImp @Inject constructor(
    private val pokeDexApiService: PokeDexApiService
) : PokeDexRepository {
    override suspend fun getPokemonList(limit: Int, offSet: Int): Flow<Resource<PokeDex>> {
        return flow {
            pokeDexApiService.getPokemonList(limit, offSet)
                .map { response ->
                    if(response is Resource.Success) {
                        return@map Resource.Success(
                            data = response.data?.toPokeDex() ?: PokeDex()
                        )
                    } else {
                        return@map Resource.Error<PokeDex>(response.message ?: "")
                    }
                }
                .collect {
                    emit(it)
                }
        }
    }
}