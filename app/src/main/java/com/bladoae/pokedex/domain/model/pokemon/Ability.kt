package com.bladoae.pokedex.domain.model.pokemon

import android.os.Parcelable
import com.bladoae.pokedex.requestmanager.model.pokemon.AbilityDto
import com.bladoaepokedex.databasemanager.entities.AbilityEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ability(
    val name: String? = null
) : Parcelable

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