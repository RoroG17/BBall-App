package com.example.bball.ui.components.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bball.R
import com.example.bball.models.Match
import com.example.bball.ui.components.card.MatchCard
import com.example.bball.ui.components.layout.LoadingComponent
import com.example.bball.viewmodels.HomeUiState
import com.example.bball.viewmodels.HomeViewModel


@Composable
fun HomeScreen(vm : HomeViewModel = HomeViewModel(), navController: NavController) {

    when (vm.state) {
        is HomeUiState.Error -> ErrorScreen(message = (vm.state as HomeUiState.Error).message)
        HomeUiState.Loading -> LoadingComponent()
        is HomeUiState.Success -> {
            MatchView(
                last = (vm.state as HomeUiState.Success).matches.previousGame,
                next = (vm.state as HomeUiState.Success).matches.nextGame,
                navController = navController
            )
        }
    }
}

@Composable
fun MatchView(last: Match, next: Match, navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = stringResource(R.string.previous_game_title), style = MaterialTheme.typography.headlineMedium)
            MatchCard(
                match = last,
                onClick = { matchId ->
                    navController.navigate("match_detail/$matchId")
                }
            )

        Text(text = stringResource(R.string.next_game_title), style = MaterialTheme.typography.headlineMedium)
            MatchCard(
                match = next,
                onClick = {matchId ->
                    navController.navigate("match_detail/$matchId")
                }
            )
    }
}