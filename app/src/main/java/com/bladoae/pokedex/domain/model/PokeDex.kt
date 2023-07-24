package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.PokeDexDto

data class PokeDex(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<Result>? = null
)

fun PokeDexDto.toPokeDex() = PokeDex(
    count,
    next,
    previous,
    results?.toResult()
)