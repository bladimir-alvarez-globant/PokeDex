package com.bladoae.pokedex.presentation.pokemonlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.pokemon.Pokemon
import com.bladoae.pokedex.presentation.general.components.DialogBoxLoading
import com.bladoae.pokedex.presentation.general.theme.ComposePokeDexTheme
import com.bladoae.pokedex.presentation.pokemondetail.PokemonDetailActivity
import com.bladoae.pokedex.presentation.pokemonlist.components.ListPokemon
import com.bladoae.pokedex.presentation.pokemonlist.components.SearchBox
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
class PokemonListActivity : ComponentActivity() {

    private val pokemonListViewModel by viewModels<PokemonListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupContent()

        pokemonListViewModel.getPokemonList()
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

    private fun setupContent() {
        setContent {
            ComposePokeDexTheme {
                val pokemonListResponse = pokemonListViewModel.pokemonList.observeAsState(Resource.Loading())

                if (pokemonListResponse.value is Resource.Loading) {
                    DialogBoxLoading()
                }

                if(pokemonListResponse.value is Resource.Success) {
                    pokemonListResponse.value.data?.let { data ->
                        PokemonListScreen(
                            data.filterNotNull(),
                            ::onSearch,
                            ::onSelectPokemon
                        )
                    }
                }
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