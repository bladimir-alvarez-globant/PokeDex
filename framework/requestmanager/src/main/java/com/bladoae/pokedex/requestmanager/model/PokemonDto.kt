package com.bladoae.pokedex.requestmanager.model

data class PokemonDto(
    val id: Int = 0,
    val name: String? = null,
    val abilities: List<AbilityDto>? = null,
    val sprites: SpritesDto? = null,
    val types: List<TypeDto>? = null
)
