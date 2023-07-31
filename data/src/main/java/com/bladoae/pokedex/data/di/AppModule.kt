package com.bladoae.pokedex.data.di

import com.bladoae.pokedex.data.apiservice.PokeDexApiService
import com.bladoae.pokedex.data.apiservice.PokeDexApiServiceImp
import com.bladoae.pokedex.requestmanager.ApiService
import com.bladoae.pokedex.requestmanager.requestmanager.ServiceGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  AppModule {

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

}