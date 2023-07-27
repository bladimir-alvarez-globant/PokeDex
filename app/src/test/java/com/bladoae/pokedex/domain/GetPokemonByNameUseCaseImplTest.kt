package com.bladoae.pokedex.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.pokedex.base.test.MainCoroutineRule
import com.bladoae.pokedex.domain.model.pokemon.Ability
import com.bladoae.pokedex.domain.model.pokemon.Pokemon
import com.bladoae.pokedex.domain.model.pokemon.Sprites
import com.bladoae.pokedex.domain.model.pokemon.Type
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import com.bladoae.pokedex.domain.usecase.GetPokemonByNameUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
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
class GetPokemonByNameUseCaseImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getPokemonByNameUseCaseImpl: GetPokemonByNameUseCaseImpl

    @MockK
    private lateinit var pokeDexRepository: PokeDexRepository

    private val dispatcher = Dispatchers.Main

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getPokemonByNameUseCaseImpl = GetPokemonByNameUseCaseImpl(pokeDexRepository, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get pokemon by name response is success`() = runBlocking {
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
            pokeDexRepository.getPokemonByName(name)
        } returns flowOf(expectedResponse)

        var actualResponse = listOf<Pokemon>()
        launch(dispatcher) {
            getPokemonByNameUseCaseImpl(name)
                .collect { response -> actualResponse = response?.filterNotNull() ?: listOf() }
        }

        coVerify(exactly = 1) { pokeDexRepository.getPokemonByName(name) }
        assertEquals(
            expectedResponse,
            actualResponse
        )
        Assert.assertEquals(
            "Name must be $name.",
            actualResponse.first().name,
            name
        )
    }

}