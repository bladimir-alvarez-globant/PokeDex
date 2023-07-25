package com.bladoaepokedex.databasemanager.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bladoaepokedex.databasemanager.RoomConverters
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "pokemon")
@TypeConverters(RoomConverters::class)
@Parcelize
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String? = null,
    @Json(name = "abilities")
    val abilities: List<AbilityEntity>? = null,
    @Json(name = "sprites")
    val sprites: SpritesEntity? = null,
    @Json(name = "types")
    val types: List<TypeEntity>? = null
) : Parcelable