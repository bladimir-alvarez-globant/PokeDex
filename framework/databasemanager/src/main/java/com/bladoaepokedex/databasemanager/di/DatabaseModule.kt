package com.bladoaepokedex.databasemanager.di

import android.content.Context
import androidx.room.Room
import com.bladoaepokedex.databasemanager.PokeDexDatabase
import com.bladoaepokedex.databasemanager.RoomConverters
import com.bladoaepokedex.databasemanager.daos.PokemonDao
import com.bladoaepokedex.databasemanager.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, moshi: Moshi): PokeDexDatabase {
        return Room.databaseBuilder(
            context,
            PokeDexDatabase::class.java,
            PokeDexDatabase.DATABASE_NAME
        )
            .addTypeConverter(RoomConverters(moshi))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideDataDictionaryDao(pavenDatabase: PokeDexDatabase): PokemonDao =
        pavenDatabase.pokemonDao()

}