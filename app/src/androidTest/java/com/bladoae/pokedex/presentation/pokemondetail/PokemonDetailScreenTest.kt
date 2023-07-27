package com.bladoae.pokedex.presentation.pokemondetail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bladoae.pokedex.domain.model.Ability
import com.bladoae.pokedex.domain.model.Pokemon
import com.bladoae.pokedex.domain.model.Sprites
import com.bladoae.pokedex.domain.model.Type
import com.bladoae.pokedex.presentation.CountingIdlingResourceSingleton
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@RunWith(AndroidJUnit4::class)
class PokemonDetailScreenTest {

    private val pokemon = Pokemon(
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

    @get:Rule
    val composeTestRule = createAndroidComposeRule<PokemonDetailActivity>()

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @Test
    fun whenAllElementsAreDisplayed() {
        val description = "Some test description"

        // When
        composeTestRule.setContent {
            PokemonDetailScreen(pokemon, description)
        }

        composeTestRule.waitForIdle()

        // Then
        // Verify the Pokemon image is displayed
        composeTestRule.onNodeWithContentDescription("pokemon").assertIsDisplayed()

        composeTestRule.onNodeWithText(pokemon.name?.replaceFirstChar { type -> type.uppercase() } ?: "", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Information", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(description, substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Weight:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.weight ?: 0} KG", substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Height:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.height ?: 0} M", substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Type:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.types?.map { it.name?.replaceFirstChar { type -> type.uppercase() } }?.joinToString()}", substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Abilities:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.abilities?.map { it.name?.replaceFirstChar { name -> name.uppercase() } }?.joinToString()}", substring = true).assertIsDisplayed()
    }

    @Test
    fun whenPokemonDoesNotHaveAnImage() {
        val description = "Some test description"
        val pokemonNoImage = pokemon.copy(
            sprites = null
        )

        // When
        composeTestRule.setContent {
            PokemonDetailScreen(pokemonNoImage, description)
        }

        composeTestRule.waitForIdle()

        // Then
        // Verify the Pokemon image is displayed
        composeTestRule.onNodeWithContentDescription("pokemon").assertDoesNotExist()

        composeTestRule.onNodeWithText(pokemon.name?.replaceFirstChar { type -> type.uppercase() } ?: "", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Information", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(description, substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Weight:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.weight ?: 0} KG", substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Height:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.height ?: 0} M", substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Type:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.types?.map { it.name?.replaceFirstChar { type -> type.uppercase() } }?.joinToString()}", substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Abilities:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.abilities?.map { it.name?.replaceFirstChar { name -> name.uppercase() } }?.joinToString()}", substring = true).assertIsDisplayed()
    }

    @Test
    fun whenPokemonDoesNotHaveAbilities() {
        val description = "Some test description"
        val pokemonNoAbilities = pokemon.copy(
            abilities = null
        )
        // When
        composeTestRule.setContent {
            PokemonDetailScreen(pokemonNoAbilities, description)
        }

        composeTestRule.waitForIdle()

        // Then
        // Verify the Pokemon image is displayed
        composeTestRule.onNodeWithContentDescription("pokemon").assertIsDisplayed()

        composeTestRule.onNodeWithText(pokemon.name?.replaceFirstChar { type -> type.uppercase() } ?: "", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Information", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(description, substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Weight:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.weight ?: 0} KG", substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Height:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.height ?: 0} M", substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Type:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.types?.map { it.name?.replaceFirstChar { type -> type.uppercase() } }?.joinToString()}", substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Abilities:", substring = true).assertDoesNotExist()
        composeTestRule.onNodeWithText("${pokemon.abilities?.map { it.name?.replaceFirstChar { name -> name.uppercase() } }?.joinToString()}", substring = true).assertDoesNotExist()
    }

}