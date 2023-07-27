package com.bladoae.pokedex.requestmanager.model.encounter

import com.google.gson.annotations.SerializedName

data class EncounterDto(
    @SerializedName("location_area")
    val locationArea: LocationAreaDto? = null
)
