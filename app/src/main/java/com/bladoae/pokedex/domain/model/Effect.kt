package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.EffectDto

data class Effect(
    val effectEntries: List<EffectEntries>? = null
)

fun EffectDto.toEffect() = Effect(
    effectEntries?.toEffectEntriesList()
)