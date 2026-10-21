package com.example.booklib.navigation

import com.example.booklib.model.Book


sealed interface TopLevelDestination {
    val label: String
}

data object ListeDestination : TopLevelDestination {
    override val label = "Accueil"
}

data object FavorisDestination : TopLevelDestination {
    override val label = "Favoris"
}


data class DetailDestination(val book: Book)
