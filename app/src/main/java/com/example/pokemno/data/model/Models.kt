package com.example.pokemno.data.model

data class PokemonListResponse(
    val count: Int,
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)

data class PokemonDetail(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites
)

data class PokemonSprites(
    val front_default: String?,
    val back_default: String?,
    val front_shiny: String?,
    val back_shiny: String?
)
