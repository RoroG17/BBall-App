package com.example.bball.models

import kotlinx.serialization.Serializable

data class Match (
    val idMatch: Int,
    val numero: Int,
    val dateMatch: String,
    val logoDom: String,
    val equipeDom: String,
    val scoreDom: Int,
    val scoreExt: Int,
    val logoExt: String,
    val equipeExt: String,
    ) {

    fun getScore(): String {
        return "$scoreDom - $scoreExt"
    }
}

val sampleMatches = listOf(
    Match(
        idMatch = 1,
        numero = 1,
        dateMatch = "21/11/2025",
        logoDom = "logo_dom_1.png",
        equipeDom = "Toulouse",
        scoreDom = 85,
        scoreExt = 78,
        logoExt = "logo_ext_1.png",
        equipeExt = "Paris"
    ),
    Match(
        idMatch = 2,
        numero = 2,
        dateMatch = "28/11/2025",
        logoDom = "logo_dom_2.png",
        equipeDom = "Lyon",
        scoreDom = 92,
        scoreExt = 88,
        logoExt = "logo_ext_2.png",
        equipeExt = "Marseille"
    ),
    Match(
        idMatch = 3,
        numero = 3,
        dateMatch = "05/12/2025",
        logoDom = "logo_dom_3.png",
        equipeDom = "Bordeaux",
        scoreDom = 76,
        scoreExt = 80,
        logoExt = "logo_ext_3.png",
        equipeExt = "Nice"
    ),
    Match(
        idMatch = 4,
        numero = 4,
        dateMatch = "12/12/2025",
        logoDom = "logo_dom_4.png",
        equipeDom = "Nantes",
        scoreDom = 101,
        scoreExt = 95,
        logoExt = "logo_ext_4.png",
        equipeExt = "Lille"
    ),
    Match(
        idMatch = 5,
        numero = 5,
        dateMatch = "19/12/2025",
        logoDom = "logo_dom_5.png",
        equipeDom = "Strasbourg",
        scoreDom = 88,
        scoreExt = 90,
        logoExt = "logo_ext_5.png",
        equipeExt = "Montpellier"
    )
)