package com.bladoae.pokedex.domain.model.pokemon

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val id: Int = 0,
    val name: String? = null,
    val abilities: List<Ability>? = null,
    val sprites: Sprites? = null,
    val types: List<Type>? = null,
    val weight: Int? = null,
    val height: Int? = null
) : Parcelable