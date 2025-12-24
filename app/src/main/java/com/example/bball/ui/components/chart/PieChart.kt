package com.example.bball.ui.components.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.bball.models.Stat
import com.example.bball.utils.getTirManque
import com.example.bball.utils.getTirReussi
import com.example.bball.viewmodels.TypePieChart

@Composable
fun MyPieChart(title: String, stats: List<Stat>, type: TypePieChart) {

    val slices = listOf(
        PieChartData.Slice(
            "Réussis",
            getTirReussi(stats, type),
            Color.Green
        ),
        PieChartData.Slice(
            "Manqués",
            getTirManque(stats, type),
            Color.Red
        )
    )

    val pieChartData = PieChartData(
        slices = slices,
        plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        animationDuration = 1200,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        PieChart(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(horizontal = 16.dp),
            pieChartData = pieChartData,
            pieChartConfig = pieChartConfig
        )

        Spacer(modifier = Modifier.height(16.dp))

        PieChartLegend(slices)
    }
}

@Composable
fun PieChartLegend(slices: List<PieChartData.Slice>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "$label : ${value.toInt()}%",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}