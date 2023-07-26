package com.bladoae.pokedex.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.pokedex.base.test.MainCoroutineRule
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.Ability
import com.bladoae.pokedex.domain.model.Pokemon
import com.bladoae.pokedex.domain.model.Sprites
import com.bladoae.pokedex.domain.model.Type
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import com.bladoae.pokedex.domain.usecase.GetPokemonDetailedListUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetPokemonDetailedListUseCaseImplTest {

    companion object {
        private const val LIMIT_POKEMON = 151
        private const val OFFSET_POKEMON = 0
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getPokemonDetailedListUseCaseImpl: GetPokemonDetailedListUseCaseImpl

    @MockK
    private lateinit var pokeDexRepository: PokeDexRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getPokemonDetailedListUseCaseImpl = GetPokemonDetailedListUseCaseImpl(pokeDexRepository, Dispatchers.IO)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get pokemon detailed list use case response is success`() = runBlockingTest {
        val name = "Pikachu"
        val pokemon = Pokemon(
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
        val pokemonList = listOf(pokemon)
        val expectedResponseList = Resource.Success(
            pokemonList
        )

        val expectedResponseDetail = Resource.Success(
            pokemon
        )

        coEvery {
            pokeDexRepository.getPokemonList(LIMIT_POKEMON, OFFSET_POKEMON)
        } returns flowOf(expectedResponseList)

        coEvery {
            pokeDexRepository.getPokemonDetail(name)
        } returns flowOf(expectedResponseDetail)

        coEvery {
            pokeDexRepository.savePokemonList(pokemonList)
        } returns Unit

        var actualResponse = listOf<Pokemon>()
        launch {
            getPokemonDetailedListUseCaseImpl()
                .collect { response -> actualResponse = response.data?.filterNotNull() ?: listOf() }
        }

        coVerify(exactly = 1) { pokeDexRepository.getPokemonList(LIMIT_POKEMON, OFFSET_POKEMON) }
        coVerify(exactly = 1) { pokeDexRepository.getPokemonDetail(name) }
        assertEquals(
            expectedResponseList.data,
            actualResponse
        )
        Assert.assertEquals(
            "Name must be $name.",
            actualResponse.first().name,
            name
        )
    }

}