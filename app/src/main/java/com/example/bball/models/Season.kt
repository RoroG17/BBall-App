package com.example.bball.models

data class Season (
    val idSeason: Int,
    val annee_debut: Int,
    val annee_fin: Int,
    val championnat: String,
    val categorie: String
) {
    fun getText(): String {
        return "$annee_debut - $annee_fin"
    }
}

val sampleSeason = listOf(
    Season(
        idSeason = 1,
        annee_debut = 2024,
        annee_fin = 2025,
        championnat = "D3",
        categorie = "U15G"
    ),
    Season(
        idSeason = 2,
        annee_debut = 2024,
        annee_fin = 2025,
        championnat = "D3",
        categorie = "U13G"
    ),
    Season(
        idSeason = 3,
        annee_debut = 2023,
        annee_fin = 2024,
        championnat = "D2",
        categorie = "U13G"
    ),
    Season(
        idSeason = 4,
        annee_debut = 2025,
        annee_fin = 2026,
        championnat = "D1",
        categorie = "U15G"
    ),
)