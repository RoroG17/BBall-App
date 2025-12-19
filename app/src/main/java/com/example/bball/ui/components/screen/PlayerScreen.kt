package com.example.bball.ui.components.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bball.ui.components.card.PlayerCard
import com.example.bball.ui.components.layout.LoadingComponent
import com.example.bball.ui.components.table.MatchStatsTable
import com.example.bball.viewmodels.PlayerUiState
import com.example.bball.viewmodels.PlayerViewModel

@Composable
fun PlayerScreen(playerVM: PlayerViewModel = PlayerViewModel(LocalContext.current)) {

    when (playerVM.state) {
        is PlayerUiState.Error -> ErrorScreen((playerVM.state as PlayerUiState.Error).message)
        PlayerUiState.Loading -> LoadingComponent()
        is PlayerUiState.Success -> PlayerDetailsScreen(playerVM)
    }

}

@Composable
fun PlayerDetailsScreen(playerVM: PlayerViewModel) {

    val player = (playerVM.state as PlayerUiState.Success).player
    val stats = (playerVM.state as PlayerUiState.Success).stats

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        PlayerCard(
            player = player,
            modifier = Modifier.width(300.dp)
        )

        Spacer(Modifier.height(10.dp))

        Row {
            //DropDownSeasons()
        }

        MatchStatsTable(stats = stats)
    }
}

@Preview
@Composable
fun ViewPlayerPage() {
    HomeScreen()
}