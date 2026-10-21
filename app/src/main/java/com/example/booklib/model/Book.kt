package com.example.booklib.model

import kotlinx.serialization.Serializable

@Serializable
class SearchResponse(
    val numFound: Int = 0,
    val docs: List<Book> = emptyList()
)

@Serializable
data class Book(
    val key: String,
    val title: String,
    val author_name: List<String>? = null,
    val first_publish_year: Int? = null,
    val cover_i: Int? = null,
    val edition_count: Int? = null
) {
    val coverUrl: String?
        get() = cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
}