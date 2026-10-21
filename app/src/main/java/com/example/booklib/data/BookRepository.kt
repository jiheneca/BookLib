package com.example.booklib.data

import android.util.Log
import com.example.booklib.model.Book
import com.example.booklib.model.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BookRepository {

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Ktor-Logger", message)
                }
            }
        }
    }

    suspend fun searchBooks(query: String): List<Book> {
        val url = "https://openlibrary.org/search.json"
        val reponse: SearchResponse = client.get(url) {
            url {
                parameters.append("q", query)
                parameters.append("limit", "50")
            }
        }.body()
        return reponse.docs
    }

    suspend fun getInitialBooks(): List<Book> {
        return searchBooks("fiction")
    }
}