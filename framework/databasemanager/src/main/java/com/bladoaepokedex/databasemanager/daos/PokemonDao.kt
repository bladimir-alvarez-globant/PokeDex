package com.bladoaepokedex.databasemanager.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bladoaepokedex.databasemanager.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(goalPreviewEntity: List<PokemonEntity>)

    @Query("DELETE FROM pokemon")
    fun deletePokemon(): Int

    @Query("SELECT * FROM pokemon WHERE name == :name")
    suspend fun selectPokemon(name: String): List<PokemonEntity>

}