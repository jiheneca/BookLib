package com.example.booklib.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.booklib.model.Book
import com.example.booklib.ui.components.BookItem
import com.example.booklib.ui.components.EmptyView
import com.example.booklib.ui.components.ErrorView
import com.example.booklib.ui.components.LoadingView
import com.example.booklib.ui.components.SearchBar
import com.example.booklib.viewmodel.BookViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.Text
@Composable
fun BookListScreen(
    viewModel: BookViewModel,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val books by viewModel.books.collectAsStateWithLifecycle()
    val chargement by viewModel.chargement.collectAsStateWithLifecycle()
    val erreur by viewModel.erreur.collectAsStateWithLifecycle()
    val favoris by viewModel.favoris.collectAsStateWithLifecycle()

    var recherche by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        SearchBar(
            query = recherche,
            onQueryChange = { recherche = it },
            onSearch = { viewModel.search(recherche) },
            modifier = Modifier.padding(vertical = 12.dp)
        )
        Button(
            onClick = { viewModel.search(recherche) },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Rechercher")
        }
        when {
            chargement -> LoadingView()
            erreur != null -> ErrorView(
                message = erreur.orEmpty(),
                onRetry = viewModel::loadInitialBooks
            )
            books.isEmpty() -> EmptyView("Aucun livre trouvé")
            else -> LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(books) { book ->
                    BookItem(
                        book = book,
                        favori = book in favoris,
                        onClick = { onBookClick(book) },
                        onToggleFavori = { viewModel.toggleFavori(book) }
                    )
                }
            }
        }
    }
}
