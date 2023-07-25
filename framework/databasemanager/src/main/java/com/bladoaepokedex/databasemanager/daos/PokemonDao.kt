package com.bladoaepokedex.databasemanager.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bladoaepokedex.databasemanager.entities.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(goalPreviewEntity: List<PokemonEntity>)

    @Query("DELETE FROM pokemon")
    fun deletePokemon(): Int

    @Query("SELECT * FROM Pokemon WHERE name == :name")
    suspend fun selectPokemon(name: String): PokemonEntity?

}