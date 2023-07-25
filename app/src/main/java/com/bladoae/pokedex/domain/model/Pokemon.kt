package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.PokemonDto
import com.bladoaepokedex.databasemanager.entities.PokemonEntity

data class Pokemon(
    val id: Int = 0,
    val name: String? = null,
    val abilities: List<Ability>? = null,
    val sprites: Sprites? = null,
    val types: List<Type>? = null
)

fun PokemonDto.toPokemon() = Pokemon(
    id,
    name = name,
    abilities = abilities?.toAbilityList(),
    sprites = sprites?.toSprites(),
    types = types?.toTypeList()
)

fun List<PokemonDto>.toPokemonList(): List<Pokemon> =
    map(PokemonDto::toPokemon)

fun Pokemon.toPokemonEntity() = PokemonEntity(
    id,
    name,
    abilities?.toAbilityEntityList(),
    sprites?.toSpritesEntity(),
    types?.toTypeEntityList()
)

fun List<Pokemon>.toPokemonEntityList(): List<PokemonEntity> =
    map(Pokemon::toPokemonEntity)

fun PokemonEntity.toPokemon() = Pokemon(
    id,
    name,
    abilities?.fromEntityToAbilityList(),
    sprites?.fromEntityToSprites(),
    types?.fromEntityToTypeList()
)