package com.example.pokemno.data.repository

import com.example.pokemno.data.api.PokeApiService

class PokemonRepository(private val api: PokeApiService) {
    suspend fun getPokemonList(limit: Int = 100, offset: Int = 0) =
        api.listPokemon(limit, offset)

    suspend fun getPokemonDetail(name: String) =
        api.getPokemonDetail(name)
}
