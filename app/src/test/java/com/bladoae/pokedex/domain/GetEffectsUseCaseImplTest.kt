package com.bladoae.pokedex.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.pokedex.base.test.MainCoroutineRule
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.Ability
import com.bladoae.pokedex.domain.model.Effect
import com.bladoae.pokedex.domain.model.EffectEntries
import com.bladoae.pokedex.domain.model.Language
import com.bladoae.pokedex.domain.model.Pokemon
import com.bladoae.pokedex.domain.model.Sprites
import com.bladoae.pokedex.domain.model.Type
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import com.bladoae.pokedex.domain.usecase.GetEffectsUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetEffectsUseCaseImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getEffectsUseCaseImpl: GetEffectsUseCaseImpl

    @MockK
    private lateinit var pokeDexRepository: PokeDexRepository

    private val dispatcher = Dispatchers.Main

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getEffectsUseCaseImpl = GetEffectsUseCaseImpl(pokeDexRepository, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get effects response is success`() = runBlocking {
        val id = 100
        val expectedResponse = Resource.Success(
            Effect(
                listOf(
                    EffectEntries(
                        effect = "Something",
                        language = Language(
                            "en"
                        )
                    )
                )
            )
        )

        coEvery {
            pokeDexRepository.getEffects(id)
        } returns flowOf(expectedResponse)

        var actualResponse = Effect()
        launch(dispatcher) {
            getEffectsUseCaseImpl(id)
                .collect { response -> actualResponse = response.data ?: Effect() }
        }

        coVerify(exactly = 1) { pokeDexRepository.getEffects(id) }
        TestCase.assertEquals(
            expectedResponse.data,
            actualResponse
        )
    }

}