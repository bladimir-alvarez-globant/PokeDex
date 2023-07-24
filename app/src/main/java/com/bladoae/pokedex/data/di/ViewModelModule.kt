package com.bladoae.pokedex.data.di

import com.bladoae.pokedex.domain.usecase.GetPokemonListUseCase
import com.bladoae.pokedex.presentation.pokemonlist.PokemonListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {
    @Provides
    fun providePokemonListViewModel(getPokemonListUseCase: GetPokemonListUseCase): PokemonListViewModel {
        return PokemonListViewModel(getPokemonListUseCase)
    }
}