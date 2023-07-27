package com.bladoae.pokedex.presentation.pokemondetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bladoae.pokedex.domain.model.Ability
import com.bladoae.pokedex.domain.model.Pokemon
import com.bladoae.pokedex.domain.model.Sprites
import com.bladoae.pokedex.domain.model.Type
import com.bladoae.pokedex.presentation.pokemondetail.components.ItemDetail
import com.bladoae.pokedex.presentation.theme.ComposePokeDexTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
class PokemonDetailActivity : ComponentActivity() {

    companion object {
        val POKEMON = "pokemon"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemon = intent.getParcelableExtra<Pokemon>(
            POKEMON
        )

        setContent {
            ComposePokeDexTheme {
                pokemon?.let { data ->
                    PokemonDetailScreen(data)
                }
            }
        }
    }

}

@ExperimentalGlideComposeApi
@Composable
fun PokemonDetailScreen(pokemon: Pokemon) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        GlideImage(
            model = pokemon.sprites?.frontDefault,
            contentDescription = "pokemon",
            modifier = Modifier
                .padding(0.dp, 10.dp)
                .defaultMinSize(150.dp, 150.dp)
                .fillMaxWidth(),
        )
        Text(
            text = pokemon.name ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp, 0.dp, 0.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )

        Text(
            text = "Information",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(0.dp, 5.dp, 0.dp, 0.dp)
        )

        Text(
            text = "This Pokémon's damaging moves have a 10% chance to make the target flinch with each hit if they do not already cause flinching as a secondary effect.\\n\\nThis ability does not stack with a held item.\\n\\nOverworld: The wild encounter rate is halved while this Pokémon is first in the party.",
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 5.dp)
        )

        ItemDetail(label = "Weight:", text = "${pokemon.weight} KG")
        ItemDetail(label = "Height:", text = "${pokemon.height} M")
        ItemDetail(label = "Abilities:", text = "${pokemon.abilities?.map { it.name }?.joinToString()}")
    }
}

@ExperimentalGlideComposeApi
@Preview(showBackground = true)
@Composable
fun PokemonDetailScreenPreview() {
    ComposePokeDexTheme {
        val pokemon = Pokemon(
            id = 12,
            name = "Pikachu",
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
        PokemonDetailScreen(pokemon)
    }
}