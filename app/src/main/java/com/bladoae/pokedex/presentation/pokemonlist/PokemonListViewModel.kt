package com.bladoae.pokedex.presentation.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.Pokemon
import com.bladoae.pokedex.domain.usecase.GetPokemonDetailedListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonDetailedListUseCase: GetPokemonDetailedListUseCase
): ViewModel() {

    private val _pokemonList = MutableLiveData<Resource<List<Pokemon?>?>>()
    val pokemonList: LiveData<Resource<List<Pokemon?>?>> = _pokemonList

    fun getPokemonList() {
        _pokemonList.value = Resource.Loading()
        viewModelScope.launch {
            getPokemonDetailedListUseCase()
                .collect {
                    _pokemonList.value = it
                }
        }
    }

}