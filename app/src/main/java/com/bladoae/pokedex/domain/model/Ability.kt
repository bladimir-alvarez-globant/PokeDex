package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.AbilityDto
import com.bladoaepokedex.databasemanager.entities.AbilityEntity

data class Ability(
    val name: String? = null
)

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