package com.example.booklib.ui.util

import com.example.booklib.model.Book

fun Book.authorsText(max: Int = 5): String {
    val auteurs = author_name.orEmpty()
    return when {
        auteurs.isEmpty() -> "Auteur inconnu"
        auteurs.size > max -> auteurs.take(max).joinToString(", ") + " et ${auteurs.size - max} autres"
        else -> auteurs.joinToString(", ")
    }
}
