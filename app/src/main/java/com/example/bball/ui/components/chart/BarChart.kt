package com.example.bball.ui.components.chart

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import com.example.bball.models.Stat
import com.example.bball.utils.averageStats
import com.example.bball.viewmodels.PlayerViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.component.LineComponent
import kotlin.math.ceil

//@Composable
//fun BarChart(
//    labels: List<String>,
//    values: List<Int>,
//    modifier: Modifier = Modifier
//) {
//    val modelProducer = remember { CartesianChartModelProducer() }
//    val scrollState = rememberVicoScrollState()
//
//    val columnComponent: LineComponent = rememberLineComponent(
//            thickness = 24.dp
//        )
//
//
//
//    LaunchedEffect(values) {
//        modelProducer.runTransaction {
//            columnSeries {
//                series(y = values)
//            }
//        }
//    }
//
//    Card(
//        modifier = modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = "Statistiques",
//                style = MaterialTheme.typography.titleMedium,
//                modifier = Modifier.padding(bottom = 12.dp)
//            )
//
//            CartesianChartHost(
//                chart = rememberCartesianChart(
//                    rememberColumnCartesianLayer(
//                        // Style des colonnes
//                        columnProvider = ColumnCartesianLayer.ColumnProvider.series(
//                            columnComponent
//                        ),
//                        //Valeur affichée sur les barres (API ancienne)
//                        dataLabel = rememberTextComponent(
//                            color = MaterialTheme.colorScheme.onSurface,
//                            textSize = 12.sp
//                        )
//                    ),
//
//                    // 📈 Axe Y
//                    startAxis = VerticalAxis.rememberStart(
//                        label = rememberTextComponent(
//                            textSize = 12.sp,
//                            color = MaterialTheme.colorScheme.onSurfaceVariant
//                        )
//                    ),
//
//                    // 🏷️ Axe X
//                    bottomAxis = HorizontalAxis.rememberBottom(
//                        valueFormatter = { _, value, _ ->
//                            labels.getOrNull(value.toInt()) ?: ""
//                        },
//                        label = rememberTextComponent(
//                            textSize = 12.sp,
//                            color = MaterialTheme.colorScheme.onSurfaceVariant
//                        )
//                    )
//                ),
//                modelProducer = modelProducer,
//                scrollState = scrollState,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(260.dp)
//            )
//        }
//    }
//}

@Composable
fun NewBarChart(stats : List<Stat>) {

    val data = averageStats(stats)
    val barsData = data.entries.mapIndexed { index, entry ->
        BarData(
            point = Point(index.toFloat(), entry.value),
            label = entry.key
        )
    }

    // 🔵 Calcul automatique de l’axe Y
    val maxY = barsData.maxOf { it.point.y }
    val ySteps = 5
    val yStepSize = ceil(maxY / ySteps).toInt().coerceAtLeast(1)

    // 🔴 Axe X
    val xAxisData = AxisData.Builder()
        .axisStepSize(36.dp)
        .steps(barsData.size)
        .bottomPadding(48.dp)
        .axisLabelAngle(0f)
        .labelData { index ->
            barsData.getOrNull(index)?.label ?: ""
        }
        .build()

    // 🟢 Axe Y
    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .labelAndAxisLinePadding(16.dp)
        .axisOffset(0.dp)
        .labelData { index ->
            (index * yStepSize).toString()
        }
        .build()

    // 🟣 Données du graphique
    val barChartData = BarChartData(
        chartData = barsData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
    )

    BarChart(
        modifier = Modifier
            .height(350.dp)
            .fillMaxWidth()
            .padding(start = 12.dp),
        barChartData = barChartData
    )
}


@Preview
@Composable
private fun BarChartPreview() {
    val labels = listOf("Lun", "Mar", "Mer", "Jeu", "Ven")
    val values = listOf(12, 8, 16, 10, 14)
    //BarChart(labels, values)
    //NewBarChart()
}
