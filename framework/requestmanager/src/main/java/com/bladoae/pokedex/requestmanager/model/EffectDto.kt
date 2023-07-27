package com.bladoae.pokedex.requestmanager.model

import com.google.gson.annotations.SerializedName

data class EffectDto(
    @SerializedName("effect_entries")
    val effectEntries: List<EffectEntriesDto>? = null
)
