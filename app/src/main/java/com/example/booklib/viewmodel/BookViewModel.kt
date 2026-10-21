package com.example.booklib.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklib.data.BookRepository
import com.example.booklib.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    val repository = BookRepository()

    val books = MutableStateFlow<List<Book>>(emptyList())
    val chargement = MutableStateFlow(false)
    val erreur = MutableStateFlow<String?>(null)
    val favoris = MutableStateFlow<List<Book>>(emptyList())

    fun toggleFavori(book: Book) {
        favoris.value = if (book in favoris.value) {
            favoris.value - book
        } else {
            favoris.value + book
        }
    }

    init {
        loadInitialBooks()
    }

    fun loadInitialBooks() {
        viewModelScope.launch {
            chargement.value = true
            erreur.value = null
            try {
                books.value = repository.getInitialBooks()
            } catch (e: Exception) {
                erreur.value = "Impossible de charger les livres"
            }
            chargement.value = false
        }
    }

    fun search(query: String) {
        if (query.isBlank()) {
            loadInitialBooks()
            return
        }
        viewModelScope.launch {
            chargement.value = true
            erreur.value = null
            try {
                books.value = repository.searchBooks(query)
            } catch (e: Exception) {
                erreur.value = "Impossible de charger les livres"
            }
            chargement.value = false
        }
    }
}