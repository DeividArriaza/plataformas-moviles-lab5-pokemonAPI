package com.example.pokemno.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokemno.data.model.PokemonResult

/**
 * Extrae el id numérico al final de la URL devuelta por la API:
 * Ej: https://pokeapi.co/api/v2/pokemon/25/  -> "25"
 */
private fun extractIdFromUrl(url: String): String {
    return url.trimEnd('/').split('/').lastOrNull() ?: ""
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    onPokemonSelected: (String) -> Unit,
    viewModel: PokemonListViewModel
) {
    // Usamos collectAsState() en vez de .value directo (más correcto en Compose)
    val list by viewModel.uiState.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokédex") }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            when {
                loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Cargando...", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Error: $error",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(list) { p: PokemonResult ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clickable { onPokemonSelected(p.name) },
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                ) {
                                    val id = extractIdFromUrl(p.url)

                                    AsyncImage(
                                        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
                                        contentDescription = p.name,
                                        modifier = Modifier.size(48.dp)
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Text(
                                        text = p.name.replaceFirstChar { it.uppercaseChar() },
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
