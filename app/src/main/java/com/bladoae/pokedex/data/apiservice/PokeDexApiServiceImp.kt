package com.bladoae.pokedex.data.apiservice

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.requestmanager.ApiService
import com.bladoae.pokedex.requestmanager.model.EffectDto
import com.bladoae.pokedex.requestmanager.model.PokemonListResponse
import com.bladoae.pokedex.requestmanager.model.PokemonDto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeDexApiServiceImp @Inject constructor(
    private val service: ApiService
) : PokeDexApiService {
    override suspend fun getPokemonList(limit: Int, offSet: Int): Flow<Resource<PokemonListResponse>> {
        return flow {
            val response = service.getPokemonList(limit, offSet)
            emit(Resource.Success(response))
        }
    }

    override suspend fun getPokemonDetail(name: String): Flow<Resource<PokemonDto>> {
        return flow {
            val response = service.getPokemonDetail(name)
            emit(Resource.Success(response))
        }
    }

    override suspend fun getEffects(id: Int): Flow<Resource<EffectDto>> {
        return flow {
            val response = service.getEffects(id)
            emit(Resource.Success(response))
        }
    }
}