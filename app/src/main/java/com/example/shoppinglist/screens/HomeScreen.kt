package com.example.shoppinglist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.* // Import Material3 baru
import androidx.compose.runtime.* // Import State compose
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
    onAddItem: () -> Unit,
    // Parameter baru untuk edit/delete
    onEditItem: (oldItem: String, newItem: String) -> Unit,
    onDeleteItem: (item: String) -> Unit
) {
    // State untuk mengontrol dialog edit
    var itemToEdit by remember { mutableStateOf<String?>(null) }
    // State untuk nilai field di dalam dialog
    var editText by remember { mutableStateOf("") }

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

        // Teruskan callback long press ke ShoppingList
        ShoppingList(
            items = shoppingItems,
            onItemLongPress = { item ->
                itemToEdit = item // Set item yang akan diedit (buka dialog)
                editText = item  // Set teks awal
            }
        )
    }

    // Tampilkan Dialog jika itemToEdit tidak null
    if (itemToEdit != null) {
        AlertDialog(
            onDismissRequest = {
                itemToEdit = null // Tutup dialog
            },
            title = { Text("Edit Item") },
            text = {
                OutlinedTextField(
                    value = editText,
                    onValueChange = { editText = it },
                    label = { Text("Nama Item") },
                    // Set fokus otomatis (opsional)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (editText.isNotBlank()) {
                            // Panggil callback edit
                            onEditItem(itemToEdit!!, editText)
                        }
                        itemToEdit = null // Tutup dialog
                    },
                    enabled = editText.isNotBlank()
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Tombol Hapus (di sebelah kiri)
                    TextButton(onClick = {
                        // Panggil callback delete
                        onDeleteItem(itemToEdit!!)
                        itemToEdit = null // Tutup dialog
                    }) {
                        Text("Hapus", color = MaterialTheme.colorScheme.error)
                    }
                    // Spacer untuk memisahkan
                    Spacer(modifier = Modifier.width(8.dp))
                    // Tombol Batal (di sebelah kanan)
                    TextButton(onClick = { itemToEdit = null }) {
                        Text("Batal")
                    }
                }
            }
        )
    }
}