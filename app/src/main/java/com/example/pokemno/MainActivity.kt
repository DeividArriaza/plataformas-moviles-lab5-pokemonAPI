package com.example.pokemno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemno.data.api.RetrofitInstance
import com.example.pokemno.data.repository.PokemonRepository
import com.example.pokemno.ui.detail.PokemonDetailScreen
import com.example.pokemno.ui.detail.PokemonDetailViewModel
import com.example.pokemno.ui.list.PokemonListScreen
import com.example.pokemno.ui.list.PokemonListViewModel
import com.example.pokemno.ui.theme.PokemnoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val repo = PokemonRepository(RetrofitInstance.api)

        setContent {
            PokemnoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        val vm = remember { PokemonListViewModel(repo) }
                        PokemonListScreen(
                            onPokemonSelected = { name -> navController.navigate("detail/$name") },
                            viewModel = vm
                        )
                    }
                    composable("detail/{name}") { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("name") ?: ""
                        val vm = remember { PokemonDetailViewModel(repo) }
                        PokemonDetailScreen(
                            name = name,
                            viewModel = vm,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
