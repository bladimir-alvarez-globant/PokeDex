package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.TypeDto

data class Type(
    val name: String? = null
)

fun TypeDto.toType() = Type(
    name = type?.name
)

fun List<TypeDto>.toTypeList() =
    map(TypeDto::toType)