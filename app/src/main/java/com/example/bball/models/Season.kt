package com.example.bball.models

import com.google.gson.annotations.SerializedName

data class Season (
    @SerializedName("Id_Saison") val idSeason: Int,
    val annee_debut: Int,
    val annee_fin: Int,
    val championnat: String,
    val categorie: String
) {
    fun getText(): String {
        return "$annee_debut - $annee_fin"
    }
}