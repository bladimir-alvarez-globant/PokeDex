package com.bladoae.pokedex.domain.model.detail

import com.bladoae.pokedex.requestmanager.model.detail.EffectEntriesDto

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
