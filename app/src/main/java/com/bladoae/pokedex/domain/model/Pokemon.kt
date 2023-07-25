package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.PokemonDto

data class Pokemon(
    val name: String? = null,
    val abilities: List<Ability>? = null,
    val sprites: Sprites? = null,
    val types: List<Type>? = null
)

fun PokemonDto.toPokemon() = Pokemon(
    name = name,
    abilities = abilities?.toAbilityList(),
    sprites = sprites?.toSprites(),
    types = types?.toTypeList()
)

fun List<PokemonDto>.toPokemonList(): List<Pokemon> =
    map(PokemonDto::toPokemon)