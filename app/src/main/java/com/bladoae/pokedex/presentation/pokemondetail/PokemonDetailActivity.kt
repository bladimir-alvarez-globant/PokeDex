package com.bladoae.pokedex.presentation.pokemondetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bladoae.pokedex.R
import com.bladoae.pokedex.common.LANGUAGE
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.Ability
import com.bladoae.pokedex.domain.model.Effect
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

    private val pokemonDetailViewModel by viewModels<PokemonDetailViewModel>()

    private var pokemon: Pokemon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pokemon = intent.getParcelableExtra(
            POKEMON
        )

        pokemonDetailViewModel.effect.observe(this, ::handleEffect)

        pokemon?.let { data ->
            pokemonDetailViewModel.getEffects(data.id)
        }
    }

    private fun handleEffect(resource: Resource<Effect>?) {
        when(resource) {
            is Resource.Loading -> {
                Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show()
            }
            is Resource.Success -> {
                resource.data?.let { effect ->
                    setupContent(effect)
                }
            }
            else -> Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupContent(effect: Effect) {
        setContent {
            ComposePokeDexTheme {
                pokemon?.let { data ->
                    val description = effect.effectEntries?.find { it.language?.name == LANGUAGE }?.effect ?: ""
                    PokemonDetailScreen(data, description)
                }
            }
        }
    }

}

@ExperimentalGlideComposeApi
@Composable
fun PokemonDetailScreen(pokemon: Pokemon, description: String) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        pokemon.sprites?.frontDefault?.let { image ->
            GlideImage(
                model = image,
                contentDescription = "pokemon",
                modifier = Modifier
                    .padding(0.dp, 10.dp)
                    .defaultMinSize(150.dp, 150.dp)
                    .fillMaxWidth(),
            )
        }

        Text(
            text = pokemon.name?.replaceFirstChar { it.uppercase() } ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp, 0.dp, 0.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )

        Text(
            text = stringResource(R.string.information),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(0.dp, 5.dp, 0.dp, 0.dp)
        )

        if(description.isNotEmpty()) {
            Text(
                text = description.replaceFirstChar { it.uppercase() },
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 5.dp)
            )
        }

        ItemDetail(label = stringResource(R.string.weight_label), text = String.format(stringResource(R.string.weight_description, pokemon.weight ?: 0)))
        ItemDetail(label = stringResource(R.string.height_label), text = String.format(stringResource(R.string.height_description, pokemon.height ?: 0)))
        ItemDetail(label = stringResource(R.string.type_label), text = "${pokemon.types?.map { it.name?.replaceFirstChar { type -> type.uppercase() } }?.joinToString()}")
        pokemon.abilities?.let { abilities ->
            ItemDetail(label = stringResource(R.string.abilities_label), text = abilities.map { it.name?.replaceFirstChar { name -> name.uppercase() } }.joinToString())
        }
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
        PokemonDetailScreen(
            pokemon,
            "This Pokémon's damaging moves have a 10% chance to make the target flinch with each hit if they do not already cause flinching as a secondary effect.\\n\\nThis ability does not stack with a held item.\\n\\nOverworld: The wild encounter rate is halved while this Pokémon is first in the party."
        )
    }
}