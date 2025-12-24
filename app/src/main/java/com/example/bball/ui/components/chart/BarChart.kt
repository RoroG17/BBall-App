package com.example.bball.ui.components.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import com.example.bball.models.Stat
import com.example.bball.utils.averageStats
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.columnSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.multiplatform.common.Fill
import com.patrykandpatrick.vico.multiplatform.common.component.LineComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberLineComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberTextComponent
import kotlin.math.ceil

@Composable
fun BarChart(
    labels: List<String>,
    values: List<Int>,
    title: String,
    modifier: Modifier = Modifier
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val scrollState = rememberVicoScrollState()

    val columnComponent: LineComponent = rememberLineComponent(
        thickness = 24.dp,
        fill = Fill(color = Color.Red)
    )

    LaunchedEffect(values) {
        modelProducer.runTransaction {
            columnSeries {
                series(y = values)
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // ===== TITRE =====
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // ===== CHART =====
            CartesianChartHost(
                chart = rememberCartesianChart(
                    rememberColumnCartesianLayer(
                        columnProvider = ColumnCartesianLayer.ColumnProvider.series(
                            columnComponent
                        ),
                        dataLabel = rememberTextComponent(
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    ),
                    startAxis = VerticalAxis.rememberStart(
                        label = rememberTextComponent(
                            style = TextStyle(MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    ),
                    bottomAxis = HorizontalAxis.rememberBottom(
                        valueFormatter = { _, value, _ -> labels.getOrNull(value.toInt()) ?: "" },
                        label = rememberTextComponent(
                            style = TextStyle(MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    )
                ),
                modelProducer = modelProducer,
                scrollState = scrollState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            )
        }
    }
}


@Composable
fun NewBarChart(stats: List<Stat>) {
    val data = averageStats(stats)
    val barsData = data.entries.mapIndexed { index, entry ->
        BarData(
            point = Point(index.toFloat(), entry.value),
            label = entry.key,
            color = if (entry.value >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
        )
    }

    val maxY = barsData.maxOf { it.point.y }
    val ySteps = 5
    val yStepSize = ceil(maxY / ySteps).toInt().coerceAtLeast(1)

    val xAxisData = AxisData.Builder()
        .axisStepSize(36.dp)
        .steps(barsData.size)
        .bottomPadding(48.dp)
        .axisLabelAngle(0f)
        .labelData { index -> barsData.getOrNull(index)?.label ?: "" }
        .build()

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .labelAndAxisLinePadding(16.dp)
        .axisOffset(0.dp)
        .labelData { index -> (index * yStepSize).toString() }
        .build()

    val barChartData = BarChartData(
        chartData = barsData,
        xAxisData = xAxisData,
        yAxisData = yAxisData
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            text = "Statistiques moyennes",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )

        BarChart(
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            barChartData = barChartData
        )
    }
}

