package com.example.fetch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.fetch.ui.ItemsScreen
import com.example.fetch.ui.theme.FetchTheme
import com.example.fetch.ui.viewmodel.FetchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val fetchViewModel: FetchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    val items by fetchViewModel.itemList.collectAsState()

                    ItemsScreen(
                        items = items,
                        getItems = {
                            fetchViewModel.getItems()
                        },
                        paddingValues = padding,
                    )
                }
            }
        }
    }
}
