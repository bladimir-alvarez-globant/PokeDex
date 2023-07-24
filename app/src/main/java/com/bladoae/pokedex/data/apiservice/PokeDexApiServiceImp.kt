package com.bladoae.pokedex.data.apiservice

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.requestmanager.ApiService
import com.bladoae.pokedex.requestmanager.model.PokeDexDto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeDexApiServiceImp @Inject constructor(
    private val service: ApiService
) : PokeDexApiService {
    override suspend fun getPokemonList(limit: Int, offSet: Int): Flow<Resource<PokeDexDto>> {
        return flow {
            val response = service.getPokemonList(limit, offSet)
            emit(Resource.Success(response))
        }
    }
}