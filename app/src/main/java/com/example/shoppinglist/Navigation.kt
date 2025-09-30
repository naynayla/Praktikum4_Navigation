package com.example.shoppinglist

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.example.shoppinglist.screens.HomeScreen
import com.example.shoppinglist.screens.ProfileScreen
import com.example.shoppinglist.screens.SettingScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Setting : Screen("setting")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    newItemText: String,
    onNewItemTextChange: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    filteredItems: List<String>,
    onAddItem: () -> Unit
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                newItemText = newItemText,
                onNewItemTextChange = onNewItemTextChange,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                shoppingItems = filteredItems,
                onAddItem = onAddItem
            )
        }
        composable(Screen.Profile.route) { ProfileScreen() }
        composable(Screen.Setting.route) { SettingScreen() }
    }
}