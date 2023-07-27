package com.bladoae.pokedex.data.di

import com.bladoae.pokedex.domain.usecase.GetEffectsUseCase
import com.bladoae.pokedex.domain.usecase.GetEncountersUseCase
import com.bladoae.pokedex.domain.usecase.GetPokemonByNameUseCase
import com.bladoae.pokedex.domain.usecase.GetPokemonDetailedListUseCase
import com.bladoae.pokedex.presentation.pokemondetail.PokemonDetailViewModel
import com.bladoae.pokedex.presentation.pokemonlist.PokemonListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {
    @Provides
    fun providePokemonListViewModel(
        getPokemonDetailedListUseCaseImpl: GetPokemonDetailedListUseCase,
        getPokemonByNameUseCase: GetPokemonByNameUseCase
    ): PokemonListViewModel {
        return PokemonListViewModel(
            getPokemonDetailedListUseCaseImpl,
            getPokemonByNameUseCase
        )
    }

    @Provides
    fun providePokemonDetailViewModel(
        getEffectsUseCase: GetEffectsUseCase,
        getEncountersUseCase: GetEncountersUseCase
    ): PokemonDetailViewModel {
        return PokemonDetailViewModel(
            getEffectsUseCase,
            getEncountersUseCase
        )
    }
}