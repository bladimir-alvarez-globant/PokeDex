package com.bladoae.pokedex.requestmanager.model

data class PokeDexDto(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<ResultDto>? = null
)