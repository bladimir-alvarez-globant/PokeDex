package com.bladoae.pokedex.requestmanager.model

data class PokeDexDto(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<ResultDto>
)