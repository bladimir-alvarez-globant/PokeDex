package com.bladoae.pokedex.presentation.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.PokeDex
import com.bladoae.pokedex.domain.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
): ViewModel() {

    private val _pokemonList = MutableLiveData<Resource<PokeDex>>()
    val pokemonList: LiveData<Resource<PokeDex>> = _pokemonList

    fun getPokemonList() {
        _pokemonList.value = Resource.Loading()
        viewModelScope.launch {
            getPokemonListUseCase()
                .collect {
                    _pokemonList.value = it
                }
        }
    }

}