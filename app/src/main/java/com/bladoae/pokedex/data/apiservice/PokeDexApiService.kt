package com.bladoae.pokedex.data.apiservice

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.requestmanager.model.PokeDexDto

interface PokeDexApiService {
    suspend fun getPokeDex(limit: Int, offSet: Int) : Resource<PokeDexDto>
}