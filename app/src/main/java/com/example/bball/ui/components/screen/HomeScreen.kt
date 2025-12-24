package com.example.bball.ui.components.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bball.R
import com.example.bball.models.Match
import com.example.bball.ui.components.card.MatchCard
import com.example.bball.ui.components.layout.LoadingComponent
import com.example.bball.ui.components.layout.SectionTitle
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
fun MatchView(
    last: Match,
    next: Match,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        SectionTitle(text = stringResource(R.string.previous_game_title))

        MatchCard(
            match = last,
            onClick = { matchId ->
                navController.navigate("match_detail/$matchId")
            }
        )

        SectionTitle(text = stringResource(R.string.next_game_title))

        MatchCard(
            match = next,
            onClick = { }
        )
    }
}