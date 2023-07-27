package com.bladoae.pokedex.domain.usecase

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.encounter.Encounter
import kotlinx.coroutines.flow.Flow

interface GetEncountersUseCase {
    suspend operator fun invoke(id: Int): Flow<Resource<List<Encounter>>>
}