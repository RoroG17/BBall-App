package com.example.bball.ui.components.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bball.ui.components.card.MatchCard
import com.example.bball.ui.components.chart.MyPieChart
import com.example.bball.ui.components.chart.NewBarChart
import com.example.bball.ui.components.layout.LoadingComponent
import com.example.bball.ui.components.table.MatchStatsTable
import com.example.bball.ui.components.table.PlayerStatsTable
import com.example.bball.viewmodels.MatchDetailUiState
import com.example.bball.viewmodels.MatchDetailViewModel
import com.example.bball.viewmodels.MatchDetailViewModelFactory
import com.example.bball.viewmodels.TypePieChart

@SuppressLint("ViewModelConstructorInComposable")
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
    ) {
        MatchCard(
            match,
            onClick = {}
        )

        MatchStatsTable(
            stats = stats,
            modifier = Modifier.height(500.dp)
        )

        NewBarChart(stats = stats)

        MyPieChart(
            title = "% de shoot",
            stats = stats,
            type = TypePieChart.SHOOT
        )

        MyPieChart(
            title = "% de 3pts",
            stats = stats,
            type = TypePieChart.THREE
        )

        MyPieChart(
            title = "% de Lf",
            stats = stats,
            type = TypePieChart.LF
        )
    }
}