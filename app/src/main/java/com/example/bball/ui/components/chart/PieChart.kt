package com.example.bball.ui.components.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.bball.models.Stat
import com.example.bball.utils.getTirManque
import com.example.bball.utils.getTirReussi
import com.example.bball.viewmodels.PlayerViewModel
import com.example.bball.viewmodels.TypePieChart
import com.patrykandpatrick.vico.multiplatform.common.LegendItem

@Composable
fun MyPieChart(title: String, stats: List<Stat>, type: TypePieChart) {

    val slices = listOf(
        PieChartData.Slice("Tirs réussis", getTirReussi(stats, type), Color(0xFF4CAF50)),
        PieChartData.Slice("Tirs ratés", getTirManque(stats, type), Color(0xFFF44336))
    )

    val pieChartData = PieChartData(
        slices = slices,
        plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = false,
        animationDuration = 1500
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🟣 Titre
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 🟠 Graphique
        PieChart(
            modifier = Modifier
                .size(300.dp),
            pieChartData = pieChartData,
            pieChartConfig = pieChartConfig
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔵 Légende
        PieChartLegend(slices)
    }
}

@Composable
fun PieChartLegend(slices: List<PieChartData.Slice>) {
    Column {
        slices.forEach { slice ->
            LegendItem(
                color = slice.color,
                label = slice.label,
                value = slice.value
            )
        }
    }
}

@Composable
fun LegendItem(
    color: Color,
    label: String,
    value: Float
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {

        Box(
            modifier = Modifier
                .size(14.dp)
                .background(color, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$label - ${value.toInt()}%",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

