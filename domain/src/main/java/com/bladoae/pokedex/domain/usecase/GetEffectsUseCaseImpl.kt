package com.bladoae.pokedex.domain.usecase

import com.bladoae.pokedex.base.common.Resource
import com.bladoae.pokedex.domain.model.detail.Effect
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetEffectsUseCaseImpl @Inject constructor(
    private val pokeDexRepository: PokeDexRepository,
    private val dispatcher: CoroutineContext
) {
    suspend operator fun invoke(id: Int): Flow<Resource<Effect>> {
        return flow {
            pokeDexRepository.getEffects(id)
                .collect { effect: Resource<Effect> ->
                    emit(effect)
                }
        }.flowOn(dispatcher)
    }
}