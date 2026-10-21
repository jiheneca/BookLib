package com.example.booklib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.booklib.navigation.BookNavigation
import com.example.booklib.ui.theme.BookLibTheme
import com.example.booklib.viewmodel.BookViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookLibTheme {
                BookNavigation(viewModel)
            }
        }
    }
}
