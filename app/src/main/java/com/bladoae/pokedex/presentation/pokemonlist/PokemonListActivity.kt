package com.bladoae.pokedex.presentation.pokemonlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.bladoae.pokedex.R
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.Pokemon
import com.bladoae.pokedex.presentation.pokemondetail.PokemonDetailActivity
import com.bladoae.pokedex.presentation.pokemonlist.components.ListPokemon
import com.bladoae.pokedex.presentation.pokemonlist.components.SearchBox
import com.bladoae.pokedex.presentation.theme.ComposePokeDexTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
class PokemonListActivity : ComponentActivity() {

    private val pokemonListViewModel by viewModels<PokemonListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pokemonListViewModel.pokemonList.observe(this, ::handlePokemonList)
        pokemonListViewModel.pokemon.observe(this, ::handlePokemon)

        pokemonListViewModel.getPokemonList()
    }

    private fun handlePokemon(pokemon: List<Pokemon?>?) {
        pokemon?.let { data ->
            setupContent(data.filterNotNull())
        }
    }

    private fun handlePokemonList(resource: Resource<List<Pokemon?>?>) {
        when(resource) {
            is Resource.Error -> {
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
            }
            is Resource.Loading -> {
                Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show()
            }
            is Resource.Success -> {
                resource.data?.let { data ->
                    val items = data.filterNotNull()
                    setupContent(items)
                }

            }
        }
    }

    private fun onSearch(value: String) {
        pokemonListViewModel.getPokemonByName(value)
    }

    @VisibleForTesting
    fun onSelectPokemon(pokemon: Pokemon) {
        startActivity(
            Intent(this, PokemonDetailActivity::class.java)
                .apply {
                    putExtra(PokemonDetailActivity.POKEMON, pokemon)
                }
        )
    }

    private fun setupContent(items: List<Pokemon>) {
        setContent {
            ComposePokeDexTheme {
                PokemonListScreen(
                    items,
                    ::onSearch,
                    ::onSelectPokemon
                )
            }
        }
    }

}

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Composable
fun PokemonListScreen(
    items: List<Pokemon>,
    onSearch: (value: String) -> Unit,
    selectPokemon: (pokemon: Pokemon) -> Unit
) {
    Column {
        SearchBox(onSearch)
        ListPokemon(items, selectPokemon)
    }
}