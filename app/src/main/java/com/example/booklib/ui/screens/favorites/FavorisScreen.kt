package com.example.booklib.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booklib.model.Book
import com.example.booklib.ui.components.BookItem
import com.example.booklib.ui.components.EmptyView

@Composable
fun FavorisScreen(
    favoris: List<Book>,
    onBookClick: (Book) -> Unit,
    onToggleFavori: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    if (favoris.isEmpty()) {
        EmptyView(
            message = "Aucun livre en favoris.\nAppuyez sur le cœur d'un livre pour l'ajouter.",
            modifier = modifier
        )
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(favoris) { book ->
                BookItem(
                    book = book,
                    favori = true,
                    onClick = { onBookClick(book) },
                    onToggleFavori = { onToggleFavori(book) }
                )
            }
        }
    }
}
