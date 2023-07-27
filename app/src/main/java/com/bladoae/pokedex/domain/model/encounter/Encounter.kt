package com.bladoae.pokedex.domain.model.encounter

import com.bladoae.pokedex.requestmanager.model.encounter.EncounterDto

data class Encounter(
    val locationArea: LocationArea? = null
)

fun EncounterDto.toEncounter() = Encounter(
    locationArea?.toLocationArea()
)

fun List<EncounterDto>.toEncounterList() =
    map(EncounterDto::toEncounter)