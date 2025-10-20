// MainScreen.kt

package com.example.shoppinglist

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.navigation.BottomBar
import com.example.shoppinglist.navigation.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var newItemText by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    // --- PERUBAHAN DI SINI: Inisialisasi dengan daftar kosong ---
    // Ganti: val shoppingItems = remember { mutableStateListOf("Susu Segar", "Roti Tawar", "Telur Ayam") }
    val shoppingItems = remember { mutableStateListOf<String>() }
    // -------------------------------------------------------------

    val filteredItems = if (searchQuery.isBlank()) {
        shoppingItems
    } else {
        shoppingItems.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    // Logic Edit dan Delete Item (Tetap sama)
    val onEditItem: (String, String) -> Unit = { oldItem, newItem ->
        val index = shoppingItems.indexOf(oldItem)
        if (index != -1) {
            shoppingItems[index] = newItem
        }
    }

    val onDeleteItem: (String) -> Unit = { item ->
        shoppingItems.remove(item)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    onNavigateToSettings = {
                        navController.navigate(Screen.Setting.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                        }
                    },
                    closeDrawer = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            bottomBar = { BottomBar(navController = navController) },
            topBar = {
                TopAppBar(
                    title = { Text("Shopping List App") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Buka Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                AppNavigation(
                    navController = navController,
                    newItemText = newItemText,
                    onNewItemTextChange = { newItemText = it },
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    filteredItems = filteredItems,
                    onAddItem = {
                        if (newItemText.isNotBlank()) {
                            shoppingItems.add(0, newItemText)
                            newItemText = ""
                        }
                    },
                    onEditItem = onEditItem,
                    onDeleteItem = onDeleteItem
                )
            }
        }
    }
}