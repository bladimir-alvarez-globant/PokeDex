package com.bladoae.pokedex.data.mappers

import com.bladoae.pokedex.domain.model.detail.Effect
import com.bladoae.pokedex.domain.model.detail.EffectEntries
import com.bladoae.pokedex.domain.model.detail.Language
import com.bladoae.pokedex.domain.model.encounter.Encounter
import com.bladoae.pokedex.domain.model.encounter.LocationArea
import com.bladoae.pokedex.domain.model.pokemon.Ability
import com.bladoae.pokedex.domain.model.pokemon.Pokemon
import com.bladoae.pokedex.domain.model.pokemon.Sprites
import com.bladoae.pokedex.domain.model.pokemon.Type
import com.bladoae.pokedex.requestmanager.model.detail.EffectDto
import com.bladoae.pokedex.requestmanager.model.detail.EffectEntriesDto
import com.bladoae.pokedex.requestmanager.model.detail.LanguageDto
import com.bladoae.pokedex.requestmanager.model.encounter.EncounterDto
import com.bladoae.pokedex.requestmanager.model.encounter.LocationAreaDto
import com.bladoae.pokedex.requestmanager.model.pokemon.AbilityDto
import com.bladoae.pokedex.requestmanager.model.pokemon.PokemonDto
import com.bladoae.pokedex.requestmanager.model.pokemon.SpritesDto
import com.bladoae.pokedex.requestmanager.model.pokemon.TypeDto
import com.bladoaepokedex.databasemanager.entities.AbilityEntity
import com.bladoaepokedex.databasemanager.entities.PokemonEntity
import com.bladoaepokedex.databasemanager.entities.SpritesEntity
import com.bladoaepokedex.databasemanager.entities.TypeEntity

fun EffectDto.toEffect() = Effect(
    effectEntries?.toEffectEntriesList()
)

fun EffectEntriesDto.toEffectEntries() = EffectEntries(
    effect,
    language?.toLanguage()
)

fun List<EffectEntriesDto>.toEffectEntriesList() =
    map(EffectEntriesDto::toEffectEntries)

fun LanguageDto.toLanguage() = Language(
    name
)

fun EncounterDto.toEncounter() = Encounter(
    locationArea?.toLocationArea()
)

fun List<EncounterDto>.toEncounterList() =
    map(EncounterDto::toEncounter)

fun LocationAreaDto.toLocationArea() = LocationArea(
    name
)

//Ability

fun AbilityDto.toAbility() = Ability(
    name = ability?.name
)

fun List<AbilityDto>.toAbilityList(): List<Ability> =
    map(AbilityDto::toAbility)

fun Ability.toAbilityEntity() = AbilityEntity(
    name
)

fun List<Ability>.toAbilityEntityList(): List<AbilityEntity> =
    map(Ability::toAbilityEntity)

fun AbilityEntity.fromEntityToAbility() = Ability(
    name
)

fun List<AbilityEntity>.fromEntityToAbilityList(): List<Ability> =
    map(AbilityEntity::fromEntityToAbility)

//Pokemon

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

//Sprites

fun SpritesDto.toSprites() = Sprites(
    frontDefault
)

fun Sprites.toSpritesEntity() = SpritesEntity(
    frontDefault
)

fun SpritesEntity.fromEntityToSprites() = Sprites(
    frontDefault
)

//Type

fun TypeDto.toType() = Type(
    name = type?.name
)

fun List<TypeDto>.toTypeList() =
    map(TypeDto::toType)

fun Type.toTypeEntity() = TypeEntity(
    name
)

fun List<Type>.toTypeEntityList() =
    map(Type::toTypeEntity)

fun TypeEntity.fromEntityToType() = Type(
    name
)

fun List<TypeEntity>.fromEntityToTypeList() =
    map(TypeEntity::fromEntityToType)