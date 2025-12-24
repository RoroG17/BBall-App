package com.example.bball.ui.components.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bball.R
import com.example.bball.models.Stat
import com.example.bball.ui.components.card.PlayerCard
import com.example.bball.ui.components.chart.BarChart
import com.example.bball.ui.components.chart.MyPieChart
import com.example.bball.ui.components.dropdown.DropDownSeasons
import com.example.bball.ui.components.layout.LoadingComponent
import com.example.bball.ui.components.table.PlayerStatsTable
import com.example.bball.viewmodels.PlayerUiState
import com.example.bball.viewmodels.PlayerViewModel
import com.example.bball.viewmodels.TypePieChart

@Composable
fun PlayerScreen(playerVM: PlayerViewModel) {

    when (playerVM.state) {
        is PlayerUiState.Error -> ErrorScreen((playerVM.state as PlayerUiState.Error).message)
        PlayerUiState.Loading -> LoadingComponent()
        is PlayerUiState.Success -> PlayerDetailsScreen(playerVM)
    }

}

enum class PieChartTab(val title: String, val type: TypePieChart) {
    SHOOT("Shoot", TypePieChart.SHOOT),
    THREE("3 pts", TypePieChart.THREE),
    LF("Lancers francs", TypePieChart.LF)
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

        PlayerStatsTable(stats = stats, modifier = Modifier.heightIn(max = 500.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            BarChart(
                labels = playerVM.getLabelsChart(),
                values = playerVM.getAverageStat(stats),
                title = stringResource(R.string.bar_chart_title_avg)
            )

            Spacer(modifier = Modifier.height(16.dp))

            PieChartTabs(stats = stats)
        }

    }
}

@Composable
fun PieChartTabs(
    stats: List<Stat>
) {
    var selectedTab by remember { mutableStateOf(PieChartTab.SHOOT) }

    Column {

        TabRow(
            selectedTabIndex = PieChartTab.entries.indexOf(selectedTab),
            containerColor = Color.Red, // fond rouge
            contentColor = Color.White,
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[PieChartTab.entries.indexOf(selectedTab)])
                        .height(3.dp)
                        .background(Color.White) // barre en blanc
                )
            },
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {
            PieChartTab.entries.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab },
                    text = {
                        Text(
                            text = tab.title,
                            color = Color.White,
                            style = if (selectedTab == tab)
                                MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            else
                                MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MyPieChart(
            title = "% de ${selectedTab.title}",
            stats = stats,
            type = selectedTab.type
        )
    }
}

