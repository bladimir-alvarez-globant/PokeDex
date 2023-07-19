package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.PokeDexDto

data class PokeDex(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)

fun PokeDexDto.toPokeDex() = PokeDex(
    count,
    next,
    previous,
    results.toResult()
)