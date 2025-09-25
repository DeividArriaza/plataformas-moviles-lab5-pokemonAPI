package com.example.pokemno.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    name: String,
    viewModel: PokemonDetailViewModel,
    onBack: () -> Unit
) {
    // Lanza la carga del Pokémon cuando cambia el nombre
    LaunchedEffect(name) {
        // método asumido existente en tu ViewModel
        viewModel.loadPokemon(name)
    }

    // Intentamos leer el estado de distintas formas (compatibilidad según tu VM)
    // Si tu VM usa `mutableStateOf(pokemon)`, accede con `.value`. Si usas StateFlow, reemplaza por collectAsState.
    val pokemonState = remember { mutableStateOf(viewModel.pokemon.value) }
    // Si tu ViewModel actualiza `pokemon.value`, esto se mantendrá consistente en la UI.
    // alternativa: si tu VM tiene StateFlow: val pokemonState by viewModel.pokemon.collectAsState()

    // Try to read from viewModel directly to avoid mismatch; fallback to pokemonState.value
    val pokemon = try {
        // prefer direct field if mutableStateOf
        viewModel.pokemon.value
    } catch (e: Exception) {
        pokemonState.value
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(name.replaceFirstChar { it.uppercaseChar() }) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            pokemon?.let { p ->
                Column(modifier = Modifier.fillMaxSize()) {
                    // Row 1: Front | Back
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Front", style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            AsyncImage(
                                model = p.sprites.front_default,
                                contentDescription = "Front",
                                modifier = Modifier.size(100.dp)
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Back", style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            AsyncImage(
                                model = p.sprites.back_default,
                                contentDescription = "Back",
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Row 2: Front Shiny | Back Shiny
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Front Shiny", style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            AsyncImage(
                                model = p.sprites.front_shiny,
                                contentDescription = "Front Shiny",
                                modifier = Modifier.size(100.dp)
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Back Shiny", style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            AsyncImage(
                                model = p.sprites.back_shiny,
                                contentDescription = "Back Shiny",
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                }
            } ?: run {
                // placeholder / loading
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Cargando detalle...", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
