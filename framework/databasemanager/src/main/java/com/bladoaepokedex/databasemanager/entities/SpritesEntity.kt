package com.bladoaepokedex.databasemanager.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpritesEntity(
    val frontDefault: String? = null
) : Parcelable