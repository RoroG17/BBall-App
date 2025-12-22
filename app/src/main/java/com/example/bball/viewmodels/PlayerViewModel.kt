package com.example.bball.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bball.models.Player
import com.example.bball.models.Season
import com.example.bball.models.Stat
import com.example.bball.network.SeasonApi
import com.example.bball.network.PlayerApi
import com.example.bball.session.SessionManager
import kotlinx.coroutines.launch

sealed interface PlayerUiState {
    data class Success(
        val player : Player,
        val season: List<Season>,
        val stats : List<Stat>
    ) : PlayerUiState

    data class Error(
        val message: String
    ) : PlayerUiState

    object Loading : PlayerUiState

}

class PlayerViewModel(context: Context) : ViewModel() {

    var state : PlayerUiState by mutableStateOf(PlayerUiState.Loading)
        private set

    val context = context
    var player : Player by mutableStateOf(Player(
        licence = "",
        name = "",
        firstName = "",
        civility = "",
        birthDate = "",
        photo = "",
        team = "",
        teamLogo = ""
    ))
        private set


    init {
        getDetailPlayer()
    }

    fun getDetailPlayer() {

        viewModelScope.launch {
            state = PlayerUiState.Loading
            state = try {
                val user = SessionManager(context).getUser()
                val response = PlayerApi.retrofitService.getPlayerDetails(user?.joueur ?: "") //TODO(récuperer le user)
                player = response.joueur
                val seasons = SeasonApi.retrofitService.getSeasons(user?.joueur ?: "")
                PlayerUiState.Success(response.joueur, seasons, response.stats)
            } catch (e: Exception) {
                e.printStackTrace()
                PlayerUiState.Error(e.message.toString())
            }
        }
    }

    fun getLabelsChart() : List<String> {
        return listOf("Pts", "Reb", "P Dec", "Int", "C", "BP", "F")
    }

    fun getAverageStat(stats : List<Stat>) : List<Int> {
        val points = stats.sumOf { it.points } / stats.size
        val rebonds = stats.sumOf { it.rebonds } / stats.size
        val passes = stats.sumOf { it.passesDecisives } / stats.size
        val int = stats.sumOf { it.interceptions } / stats.size
        val contres = stats.sumOf { it.contres } / stats.size
        val bp = stats.sumOf { it.ballonsPerdus } / stats.size
        val fouls = stats.sumOf { it.fautes } / stats.size

        return listOf(points, rebonds, passes, int, contres, bp, fouls)
    }
}