package com.bladoae.pokedex.requestmanager.model.pokemon

import com.google.gson.annotations.SerializedName

data class SpritesDto(
    @SerializedName("front_default")
    val frontDefault: String? = null
)