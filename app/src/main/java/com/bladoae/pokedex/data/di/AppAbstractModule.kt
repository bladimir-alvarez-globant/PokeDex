package com.bladoae.pokedex.data.di

import com.bladoae.pokedex.data.repository.PokeDexRepositoryImp
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppAbstractModule {

    @Binds
    @Singleton
    abstract fun providePokeDexRepository(
        pokeDexRepository: PokeDexRepositoryImp
    ): PokeDexRepository

}