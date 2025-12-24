package com.example.bball.utils

import com.example.bball.models.Stat
import com.example.bball.viewmodels.TypePieChart

fun getTirReussi(stats: List<Stat>, type: TypePieChart): Float {
    var tirsReussis : Int
    var tirsTentes : Int

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
    var tirsRate : Int
    var tirsTentes : Int

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