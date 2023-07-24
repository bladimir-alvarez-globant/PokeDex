package com.bladoae.pokedex.presentation.pokemonlist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.PokeDex
import com.bladoae.pokedex.presentation.theme.ComposePokeDexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListActivity : ComponentActivity() {

    private val pokemonListViewModel by viewModels<PokemonListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposePokeDexTheme {
                PokemonListScreen()
            }
        }

        pokemonListViewModel.pokemonList.observe(this, ::handlePokemonList)

        pokemonListViewModel.getPokemonList()
    }

    private fun handlePokemonList(resource: Resource<PokeDex>) {
        when(resource) {
            is Resource.Error -> {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
            is Resource.Loading -> {
                Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
            }
            is Resource.Success -> {
                Toast.makeText(this, "Success ${resource.data?.results?.size} elements", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

@Composable
fun PokemonListScreen() {

}