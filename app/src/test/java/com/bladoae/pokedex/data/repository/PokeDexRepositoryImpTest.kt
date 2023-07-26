package com.bladoae.pokedex.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.pokedex.base.test.MainCoroutineRule
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.data.apiservice.PokeDexApiService
import com.bladoae.pokedex.domain.model.Ability
import com.bladoae.pokedex.domain.model.Pokemon
import com.bladoae.pokedex.domain.model.Sprites
import com.bladoae.pokedex.domain.model.Type
import com.bladoae.pokedex.domain.model.fromListEntityToPokemonList
import com.bladoae.pokedex.domain.model.toPokemon
import com.bladoae.pokedex.domain.model.toPokemonEntityList
import com.bladoae.pokedex.domain.model.toPokemonList
import com.bladoae.pokedex.requestmanager.model.AbilityDetailDto
import com.bladoae.pokedex.requestmanager.model.AbilityDto
import com.bladoae.pokedex.requestmanager.model.PokemonDto
import com.bladoae.pokedex.requestmanager.model.PokemonListResponse
import com.bladoae.pokedex.requestmanager.model.SpritesDto
import com.bladoae.pokedex.requestmanager.model.TypeDetailDto
import com.bladoae.pokedex.requestmanager.model.TypeDto
import com.bladoaepokedex.databasemanager.daos.PokemonDao
import com.bladoaepokedex.databasemanager.entities.AbilityEntity
import com.bladoaepokedex.databasemanager.entities.PokemonEntity
import com.bladoaepokedex.databasemanager.entities.SpritesEntity
import com.bladoaepokedex.databasemanager.entities.TypeEntity
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
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class PokeDexRepositoryImpTest {

    companion object {
        private const val LIMIT_POKEMON = 151
        private const val OFFSET_POKEMON = 0
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var pokeDexRepositoryImp: PokeDexRepositoryImp

    @MockK
    private lateinit var pokeDexApiService: PokeDexApiService

    @MockK
    private lateinit var pokemonDao: PokemonDao

    private val dispatcher = Dispatchers.Main

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        pokeDexRepositoryImp = PokeDexRepositoryImp(pokeDexApiService, pokemonDao, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get pokemon list response is success`() = runBlocking {
        val name = "Pikachu"
        val expectedResponse = Resource.Success(
            PokemonListResponse(
                listOf(
                    PokemonDto(
                        id = 12,
                        name = "Pikachu",
                        abilities = listOf(
                            AbilityDto(
                                AbilityDetailDto(
                                    "Poisson"
                                )
                            )
                        ),
                        sprites = SpritesDto(
                            "URL"
                        ),
                        types = listOf(
                            TypeDto(
                                TypeDetailDto(
                                    "Fire"
                                )
                            )
                        )
                    )
                )
            )
        )

        coEvery {
            pokeDexApiService.getPokemonList(LIMIT_POKEMON, OFFSET_POKEMON)
        } returns flowOf(expectedResponse)

        var actualResponse = listOf<Pokemon>()
        launch(dispatcher) {
            pokeDexRepositoryImp.getPokemonList(LIMIT_POKEMON, OFFSET_POKEMON)
                .collect { response -> actualResponse = response.data ?: listOf() }
        }

        coVerify(exactly = 1) { pokeDexApiService.getPokemonList(LIMIT_POKEMON, OFFSET_POKEMON) }
        assertEquals(
            expectedResponse.data?.results?.toPokemonList(),
            actualResponse
        )
        assertEquals(
            "Name must be $name.",
            actualResponse.first().name,
            name
        )
    }

    @Test
    fun testSavePokemonList() = runBlocking {
        // Prepare test data
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

        // Create a spy of the actual object
        val pokemonDaoSpy = spy(pokemonDao)

        // Create the PokemonRepository and pass the spy
        val pokemonRepositorySpy = PokeDexRepositoryImp(pokeDexApiService, pokemonDaoSpy, Dispatchers.IO)

        // Call the function being tested
        pokemonRepositorySpy.savePokemonList(pokemonList)

        // Verify that the method was called with the correct arguments
        verify(pokemonDaoSpy).insertPokemon(pokemonList.toPokemonEntityList())
    }

    @Test
    fun `when get pokemon detail response is success`() = runBlocking {
        val name = "Pikachu"
        val expectedResponse = Resource.Success(
            PokemonDto(
                id = 12,
                name = "Pikachu",
                abilities = listOf(
                    AbilityDto(
                        AbilityDetailDto(
                            "Poisson"
                        )
                    )
                ),
                sprites = SpritesDto(
                    "URL"
                ),
                types = listOf(
                    TypeDto(
                        TypeDetailDto(
                            "Fire"
                        )
                    )
                )
            )
        )

        coEvery {
            pokeDexApiService.getPokemonDetail(name)
        } returns flowOf(expectedResponse)

        var actualResponse = Pokemon()
        launch(dispatcher) {
            pokeDexRepositoryImp.getPokemonDetail(name)
                .collect { response -> response.data?.let { data -> actualResponse = data } }
        }

        coVerify(exactly = 1) { pokeDexApiService.getPokemonDetail(name) }
        assertEquals(
            expectedResponse.data?.toPokemon(),
            actualResponse
        )
        assertEquals(
            "Name must be $name.",
            actualResponse.name,
            name
        )
    }

    @Test
    fun `when get pokemon by name response is success`() = runBlocking {
        val name = "Pikachu"
        val expectedResponse = listOf(
            PokemonEntity(
                id = 12,
                name = "Pikachu",
                abilities = listOf(
                    AbilityEntity(
                        "Poisson"
                    )
                ),
                sprites = SpritesEntity(
                    "URL"
                ),
                types = listOf(
                    TypeEntity(
                        "Fire"
                    )
                )
            )
        )

        coEvery {
            pokemonDao.selectPokemon(name)
        } returns expectedResponse

        var actualResponse = listOf<Pokemon?>(Pokemon())
        launch(dispatcher) {
            pokeDexRepositoryImp.getPokemonByName(name)
                .collect { response -> actualResponse = response ?: listOf() }
        }

        coVerify(exactly = 1) { pokemonDao.selectPokemon(name) }
        assertEquals(
            expectedResponse.fromListEntityToPokemonList(),
            actualResponse
        )
        assertEquals(
            "Name must be $name.",
            actualResponse.first()?.name,
            name
        )
    }

}