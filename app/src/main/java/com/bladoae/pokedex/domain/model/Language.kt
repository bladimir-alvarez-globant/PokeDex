package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.LanguageDto

data class Language(
    val name: String? = null
)

fun LanguageDto.toLanguage() = Language(
    name
)