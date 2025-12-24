package com.example.bball.ui.components.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bball.ui.components.card.MatchCard
import com.example.bball.ui.components.chart.BarChart
import com.example.bball.ui.components.chart.NewBarChart
import com.example.bball.ui.components.layout.LoadingComponent
import com.example.bball.ui.components.table.MatchStatsTable
import com.example.bball.viewmodels.MatchDetailUiState
import com.example.bball.viewmodels.MatchDetailViewModel
import com.example.bball.viewmodels.MatchDetailViewModelFactory

@Composable
fun MatchDetailScreen(id : Int){
    val vm: MatchDetailViewModel = viewModel(
        factory = MatchDetailViewModelFactory(id)
    )
    
    when(vm.state) {
        is MatchDetailUiState.Error -> ErrorScreen((vm.state as MatchDetailUiState.Error).message)
        MatchDetailUiState.Loading -> LoadingComponent()
        is MatchDetailUiState.Success -> MatchDetails(vm)
    }
}

@Composable
fun MatchDetails(vm: MatchDetailViewModel) {
    val match = (vm.state as MatchDetailUiState.Success).match
    val stats = (vm.state as MatchDetailUiState.Success).stats

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // ===== MATCH CARD =====
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            MatchCard(
                match = match,
                onClick = {},
                modifier = Modifier.padding(12.dp)
            )
        }

        // ===== STAT TABLE =====
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 2.dp,
            shadowElevation = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            MatchStatsTable(
                stats = stats,
                modifier = Modifier.fillMaxSize()
            )
        }

        // ===== BAR CHART =====
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 2.dp,
            shadowElevation = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Graphique des performances",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                BarChart(
                    labels = vm.getLabelsChart(),
                    values = vm.getAverageStat(stats),
                    title = "Statistiques"
                )
            }
        }

        // ===== PIE CHART TABS =====
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 2.dp,
            shadowElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Répartition des tirs",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                PieChartTabs(stats = stats)
            }
        }
    }
}
