package com.example.fetch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetch.model.AppException

@Composable
fun ErrorScreen(exception: Exception) {
    Column (
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Error", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(30.dp))

        val message = when(exception) {
            is AppException -> exception.description
            else -> exception.message ?: exception.cause?.message ?: exception.javaClass.name
        }
        Text(message, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview
@Composable
fun PreviewErrorScreen() {
    ErrorScreen(Exception("Test exception"))
}

