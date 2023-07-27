package com.bladoae.pokedex.presentation.pokemonlist

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents.init
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.release
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bladoae.pokedex.domain.model.Ability
import com.bladoae.pokedex.domain.model.Pokemon
import com.bladoae.pokedex.domain.model.Sprites
import com.bladoae.pokedex.domain.model.Type
import com.bladoae.pokedex.presentation.CountingIdlingResourceSingleton
import com.bladoae.pokedex.presentation.pokemondetail.PokemonDetailActivity
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@RunWith(AndroidJUnit4::class)
class PokemonListScreenTest {

    private val pokemonList = listOf(
        Pokemon(
            id = 12,
            name = "pikachu",
            abilities = listOf(
                Ability(
                    name = "Poisson"
                )
            ),
            sprites = Sprites(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
            ),
            types = listOf(
                Type(
                    name = "Fire"
                )
            ),
            height = 19,
            weight = 80
        )
    )

    @get:Rule
    val composeTestRule = createAndroidComposeRule<PokemonListActivity>()

    @Before
    fun registerIdlingResource() {
        init()
        IdlingRegistry.getInstance().register(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        release()
        IdlingRegistry.getInstance().unregister(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @Test
    fun whenSearchBoxIsDisplayed() {
        val searchBoxDescription = "Find your favorite pokemon"

        // When
        composeTestRule.setContent {
            PokemonListScreen(pokemonList,
                { },
                { }
            )
        }

        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithText(searchBoxDescription).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("searchIcon").assertIsDisplayed()
    }

    @Test
    fun whenSearchBoxInputValidation() {
        val searchBoxDescription = "Find your favorite pokemon"

        // When
        composeTestRule.setContent {
            PokemonListScreen(pokemonList,
                { },
                { }
            )
        }

        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithText(searchBoxDescription).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("searchIcon").assertIsDisplayed()

        // I change the input
        val input = "pikachu"
        composeTestRule.onNodeWithText(searchBoxDescription).performTextInput(input)
        composeTestRule.onNodeWithText(input).assertIsDisplayed()
    }

    @Test
    fun whenItemsAreDisplayed() {
        // When
        composeTestRule.setContent {
            PokemonListScreen(pokemonList,
                { },
                { }
            )
        }

        val pokemon = pokemonList.first()

        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithContentDescription("pokemon").assertIsDisplayed()
        composeTestRule.onNodeWithText(pokemon.name?.replaceFirstChar { it.uppercase() } ?: "").assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.types?.map { type -> type.name?.replaceFirstChar { it.uppercase() } }?.joinToString()}").assertIsDisplayed()
    }

    @Test
    fun whenAItemIsSelected() {
        // When
        composeTestRule.setContent {
            PokemonListScreen(pokemonList,
                { },
                { composeTestRule.activity.onSelectPokemon(it) }
            )
        }

        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNode(hasContentDescription("pokemon")).assertIsDisplayed()

        composeTestRule.onNode(hasContentDescription("pokemon")).performClick()

        intended(hasComponent(PokemonDetailActivity::class.java.name), times(1))
    }

}