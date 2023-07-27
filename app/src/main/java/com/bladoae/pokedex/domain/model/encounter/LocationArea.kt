package com.bladoae.pokedex.domain.model.encounter

import com.bladoae.pokedex.requestmanager.model.encounter.LocationAreaDto

data class LocationArea(
    val name: String? = null
)

fun LocationAreaDto.toLocationArea() = LocationArea(
    name
)