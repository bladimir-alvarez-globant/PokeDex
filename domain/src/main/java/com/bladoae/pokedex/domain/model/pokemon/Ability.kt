package com.bladoae.pokedex.domain.model.pokemon

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ability(
    val name: String? = null
) : Parcelable