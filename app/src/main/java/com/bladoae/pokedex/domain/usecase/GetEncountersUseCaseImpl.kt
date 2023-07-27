package com.bladoae.pokedex.domain.usecase

import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.encounter.Encounter
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetEncountersUseCaseImpl @Inject constructor(
    private val pokeDexRepository: PokeDexRepository,
    private val dispatcher: CoroutineContext
) : GetEncountersUseCase {
    override suspend fun invoke(id: Int): Flow<Resource<List<Encounter>>> {
        return flow {
            pokeDexRepository.getEncounters(id)
                .collect { effect ->
                    emit(effect)
                }
        }.flowOn(dispatcher)
    }
}