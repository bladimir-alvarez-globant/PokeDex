package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.AbilityDto

data class Ability(
    val name: String? = null
)

fun AbilityDto.toAbility() = Ability(
    name = ability?.name
)

fun List<AbilityDto>.toAbilityList(): List<Ability> =
    map(AbilityDto::toAbility)