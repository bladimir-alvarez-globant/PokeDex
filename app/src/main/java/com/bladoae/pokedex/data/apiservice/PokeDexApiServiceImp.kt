package com.bladoae.pokedex.data.apiservice

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.requestmanager.ApiService
import com.bladoae.pokedex.requestmanager.model.PokeDexDto
import javax.inject.Inject

class PokeDexApiServiceImp @Inject constructor(
    private val service: ApiService
) : PokeDexApiService {
    override suspend fun getPokeDex(limit: Int, offSet: Int): Resource<PokeDexDto> {
        val response = service.getPokedex(limit, offSet)
        return Resource.Success(response)
    }
}