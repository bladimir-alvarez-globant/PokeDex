package com.bladoae.pokedex.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.pokedex.base.test.MainCoroutineRule
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.encounter.Encounter
import com.bladoae.pokedex.domain.model.encounter.LocationArea
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import com.bladoae.pokedex.domain.usecase.GetEncountersUseCase
import com.bladoae.pokedex.domain.usecase.GetEncountersUseCaseImpl
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetEncountersUseCaseImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getEncountersUseCase: GetEncountersUseCase

    @MockK
    private lateinit var pokeDexRepository: PokeDexRepository

    private val dispatcher = Dispatchers.Main

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getEncountersUseCase = GetEncountersUseCaseImpl(pokeDexRepository, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get encounters response is success`() = runBlocking {
        val id = 100
        val expectedResponse = Resource.Success(
            listOf(
                Encounter(
                    LocationArea(
                        "National Park"
                    )
                )
            )
        )

        coEvery {
            pokeDexRepository.getEncounters(id)
        } returns flowOf(expectedResponse)

        var actualResponse = listOf(Encounter())
        launch(dispatcher) {
            getEncountersUseCase(id)
                .collect { response -> actualResponse = response.data ?: listOf(Encounter()) }
        }

        coVerify(exactly = 1) { pokeDexRepository.getEncounters(id) }
        TestCase.assertEquals(
            expectedResponse.data,
            actualResponse
        )
    }

}