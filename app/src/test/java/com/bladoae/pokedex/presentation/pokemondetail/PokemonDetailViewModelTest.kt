package com.bladoae.pokedex.presentation.pokemondetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.pokedex.base.test.MainCoroutineRule
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.detail.Effect
import com.bladoae.pokedex.domain.model.detail.EffectEntries
import com.bladoae.pokedex.domain.model.detail.Language
import com.bladoae.pokedex.domain.usecase.GetPokemonDetailUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonDetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: PokemonDetailViewModel

    @MockK
    private lateinit var getEffectsUseCase: GetPokemonDetailUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = PokemonDetailViewModel(
            getEffectsUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get effects response is success`() = runBlockingTest {
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
            getEffectsUseCase(id)
        } returns flowOf(expectedResponse)

        viewModel.getEffects(id)
        viewModel.effect.observeForever {}

        val actualResponse = viewModel.effect.value

        TestCase.assertEquals(expectedResponse.data, actualResponse?.data)
    }

}