package com.bladoae.pokedex.domain.model.detail

import com.bladoae.pokedex.requestmanager.model.detail.EffectDto

data class Effect(
    val effectEntries: List<EffectEntries>? = null
)

fun EffectDto.toEffect() = Effect(
    effectEntries?.toEffectEntriesList()
)