package com.example.bball.models

import com.google.gson.annotations.SerializedName

data class Player (
    val licence : String,
    @SerializedName("nom") val name : String,
    @SerializedName("prenom") val firstName : String,
    @SerializedName("civilite") val civility: String,
    @SerializedName("date_naissance") val birthDate : String,
    val photo: String,
    @SerializedName("equipe") val team : String,
    @SerializedName("logo") val teamLogo: String
){
}