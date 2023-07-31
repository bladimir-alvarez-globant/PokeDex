package com.bladoae.pokedex.domain.model.pokemon

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sprites(
    val frontDefault: String? = null
) : Parcelable