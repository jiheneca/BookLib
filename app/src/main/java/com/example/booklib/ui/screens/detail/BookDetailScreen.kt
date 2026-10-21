package com.example.booklib.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.booklib.model.Book
import com.example.booklib.ui.components.BookCover
import com.example.booklib.ui.util.authorsText

@Composable
fun BookDetailScreen(
    book: Book,
    favori: Boolean,
    onToggleFavori: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookCover(book = book, width = 180.dp)

        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = book.authorsText(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )

        Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp)) {
            book.first_publish_year?.let { InfoRow("Première publication", it.toString()) }
            book.edition_count?.let { InfoRow("Nombre d'éditions", it.toString()) }
        }

        Button(
            onClick = onToggleFavori,
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            colors = if (favori) ButtonDefaults.filledTonalButtonColors() else ButtonDefaults.buttonColors()
        ) {
            Icon(Icons.Default.Favorite, contentDescription = null)
            Text(
                text = if (favori) "Retirer des favoris" else "Ajouter aux favoris",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
