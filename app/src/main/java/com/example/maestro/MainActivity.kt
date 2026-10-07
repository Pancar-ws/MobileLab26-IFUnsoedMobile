package com.example.maestro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.maestro.ui.screen.DetailScreen
import com.example.maestro.ui.screen.HomeScreen
import com.example.maestro.ui.theme.MaestroTheme
import com.example.maestro.ui.viewmodel.GempaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaestroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GempaAppNavigation()
                }
            }
        }
    }
}

/**
 * NavHost Navigation Compose (Maksimal 2 Screens: Home & Detail)
 */
@Composable
fun GempaAppNavigation(
    viewModel: GempaViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Screen 1: Home Screen
        composable(route = "home") {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = { gempaIndex ->
                    navController.navigate("detail/$gempaIndex")
                }
            )
        }

        // Screen 2: Detail Screen
        composable(
            route = "detail/{gempaIndex}",
            arguments = listOf(
                navArgument("gempaIndex") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val gempaIndex = backStackEntry.arguments?.getInt("gempaIndex") ?: 0
            val gempa = viewModel.getGempaByIndex(gempaIndex)

            DetailScreen(
                gempa = gempa,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}