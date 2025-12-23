package com.example.bball.utils

import com.example.bball.models.Stat
import com.example.bball.viewmodels.TypePieChart

fun getTirReussi(stats: List<Stat>, type: TypePieChart): Float {
    var tirsReussis : Int = 0
    var tirsTentes : Int = 0

    when(type) {
        TypePieChart.SHOOT -> {
            tirsReussis = stats.sumOf { it.tirs2Reussis }
            tirsTentes = stats.sumOf { it.tirs2Tentes }
        }
        TypePieChart.THREE -> {
            tirsReussis = stats.sumOf { it.tirs3Reussis }
            tirsTentes = stats.sumOf { it.tirs3Tentes }
        }
        TypePieChart.LF -> {
            tirsReussis = stats.sumOf { it.lfReussis }
            tirsTentes = stats.sumOf { it.lfTentes }
        }
    }


    if (tirsTentes == 0) return 0f

    return (tirsReussis.toFloat() / tirsTentes.toFloat()) * 100f
}

fun getTirManque(stats: List<Stat>, type: TypePieChart): Float {
    var tirsRate : Int = 0
    var tirsTentes : Int = 0

    when(type) {
        TypePieChart.SHOOT -> {
            tirsRate = stats.sumOf { it.tirs2Manques }
            tirsTentes = stats.sumOf { it.tirs2Tentes }
        }
        TypePieChart.THREE -> {
            tirsRate = stats.sumOf { it.tirs3Manques }
            tirsTentes = stats.sumOf { it.tirs3Tentes }
        }
        TypePieChart.LF -> {
            tirsRate = stats.sumOf { it.lfRates }
            tirsTentes = stats.sumOf { it.lfTentes }
        }
    }

    if (tirsTentes == 0) return 0f

    return (tirsRate.toFloat() / tirsTentes.toFloat()) * 100f
}

fun averageStats(stats: List<Stat>): Map<String, Float> {
    if (stats.isEmpty()) return emptyMap()

    val size = stats.size.toFloat()

    val pts = stats.sumOf { it.points }.toFloat() / size
    val pd = stats.sumOf { it.passesDecisives }.toFloat() / size
    val reb = stats.sumOf { it.rebonds }.toFloat() / size
    val int = stats.sumOf { it.interceptions }.toFloat() / size
    val con = stats.sumOf { it.contres }.toFloat() / size
    val bp = stats.sumOf { it.ballonsPerdus }.toFloat() / size
    val fautes = stats.sumOf { it.fautes }.toFloat() / size

    return mapOf(
        "Pts" to pts,
        "PD" to pd,
        "Reb" to reb,
        "I" to int,
        "C" to con,
        "BP" to bp,
        "F" to fautes
    )
}