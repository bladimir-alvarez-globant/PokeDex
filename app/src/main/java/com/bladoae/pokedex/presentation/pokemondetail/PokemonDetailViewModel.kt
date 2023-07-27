package com.bladoae.pokedex.presentation.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bladoae.pokedex.common.LANGUAGE
import com.bladoae.pokedex.common.Resource
import com.bladoae.pokedex.domain.model.detail.Effect
import com.bladoae.pokedex.domain.model.encounter.Encounter
import com.bladoae.pokedex.domain.usecase.GetEffectsUseCase
import com.bladoae.pokedex.domain.usecase.GetEncountersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getEffectsUseCase: GetEffectsUseCase,
    private val getEncountersUseCase: GetEncountersUseCase
): ViewModel() {

    private val _effect = MutableLiveData<Resource<Effect>>()
    val effect: LiveData<Resource<Effect>> = _effect
    var effectDescription = ""

    fun getEffects(id: Int) {
        viewModelScope.launch {
            getEffectsUseCase(id)
                .collect {
                    it.data?.let { data ->
                        effectDescription = data.effectEntries?.find { effect -> effect.language?.name == LANGUAGE }?.effect ?: ""
                    }
                    _effect.value = it
                }
        }
    }

    private val _encounters = MutableLiveData<Resource<List<Encounter>>>()
    val encounters: LiveData<Resource<List<Encounter>>> = _encounters
    var encountersDescription = ""

    fun getEncounters(id: Int) {
        viewModelScope.launch {
            getEncountersUseCase(id)
                .collect {
                    it.data?.let { data ->
                        encountersDescription =
                            data.map { location -> location.locationArea?.name?.replaceFirstChar { name -> name.uppercase() } }.joinToString()
                    }
                    _encounters.value = it
                }
        }
    }

}