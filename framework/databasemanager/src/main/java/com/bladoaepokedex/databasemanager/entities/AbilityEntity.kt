package com.bladoaepokedex.databasemanager.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AbilityEntity(
    val name: String? = null
) : Parcelable