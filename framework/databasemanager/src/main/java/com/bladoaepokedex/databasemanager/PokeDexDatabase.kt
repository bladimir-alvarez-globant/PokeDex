package com.bladoaepokedex.databasemanager

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bladoaepokedex.databasemanager.daos.PokemonDao
import com.bladoaepokedex.databasemanager.entities.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokeDexDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "pokedex_db"
    }

    abstract fun pokemonDao(): PokemonDao

}