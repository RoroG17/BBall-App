package com.example.bball.ui.components.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bball.models.Stat

data class ColumnSpec<T>(
    val header: String,
    val width: Dp,
    val value: (T) -> String,
    val alignCenter: Boolean = true
)

@Composable
fun MatchStatsTable(
    stats: List<Stat>,
    modifier: Modifier = Modifier
) {
    val scrollX = rememberScrollState()

    val columns = listOf<ColumnSpec<Stat>>(
        ColumnSpec("Match", 160.dp, { it.match_libelle }, alignCenter = false),

        ColumnSpec("PTS", 72.dp, { it.points.toString() }),
        ColumnSpec("PD", 64.dp, { it.passesDecisives.toString() }),
        ColumnSpec("REB", 72.dp, { it.rebonds.toString() }),

        ColumnSpec("INT", 64.dp, { it.interceptions.toString() }),
        ColumnSpec("CTR", 64.dp, { it.contres.toString() }),
        ColumnSpec("BP", 64.dp, { it.ballonsPerdus.toString() }),
        ColumnSpec("Fautes", 72.dp, { it.fautes.toString() }),
    )

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        tonalElevation = 1.dp
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {

            // ===== HEADER =====
            stickyHeader {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .horizontalScroll(scrollX)
                        .padding(vertical = 8.dp)
                ) {
                    columns.forEach { col ->
                        HeaderCell(col.header, col.width)
                    }
                }
                DividerLine()
            }

            // ===== ROWS =====
            itemsIndexed(stats, key = { _, r -> r.id }) { index, row ->
                val bgColor =
                    if (index % 2 == 0) Color.Transparent
                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)

                Row(
                    modifier = Modifier
                        .background(bgColor)
                        .horizontalScroll(scrollX)
                        .padding(vertical = 6.dp)
                ) {
                    columns.forEach { col ->
                        BodyCell(
                            text = col.value(row),
                            width = col.width,
                            center = col.alignCenter
                        )
                    }
                }
                DividerLine()
            }
        }
    }
}


@Composable
private fun HeaderCell(text: String, width: Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BodyCell(text: String, width: Dp, center: Boolean) {
    Box(
        modifier = Modifier
            .width(width)
            .padding(horizontal = 8.dp),
        contentAlignment = if (center) Alignment.Center else Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = if (center) TextAlign.Center else TextAlign.Start
        )
    }
}

@Composable
private fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
}