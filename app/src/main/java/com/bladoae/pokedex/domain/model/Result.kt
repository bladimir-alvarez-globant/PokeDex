package com.bladoae.pokedex.domain.model

import com.bladoae.pokedex.requestmanager.model.ResultDto

data class Result(
    val name: String,
    val url: String
)

fun ResultDto.toResult() = Result(
    name,
    url
)

fun List<ResultDto>.toResult(): List<Result> = map(ResultDto::toResult)