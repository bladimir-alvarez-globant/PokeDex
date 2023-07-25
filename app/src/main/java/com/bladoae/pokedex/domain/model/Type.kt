package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.TypeDto
import com.bladoaepokedex.databasemanager.entities.TypeEntity

data class Type(
    val name: String? = null
)

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