package com.example.bball.ui.components.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bball.models.Stat
import com.example.bball.viewmodels.StatScope
import com.example.bball.viewmodels.StatViewModel
import com.example.bball.R

@Composable
fun PlayerStatsTable(
    stats: List<Stat>,
    modifier: Modifier = Modifier,
    statVM: StatViewModel = viewModel(),
) {
    val scrollX = rememberScrollState()

    Surface(
        modifier = modifier
            .padding(12.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 1.dp
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {

            // ===== HEADER =====
            stickyHeader {
                Column {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .horizontalScroll(scrollX)
                            .padding(vertical = 8.dp)
                    ) {
                        statVM.columns.forEach { col ->
                            HeaderCell(col.header, col.width)
                        }
                    }
                    DividerLine()
                }
            }

            // ===== ROWS =====
            itemsIndexed(stats, key = { _, r -> r.id }) { index, row ->

                val baseBg =
                    if (index % 2 == 0) Color.Transparent
                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)

                val bgColor =
                    if (statVM.selectedStat == row)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                    else baseBg

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(bgColor)
                        .clickable { statVM.selectedStat = row }
                        .horizontalScroll(scrollX)
                        .height(44.dp)
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    statVM.columns.forEach { col ->
                        BodyCell(
                            text = col.value(row),
                            width = col.width,
                            center = col.alignCenter
                        )
                    }
                }
            }
        }
    }

    statVM.selectedStat?.let { stat ->
        StatTabbedDialog(
            stat = stat,
            statVM = statVM,
            onDismiss = { statVM.selectedStat = null }
        )
    }
}

/* ========================================================= */
/* ====================== DIALOG =========================== */
/* ========================================================= */

@Composable
private fun StatTabbedDialog(
    stat: Stat,
    statVM: StatViewModel,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fermer")
            }
        },
        title = {
            Column {
                Text(
                    text = "Détails du match",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stat.match_libelle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            Column {

                TabRow(
                    selectedTabIndex = statVM.tabs.indexOf(statVM.selectedTab),
                    indicator = { tabPositions ->
                        Box(
                            Modifier
                                .tabIndicatorOffset(
                                    tabPositions[statVM.tabs.indexOf(statVM.selectedTab)]
                                )
                                .height(3.dp)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                ) {
                    statVM.tabs.forEach { scope ->
                        Tab(
                            selected = statVM.selectedTab == scope,
                            onClick = { statVM.selectedTab = scope },
                            text = {
                                Text(statVM.tabTitles[scope]!!)
                            }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                StatScopeContent(
                    stat = stat,
                    scope = statVM.selectedTab,
                    statVM = statVM
                )
            }
        }
    )
}

/* ========================================================= */
/* ====================== CONTENT ========================== */
/* ========================================================= */

@Composable
private fun StatScopeContent(
    stat: Stat,
    scope: StatScope,
    statVM: StatViewModel
) {
    val data = remember(stat, scope) {
        statVM.buildScopedStats(stat, scope)
    }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        SectionTitle(stringResource(R.string.popup_title))

        StatLine(stringResource(R.string.stat_points), data.points)
        StatLine(stringResource(R.string.stat_assists), data.pd)

        StatLine(
            stringResource(R.string.stat_rebounds),
            data.rebTotal
        )

        StatLine(stringResource(R.string.stat_steals), data.inter)
        StatLine(stringResource(R.string.stat_blocks), data.ctr)
        StatLine(stringResource(R.string.stat_turnovers), data.bp)
        StatLine(stringResource(R.string.stat_fouls), data.fautes)

        SectionTitle(stringResource(R.string.stats_shots_title))

        StatLine(stringResource(R.string.stat_points), data.points)

        StatLine(
            stringResource(R.string.stat_2pts),
            stringResource(R.string.stat_shots_format, data.t2In, data.t2Att, data.pct2)
        )

        StatLine(
            stringResource(R.string.stat_3pts),
            stringResource(R.string.stat_shots_format, data.t3In, data.t3Att, data.pct3)
        )

        StatLine(
            stringResource(R.string.stat_free_throws),
            stringResource(R.string.stat_shots_format, data.lfIn, data.lfAtt, data.pctLF)
        )

        StatLine(
            stringResource(R.string.stat_fg_percent),
            stringResource(R.string.stat_shots_format, data.fgIn, data.fgAtt, data.pctFG)
        )

        SectionTitle("Rebonds")
        StatLine(stringResource(R.string.stat_rebounds), data.rebTotal)
        StatLine("Rebonds Offensifs", data.rebOff)
        StatLine("Rebonds Défensifs", data.rebDef)

        SectionTitle("Passes")
        StatLine(stringResource(R.string.stat_assists), data.pd)
        StatLine("Passes", "${data.passesReussis} / ${data.passesRates} (${"%.2f".format(data.pctPasses)}%)")


        if (scope == StatScope.TOTAL) {
            SectionTitle(stringResource(R.string.stats_advanced_title))
            StatLine("Points créés", "${data.createdPoints} pts")
            StatLine("Ration Récupération / BP", "%.2f".format(data.ratioGetLost))
        }

    }
}

/* ========================================================= */
/* ====================== UI ATOMS ========================= */
/* ========================================================= */

@Composable
private fun SectionTitle(text: String) {
    Spacer(Modifier.height(12.dp))
    Text(
        text,
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(Modifier.height(6.dp))
}

@Composable
private fun StatLine(label: String, value: Any) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        shape = MaterialTheme.shapes.small,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                value.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun HeaderCell(text: String, width: Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
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
            style = MaterialTheme.typography.bodySmall,
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
