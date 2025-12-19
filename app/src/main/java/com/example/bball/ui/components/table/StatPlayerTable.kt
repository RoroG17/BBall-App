package com.example.bball.ui.components.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var selectedStat by remember { mutableStateOf<Stat?>(null) }

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
                        .clickable { selectedStat = row }
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

    selectedStat?.let { s ->
        StatTabbedDialog(
            stat = s,
            onDismiss = { selectedStat = null }
        )
    }
}


private enum class StatScope { TOTAL, Q1, Q2, Q3, Q4 }

@Composable
private fun StatTabbedDialog(
    stat: Stat,
    onDismiss: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(StatScope.TOTAL) }
    val tabs = listOf(StatScope.TOTAL, StatScope.Q1, StatScope.Q2, StatScope.Q3, StatScope.Q4)
    val tabTitles = mapOf(
        StatScope.TOTAL to "Total",
        StatScope.Q1 to "Q1",
        StatScope.Q2 to "Q2",
        StatScope.Q3 to "Q3",
        StatScope.Q4 to "Q4",
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Fermer") }
        },
        title = { Text("Détails – ${stat.match_libelle}", modifier = Modifier.background(Color.Red)) },
        text = {
            Column {
                // ---- Onglets ----
                TabRow(selectedTabIndex = tabs.indexOf(selectedTab)) {
                    tabs.forEachIndexed { index, scope ->
                        Tab(
                            selected = selectedTab == scope,
                            onClick = { selectedTab = scope },
                            text = { Text(tabTitles[scope]!!) }
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))

                // ---- Contenu selon onglet ----
                StatScopeContent(stat = stat, scope = selectedTab)
            }
        }
    )
}


@Composable
private fun StatScopeContent(
    stat: Stat,
    scope: StatScope
) {
    val data = remember(stat, scope) { buildScopedStats(stat, scope) }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        // Synthèse
        Text("Synthèse", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))
        StatLine("Points", data.points)
        StatLine("Passes décisives", data.pd)
        StatLine("Rebonds (Off/Def)", "${data.rebOff} / ${data.rebDef}  (Total: ${data.rebTotal})")
        StatLine("Interceptions", data.inter)
        StatLine("Contres", data.ctr)
        StatLine("Ballons perdus", data.bp)
        StatLine("Fautes", data.fautes)

        Spacer(Modifier.height(12.dp))
        // Tirs
        Text("Tirs", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))
        StatLine("2 pts", "${data.t2In}/${data.t2Att} (${data.pct2}%)")
        StatLine("3 pts", "${data.t3In}/${data.t3Att} (${data.pct3}%)")
        StatLine("LF", "${data.lfIn}/${data.lfAtt} (${data.pctLF}%)")
        StatLine("FG%", "${data.fgIn}/${data.fgAtt} (${data.pctFG}%)")

        if (scope == StatScope.TOTAL) {
            Spacer(Modifier.height(12.dp))
            Text("Métriques avancées", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            StatLine("Négatives (BP+Fautes)", data.negatives)
            StatLine("Assist/Turnover", data.astTo)
            StatLine("Efficacité", data.efficacite)
        }
    }
}


private data class ScopedStats(
    val points: Int,
    val pd: Int,
    val rebOff: Int,
    val rebDef: Int,
    val rebTotal: Int,
    val inter: Int,
    val ctr: Int,
    val bp: Int,
    val fautes: Int,
    val t2In: Int, val t2Att: Int, val pct2: Int,
    val t3In: Int, val t3Att: Int, val pct3: Int,
    val lfIn: Int, val lfAtt: Int, val pctLF: Int,
    val fgIn: Int, val fgAtt: Int, val pctFG: Int,
    val negatives: Int = 0,
    val astTo: String = "—",
    val efficacite: Int = 0,
)

private fun pct(made: Int, att: Int): Int = if (att > 0) (100f * made / att).toInt() else 0

