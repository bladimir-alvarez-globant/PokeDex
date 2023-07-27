package com.bladoae.pokedex.presentation.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.Effect
import com.bladoae.pokedex.domain.usecase.GetEffectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getEffectsUseCase: GetEffectsUseCase
): ViewModel() {

    private val _effect = MutableLiveData<Resource<Effect>>()
    val effect: LiveData<Resource<Effect>> = _effect

    fun getEffects(id: Int) {
        viewModelScope.launch {
            getEffectsUseCase(id)
                .collect {
                    _effect.value = it
                }
        }
    }

}