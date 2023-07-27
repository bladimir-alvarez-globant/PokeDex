package com.bladoae.pokedex.presentation.pokemonlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.pokedex.base.test.MainCoroutineRule
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.pokemon.Ability
import com.bladoae.pokedex.domain.model.pokemon.Pokemon
import com.bladoae.pokedex.domain.model.pokemon.Sprites
import com.bladoae.pokedex.domain.model.pokemon.Type
import com.bladoae.pokedex.domain.usecase.GetPokemonByNameUseCase
import com.bladoae.pokedex.domain.usecase.GetPokemonDetailedListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: PokemonListViewModel

    @MockK
    private lateinit var getPokemonDetailedListUseCase: GetPokemonDetailedListUseCase

    @MockK
    private lateinit var getPokemonByNameUseCase: GetPokemonByNameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = PokemonListViewModel(
            getPokemonDetailedListUseCase,
            getPokemonByNameUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get pokemon list response is success`() = runBlockingTest {
        val data = listOf(
            Pokemon(
                id = 12,
                name = "Pikachu",
                abilities = listOf(
                    Ability(
                        name = "Poisson"
                    )
                ),
                sprites = Sprites(
                    "URL"
                ),
                types = listOf(
                    Type(
                        name = "Fire"
                    )
                )
            )
        )
        val expectedResponse = Resource.Success<List<Pokemon?>?>(
            data
        )

        coEvery {
            getPokemonDetailedListUseCase()
        } returns flowOf(expectedResponse)

        viewModel.getPokemonList()
        viewModel.pokemonList.observeForever {}

        val actualResponse = viewModel.pokemonList.value

        assertEquals(expectedResponse.data, actualResponse?.data)
        Assert.assertEquals(
            "Name must be Pikachu",
            "Pikachu",
            actualResponse?.data?.first()?.name,
        )
    }

    @Test
    fun `when get pokemon by name response is success`() = runBlockingTest {
        val name = "Pikachu"
        val expectedResponse = listOf(
            Pokemon(
                id = 12,
                name = "Pikachu",
                abilities = listOf(
                    Ability(
                        name = "Poisson"
                    )
                ),
                sprites = Sprites(
                    "URL"
                ),
                types = listOf(
                    Type(
                        name = "Fire"
                    )
                )
            )
        )

        coEvery {
            getPokemonByNameUseCase(name)
        } returns flowOf(expectedResponse)

        viewModel.getPokemonByName(name)
        viewModel.pokemon.observeForever {}

        val actualResponse = viewModel.pokemon.value

        assertEquals(expectedResponse, actualResponse)
        Assert.assertEquals(
            "Name must be Pikachu",
            name,
            actualResponse?.first()?.name,
        )
    }

}