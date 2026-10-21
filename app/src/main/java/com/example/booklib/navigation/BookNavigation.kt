package com.example.booklib.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.booklib.ui.screens.detail.BookDetailScreen
import com.example.booklib.ui.screens.favorites.FavorisScreen
import com.example.booklib.ui.screens.list.BookListScreen
import com.example.booklib.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookNavigation(viewModel: BookViewModel) {
    val backStack = remember { mutableStateListOf<Any>(ListeDestination) }
    val favoris by viewModel.favoris.collectAsStateWithLifecycle()

    val current = backStack.lastOrNull()
    val isDetail = current is DetailDestination


    fun switchTab(dest: TopLevelDestination) {
        if (current == dest) return
        backStack.clear()
        backStack.add(ListeDestination)
        if (dest != ListeDestination) backStack.add(dest)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        when (current) {
                            is FavorisDestination -> "Mes favoris"
                            is DetailDestination -> "Détails"
                            else -> "BookLib"
                        }
                    )
                },
                navigationIcon = {
                    if (isDetail || current == FavorisDestination) {
                        IconButton(onClick = { backStack.removeLastOrNull() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (!isDetail) {
                NavigationBar {
                    NavigationBarItem(
                        selected = current == ListeDestination,
                        onClick = { switchTab(ListeDestination) },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text(ListeDestination.label) }
                    )
                    NavigationBarItem(
                        selected = current == FavorisDestination,
                        onClick = { switchTab(FavorisDestination) },
                        icon = {
                            BadgedBox(badge = {
                                if (favoris.isNotEmpty()) Badge { Text(favoris.size.toString()) }
                            }) {
                                Icon(Icons.Default.Favorite, contentDescription = null)
                            }
                        },
                        label = { Text(FavorisDestination.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backStack,
            entryProvider = entryProvider {
                entry<ListeDestination> {
                    BookListScreen(
                        viewModel = viewModel,
                        onBookClick = { backStack.add(DetailDestination(it)) }
                    )
                }
                entry<FavorisDestination> {
                    FavorisScreen(
                        favoris = favoris,
                        onBookClick = { backStack.add(DetailDestination(it)) },
                        onToggleFavori = viewModel::toggleFavori
                    )
                }
                entry<DetailDestination> { key ->
                    BookDetailScreen(
                        book = key.book,
                        favori = key.book in favoris,
                        onToggleFavori = { viewModel.toggleFavori(key.book) }
                    )
                }
            }
        )
    }
}
