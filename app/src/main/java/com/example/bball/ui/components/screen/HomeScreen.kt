package com.example.bball.ui.components.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bball.models.sampleMatches
import com.example.bball.ui.components.Card.MatchCard
import com.example.bball.ui.components.layout.AppTopBar


@Composable
fun HomeScreen() {
    // Exemple simple
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Prochain Match", style = MaterialTheme.typography.headlineMedium)
        MatchCard(
            match = sampleMatches[1],
            onClick = { id -> println("Match $id cliqué") }
        )

        Text(text = "Dernier Match", style = MaterialTheme.typography.headlineMedium)
        MatchCard(
            match = sampleMatches[0],
            onClick = { id -> println("Match $id cliqué") }
        )
        
    }
}



@Preview
@Composable
fun ViewHomePage() {
    HomeScreen()
}