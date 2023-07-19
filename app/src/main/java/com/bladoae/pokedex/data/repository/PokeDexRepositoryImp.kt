package com.bladoae.pokedex.data.repository

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.data.apiservice.PokeDexApiService
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import com.bladoae.pokedex.requestmanager.model.PokeDexDto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeDexRepositoryImp @Inject constructor(
    private val pokeDexApiService: PokeDexApiService
) : PokeDexRepository {
    override suspend fun getPokeDex(limit: Int, offSet: Int): Flow<Resource<PokeDexDto>> {
        return flow {
            val response = pokeDexApiService.getPokeDex(limit, offSet)
            emit(response)
        }
    }
}