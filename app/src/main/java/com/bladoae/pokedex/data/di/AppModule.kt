package com.bladoae.pokedex.data.di

import com.bladoae.pokedex.BuildConfig
import com.bladoae.pokedex.data.apiservice.PokeDexApiService
import com.bladoae.pokedex.data.apiservice.PokeDexApiServiceImp
import com.bladoae.pokedex.domain.repository.PokeDexRepository
import com.bladoae.pokedex.domain.usecase.GetEffectsUseCase
import com.bladoae.pokedex.domain.usecase.GetEffectsUseCaseImpl
import com.bladoae.pokedex.domain.usecase.GetPokemonByNameUseCase
import com.bladoae.pokedex.domain.usecase.GetPokemonByNameUseCaseImpl
import com.bladoae.pokedex.domain.usecase.GetPokemonDetailedListUseCase
import com.bladoae.pokedex.domain.usecase.GetPokemonDetailedListUseCaseImpl
import com.bladoae.pokedex.requestmanager.ApiService
import com.bladoae.pokedex.requestmanager.requestmanager.ServiceGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    @Named("baseBackendUrl")
    fun baseBackendUrlProvider(): String = BuildConfig.BASE_BE_URL

    @Provides
    @Singleton
    fun provideApiService(
        @Named("baseBackendUrl") baseBackendUrl: String
    ): ApiService {
        return ServiceGenerator(baseBackendUrl).createService()
    }

    @Provides
    @Singleton
    fun providePokeDexApiDataSource(
        service: ApiService
    ): PokeDexApiService {
        return PokeDexApiServiceImp(service)
    }

    @Provides
    @Singleton
    fun provideGetPokeDexListUseCase(
        pokeDexRepository: PokeDexRepository,
        dispatcher: CoroutineContext
    ): GetPokemonDetailedListUseCase {
        return GetPokemonDetailedListUseCaseImpl(pokeDexRepository, dispatcher)
    }

    @Provides
    @Singleton
    fun provideGetPokemonByNameUseCaseImpl(
        pokeDexRepository: PokeDexRepository,
        dispatcher: CoroutineContext
    ): GetPokemonByNameUseCase {
        return GetPokemonByNameUseCaseImpl(pokeDexRepository, dispatcher)
    }

    @Provides
    @Singleton
    fun provideGetEffectsUseCaseImpl(
        pokeDexRepository: PokeDexRepository,
        dispatcher: CoroutineContext
    ): GetEffectsUseCase {
        return GetEffectsUseCaseImpl(pokeDexRepository, dispatcher)
    }

}