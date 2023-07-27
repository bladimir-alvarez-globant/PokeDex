package com.bladoae.pokedex.domain.usecase

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.Effect
import kotlinx.coroutines.flow.Flow

interface GetEffectsUseCase {
    suspend operator fun invoke(id: Int): Flow<Resource<Effect>>
}