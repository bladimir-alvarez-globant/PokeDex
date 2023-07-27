package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.EffectEntriesDto

data class EffectEntries(
    val effect: String? = "",
    val language: Language? = null
)

fun EffectEntriesDto.toEffectEntries() = EffectEntries(
    effect,
    language?.toLanguage()
)

fun List<EffectEntriesDto>.toEffectEntriesList() =
    map(EffectEntriesDto::toEffectEntries)
