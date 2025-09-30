package com.example.shoppinglist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.components.ItemInput
import com.example.shoppinglist.components.SearchInput
import com.example.shoppinglist.components.ShoppingList
import com.example.shoppinglist.components.Title

@Composable
fun HomeScreen(
    newItemText: String,
    onNewItemTextChange: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    shoppingItems: List<String>,
    onAddItem: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Title()
        ItemInput(
            text = newItemText,
            onTextChange = onNewItemTextChange,
            onAddItem = onAddItem
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchInput(query = searchQuery, onQueryChange = onSearchQueryChange)
        Spacer(modifier = Modifier.height(16.dp))
        ShoppingList(items = shoppingItems)
    }
}