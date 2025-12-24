package com.example.bball.models


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
    val idSaison: Int,
    ) {

    fun getScore(): String {
        return "$scoreDom - $scoreExt"
    }
}