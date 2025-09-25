package com.example.pokemno.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemno.data.model.PokemonResult
import com.example.pokemno.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(private val repo: PokemonRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<List<PokemonResult>>(emptyList())
    val uiState: StateFlow<List<PokemonResult>> = _uiState

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        viewModelScope.launch {
            try {
                val response = repo.getPokemonList()
                _uiState.value = response.results
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
