package com.bladoae.pokedex.domain.model.pokemon

import android.os.Parcelable
import com.bladoae.pokedex.requestmanager.model.pokemon.TypeDto
import com.bladoaepokedex.databasemanager.entities.TypeEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Type(
    val name: String? = null
) : Parcelable

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