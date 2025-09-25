package com.example.pokemno.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemno.data.model.PokemonDetail
import com.example.pokemno.data.repository.PokemonRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class PokemonDetailViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemon = mutableStateOf<PokemonDetail?>(null)
    val pokemon: State<PokemonDetail?> = _pokemon

    fun loadPokemon(name: String) {
        viewModelScope.launch {
            try {
                val result = repository.getPokemonDetail(name)
                _pokemon.value = result
            } catch (e: Exception) {
                e.printStackTrace()
                _pokemon.value = null
            }
        }
    }
}
