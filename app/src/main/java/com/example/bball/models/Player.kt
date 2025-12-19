package com.example.bball.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

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

    fun getBirthDateFormat(): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val date = LocalDate.parse(birthDate, inputFormatter)
        return date.format(outputFormatter)
    }
}