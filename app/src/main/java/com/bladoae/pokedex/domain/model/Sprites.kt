package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.SpritesDto
import com.bladoaepokedex.databasemanager.entities.SpritesEntity
import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String? = null
)

fun SpritesDto.toSprites() = Sprites(
    frontDefault
)

fun Sprites.toSpritesEntity() = SpritesEntity(
    frontDefault
)

fun SpritesEntity.fromEntityToSprites() = Sprites(
    frontDefault
)