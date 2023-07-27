package com.bladoae.pokedex.domain.model.pokemon

import android.os.Parcelable
import com.bladoae.pokedex.requestmanager.model.PokemonDto
import com.bladoaepokedex.databasemanager.entities.PokemonEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val id: Int = 0,
    val name: String? = null,
    val abilities: List<Ability>? = null,
    val sprites: Sprites? = null,
    val types: List<Type>? = null,
    val weight: Int? = null,
    val height: Int? = null
) : Parcelable

fun PokemonDto.toPokemon() = Pokemon(
    id,
    name = name,
    abilities = abilities?.toAbilityList(),
    sprites = sprites?.toSprites(),
    types = types?.toTypeList(),
    weight,
    height
)

fun List<PokemonDto>.toPokemonList(): List<Pokemon> =
    map(PokemonDto::toPokemon)

fun Pokemon.toPokemonEntity() = PokemonEntity(
    id,
    name,
    abilities?.toAbilityEntityList(),
    sprites?.toSpritesEntity(),
    types?.toTypeEntityList(),
    weight,
    height
)

fun List<Pokemon>.toPokemonEntityList(): List<PokemonEntity> =
    map(Pokemon::toPokemonEntity)

fun PokemonEntity.toPokemon() = Pokemon(
    id,
    name,
    abilities?.fromEntityToAbilityList(),
    sprites?.fromEntityToSprites(),
    types?.fromEntityToTypeList(),
    weight,
    height
)

fun List<PokemonEntity>.fromListEntityToPokemonList() =
    map(PokemonEntity::toPokemon)