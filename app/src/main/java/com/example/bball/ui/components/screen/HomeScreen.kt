package com.example.bball.ui.components.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bball.models.Match
import com.example.bball.ui.components.Card.MatchCard
import com.example.bball.ui.components.layout.LoadingComponent
import com.example.bball.viewmodels.HomeUiState
import com.example.bball.viewmodels.HomeViewModel


@Composable
fun HomeScreen(vm : HomeViewModel = HomeViewModel()) {

    when (vm.state) {
        is HomeUiState.Error -> ErrorScreen(message = (vm.state as HomeUiState.Error).message)
        HomeUiState.Loading -> LoadingComponent()
        is HomeUiState.Success -> {
            Log.d("HomeScreen Info", "Success")
            MatchView(
                last = (vm.state as HomeUiState.Success).matches.previousGame,
                next = (vm.state as HomeUiState.Success).matches.nextGame
            )
        }

        else -> {}
    }
}

@Composable
fun MatchView(last: Match, next: Match) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Résultat", style = MaterialTheme.typography.headlineMedium)
            MatchCard(
                match = last,
                onClick = { id -> println("Match $id cliqué") }
            )

        Text(text = "Prochain Match", style = MaterialTheme.typography.headlineMedium)
            MatchCard(
                match = next,
                onClick = { id -> println("Match $id cliqué") }
            )
    }
}


@Preview
@Composable
fun ViewHomePage() {
    HomeScreen()
}