package com.bladoae.pokedex.domain.model.detail

import com.bladoae.pokedex.requestmanager.model.detail.LanguageDto

data class Language(
    val name: String? = null
)

fun LanguageDto.toLanguage() = Language(
    name
)