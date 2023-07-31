package com.bladoae.pokedex.presentation.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bladoae.pokedex.base.common.Resource
import com.bladoae.pokedex.domain.model.pokemon.Pokemon
import com.bladoae.pokedex.domain.usecase.GetPokemonByNameUseCaseImpl
import com.bladoae.pokedex.domain.usecase.GetPokemonDetailedListUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonDetailedListUseCase: GetPokemonDetailedListUseCaseImpl,
    private val getPokemonByNameUseCase: GetPokemonByNameUseCaseImpl
): ViewModel() {

    private val _pokemonList = MutableLiveData<Resource<List<Pokemon?>?>>()
    val pokemonList: LiveData<Resource<List<Pokemon?>?>> = _pokemonList

    fun getPokemonList() {
        _pokemonList.value = Resource.Loading()
        viewModelScope.launch {
            getPokemonDetailedListUseCase()
                .collect { response ->
                    _pokemonList.value = response
                }
        }
    }

    fun getPokemonByName(name: String) {
        viewModelScope.launch {
            getPokemonByNameUseCase(name)
                .collect { response ->
                    _pokemonList.value = Resource.Success(response?.filterNotNull())
                }
        }
    }

}