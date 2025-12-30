package com.example.bball.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.bball.models.ColumnSpec
import com.example.bball.models.Quarter
import com.example.bball.models.ScopedStats
import com.example.bball.models.Stat

enum class StatScope { TOTAL, Q1, Q2, Q3, Q4 }
class StatViewModel : ViewModel() {
    var selectedStat by mutableStateOf<Stat?>(null)

    val columns = listOf<ColumnSpec<Stat>>(
        ColumnSpec("Match", 160.dp, { it.match_libelle }, alignCenter = false),
        ColumnSpec("PTS", 72.dp, { it.points.toString() }),
        ColumnSpec("PD", 64.dp, { it.passesDecisives.toString() }),
        ColumnSpec("REB", 72.dp, { it.rebonds.toString() }),
        ColumnSpec("INT", 64.dp, { it.interceptions.toString() }),
        ColumnSpec("CTR", 64.dp, { it.contres.toString() }),
        ColumnSpec("BP", 64.dp, { it.ballonsPerdus.toString() }),
        ColumnSpec("Faute", 72.dp, { it.fautes.toString() }),
    )

    val columnsMatch = listOf<ColumnSpec<Stat>>(
        ColumnSpec("", 160.dp, { it.getPlayerName() }, alignCenter = false),
        ColumnSpec("PTS", 72.dp, { it.points.toString() }),
        ColumnSpec("PD", 64.dp, { it.passesDecisives.toString() }),
        ColumnSpec("REB", 72.dp, { it.rebonds.toString() }),
        ColumnSpec("INT", 64.dp, { it.interceptions.toString() }),
        ColumnSpec("CTR", 64.dp, { it.contres.toString() }),
        ColumnSpec("BP", 64.dp, { it.ballonsPerdus.toString() }),
        ColumnSpec("Faute", 72.dp, { it.fautes.toString() }),
    )

    var selectedTab by mutableStateOf(StatScope.TOTAL)
    val tabs = listOf(StatScope.TOTAL, StatScope.Q1, StatScope.Q2, StatScope.Q3, StatScope.Q4)
    val tabTitles = mapOf(
        StatScope.TOTAL to "All",
        StatScope.Q1 to "Q1",
        StatScope.Q2 to "Q2",
        StatScope.Q3 to "Q3",
        StatScope.Q4 to "Q4",
    )

    fun pct(made: Int, att: Int): Int = if (att > 0) (100f * made / att).toInt() else 0

    fun buildScopedStats(stat: Stat, scope: StatScope): ScopedStats {
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
                    createdPoints = stat.createdPoints,
                    ratioGetLost = (stat.rebondsDef.toFloat() + stat.interceptions.toFloat()) / (1F + stat.ballonsPerdus.toFloat()),
                    efficacite = stat.efficacite,
                    passesReussis = stat.passesReussisTotal,
                    passesRates = stat.passesRatesTotal,
                    pctPasses = if (stat.passesReussisTotal + stat.passesRatesTotal > 0) {
                        stat.passesReussisTotal.toFloat() /
                                (stat.passesReussisTotal + stat.passesRatesTotal).toFloat() * 100f
                    } else {
                        0f
                    }

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
                        lfMiss = stat.q1_lancers_francs_rates,
                        passIn =  stat.q1_passes_reussies,
                        passMiss = stat.q1_passes_rates,
                        pctPass = if (stat.q1_passes_reussies + stat.q1_passes_rates > 0) {
                            stat.q1_passes_reussies.toFloat() /
                                    (stat.q1_passes_reussies + stat.q1_passes_rates).toFloat() * 100f
                        } else {
                            0f
                        }
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
                        lfMiss = stat.q2_lancers_francs_rates,
                        passIn =  stat.q2_passes_reussies,
                        passMiss = stat.q2_passes_rates,
                        pctPass = if (stat.q2_passes_reussies + stat.q2_passes_rates > 0) {
                            stat.q2_passes_reussies.toFloat() /
                                    (stat.q2_passes_reussies + stat.q2_passes_rates).toFloat() * 100f
                        } else {
                            0f
                        }
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
                        lfMiss = stat.q3_lancers_francs_rates,
                        passIn =  stat.q3_passes_reussies,
                        passMiss = stat.q3_passes_rates,
                        pctPass = if (stat.q3_passes_reussies + stat.q3_passes_rates > 0) {
                            stat.q3_passes_reussies.toFloat() /
                                    (stat.q3_passes_reussies + stat.q3_passes_rates).toFloat() * 100f
                        } else {
                            0f
                        }
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
                        lfMiss = stat.q4_lancers_francs_rates,
                        passIn =  stat.q4_passes_reussies,
                        passMiss = stat.q4_passes_rates,
                        pctPass = if (stat.q4_passes_reussies + stat.q4_passes_rates > 0) {
                            stat.q4_passes_reussies.toFloat() /
                                    (stat.q4_passes_reussies + stat.q4_passes_rates).toFloat() * 100f
                        } else {
                            0f
                        }
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
                    passesReussis = q.passIn,
                    passesRates = q.passMiss,
                    pctPasses = q.pctPass,
                )
            }
        }
    }
}