private fun buildScopedStats(stat: Stat, scope: StatScope): ScopedStats {
    return when (scope) {
        StatScope.TOTAL -> {
            val fgIn = stat.tirsReussisTotal
            val fgAtt = stat.tirsTentesTotal
            ScopedStats(
                points = stat.points,
                pd = stat.passesDecisives,
                rebOff = stat.rebondsOff,
                rebDef = stat.rebondsDef,
                rebTotal = stat.rebonds,
                inter = stat.interceptions,
                ctr = stat.contres,
                bp = stat.ballonsPerdus,
                fautes = stat.fautes,
                t2In = stat.tirs2Reussis,
                t2Att = stat.tirs2Tentes,
                pct2 = pct(stat.tirs2Reussis, stat.tirs2Tentes),
                t3In = stat.tirs3Reussis,
                t3Att = stat.tirs3Tentes,
                pct3 = pct(stat.tirs3Reussis, stat.tirs3Tentes),
                lfIn = stat.lfReussis,
                lfAtt = stat.lfTentes,
                pctLF = pct(stat.lfReussis, stat.lfTentes),
                fgIn = fgIn,
                fgAtt = fgAtt,
                pctFG = pct(fgIn, fgAtt),
                negatives = stat.negatives,
                astTo = String.format("%.2f", stat.ratioAssistTurnover),
                efficacite = stat.efficacite
            )
        }
        StatScope.Q1, StatScope.Q2, StatScope.Q3, StatScope.Q4 -> {
            val q = when (scope) {
                StatScope.Q1 -> Quarter(
                    pd = stat.q1_passes_decisives,
                    rebOff = stat.q1_rebonds_offensifs,
                    rebDef = stat.q1_rebonds_defensifs,
                    inter = stat.q1_interceptions,
                    ctr = stat.q1_contres,
                    bp = stat.q1_ballons_perdus,
                    fautes = stat.q1_fautes,
                    t2In = stat.q1_tirs_2pts_reussis,
                    t2Miss = stat.q1_tirs_2pts_manques,
                    t3In = stat.q1_tirs_3pts_reussis,
                    t3Miss = stat.q1_tirs_3pts_manques,
                    lfIn = stat.q1_lancers_francs_reussis,
                    lfMiss = stat.q1_lancers_francs_rates
                )
                StatScope.Q2 -> Quarter(
                    pd = stat.q2_passes_decisives,
                    rebOff = stat.q2_rebonds_offensifs,
                    rebDef = stat.q2_rebonds_defensifs,
                    inter = stat.q2_interceptions,
                    ctr = stat.q2_contres,
                    bp = stat.q2_ballons_perdus,
                    fautes = stat.q2_fautes,
                    t2In = stat.q2_tirs_2pts_reussis,
                    t2Miss = stat.q2_tirs_2pts_manques,
                    t3In = stat.q2_tirs_3pts_reussis,
                    t3Miss = stat.q2_tirs_3pts_manques,
                    lfIn = stat.q2_lancers_francs_reussis,
                    lfMiss = stat.q2_lancers_francs_rates
                )
                StatScope.Q3 -> Quarter(
                    pd = stat.q3_passes_decisives,
                    rebOff = stat.q3_rebonds_offensifs,
                    rebDef = stat.q3_rebonds_defensifs,
                    inter = stat.q3_interceptions,
                    ctr = stat.q3_contres,
                    bp = stat.q3_ballons_perdus,
                    fautes = stat.q3_fautes,
                    t2In = stat.q3_tirs_2pts_reussis,
                    t2Miss = stat.q3_tirs_2pts_manques,
                    t3In = stat.q3_tirs_3pts_reussis,
                    t3Miss = stat.q3_tirs_3pts_manques,
                    lfIn = stat.q3_lancers_francs_reussis,
                    lfMiss = stat.q3_lancers_francs_rates
                )
                StatScope.Q4 -> Quarter(
                    pd = stat.q4_passes_decisives,
                    rebOff = stat.q4_rebonds_offensifs,
                    rebDef = stat.q4_rebonds_defensifs,
                    inter = stat.q4_interceptions,
                    ctr = stat.q4_contres,
                    bp = stat.q4_ballons_perdus,
                    fautes = stat.q4_fautes,
                    t2In = stat.q4_tirs_2pts_reussis,
                    t2Miss = stat.q4_tirs_2pts_manques,
                    t3In = stat.q4_tirs_3pts_reussis,
                    t3Miss = stat.q4_tirs_3pts_manques,
                    lfIn = stat.q4_lancers_francs_reussis,
                    lfMiss = stat.q4_lancers_francs_rates
                )
                else -> error("should not happen")
            }

            val t2Att = q.t2In + q.t2Miss
            val t3Att = q.t3In + q.t3Miss
            val lfAtt = q.lfIn + q.lfMiss
            val fgIn = q.t2In + q.t3In
            val fgAtt = t2Att + t3Att

            val points = q.t2In * 2 + q.t3In * 3 + q.lfIn

            ScopedStats(
                points = points,
                pd = q.pd,
                rebOff = q.rebOff,
                rebDef = q.rebDef,
                rebTotal = q.rebOff + q.rebDef,
                inter = q.inter,
                ctr = q.ctr,
                bp = q.bp,
                fautes = q.fautes,
                t2In = q.t2In, t2Att = t2Att, pct2 = pct(q.t2In, t2Att),
                t3In = q.t3In, t3Att = t3Att, pct3 = pct(q.t3In, t3Att),
                lfIn = q.lfIn, lfAtt = lfAtt, pctLF = pct(q.lfIn, lfAtt),
                fgIn = fgIn, fgAtt = fgAtt, pctFG = pct(fgIn, fgAtt),
            )
        }
    }
}

private data class Quarter(
    val pd: Int,
    val rebOff: Int,
    val rebDef: Int,
    val inter: Int,
    val ctr: Int,
    val bp: Int,
    val fautes: Int,
    val t2In: Int,
    val t2Miss: Int,
    val t3In: Int,
    val t3Miss: Int,
    val lfIn: Int,
    val lfMiss: Int
)


@Composable
private fun StatLine(label: String, value: Any) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(value.toString(), fontWeight = FontWeight.SemiBold)
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