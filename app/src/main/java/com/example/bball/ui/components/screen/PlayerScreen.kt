package com.example.bball.ui.components.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bball.ui.components.card.PlayerCard
import com.example.bball.ui.components.chart.BarChart
import com.example.bball.ui.components.dropdown.DropDownSeasons
import com.example.bball.ui.components.layout.LoadingComponent
import com.example.bball.ui.components.table.MatchStatsTable
import com.example.bball.ui.components.table.PlayerStatsTable
import com.example.bball.viewmodels.PlayerUiState
import com.example.bball.viewmodels.PlayerViewModel

@Composable
fun PlayerScreen(playerVM: PlayerViewModel) {

    when (playerVM.state) {
        is PlayerUiState.Error -> ErrorScreen((playerVM.state as PlayerUiState.Error).message)
        PlayerUiState.Loading -> LoadingComponent()
        is PlayerUiState.Success -> PlayerDetailsScreen(playerVM)
    }

}

@Composable
fun PlayerDetailsScreen(playerVM: PlayerViewModel) {

    val player = (playerVM.state as PlayerUiState.Success).player
    val seasons = (playerVM.state as PlayerUiState.Success).season
    val stats = (playerVM.state as PlayerUiState.Success).stats

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PlayerCard(
            player = player,
            modifier = Modifier
                .fillMaxWidth()
        )

        DropDownSeasons(playerVM, seasons = seasons)

        PlayerStatsTable(stats = stats, modifier = Modifier.height(500.dp))

        //Graphique
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text(
                text = "Statistiques moyennes",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            BarChart(
                labels = playerVM.getLabelsChart(),
                values = playerVM.getAverageStat(stats)
            )
        }
    }
}