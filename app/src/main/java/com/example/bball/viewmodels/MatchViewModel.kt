package com.example.bball.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bball.models.Match
import com.example.bball.models.Season
import com.example.bball.network.MatchApi
import com.example.bball.network.SeasonApi
import com.example.bball.session.SessionManager
import kotlinx.coroutines.launch


sealed interface MatchUiState {
    data class Success(
        val matches: List<Match>,
        val seasons: List<Season>
    ) : MatchUiState

    data class Error(val message: String) : MatchUiState
    object Loading : MatchUiState
}
class MatchViewModel(context: Context) : ViewModel() {

    var state: MatchUiState by mutableStateOf(MatchUiState.Loading)
        private set

    val context = context

    var matches = listOf<Match>()
    var season : Season? by mutableStateOf(null)
        private set

    init {
        getListMatch()
    }

    fun getListMatch() {

        viewModelScope.launch {
            state = MatchUiState.Loading
            state = try {
                matches = MatchApi.retrofitService.getMatchs()

                val user = SessionManager(context).getUser()
                val dataSeason = SeasonApi.retrofitService.getSeasons(user?.joueur ?: "")

                if (!dataSeason.isEmpty()) {
                    season = dataSeason[dataSeason.size - 1]
                    MatchUiState.Success(matches.filter { match -> match.idSaison == season?.idSeason }, dataSeason)
                } else {
                    MatchUiState.Error("Aucune saison n'a été trouvée")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                MatchUiState.Error("Erreur chargement des données")
            }
        }
    }


    fun onSeasonSelected(newSeason: Season) {
        season = newSeason
        getMatchesBySeason()
    }


    fun getMatchesBySeason() {
        try {
            val data = matches.filter { match ->
                match.idSaison == season?.idSeason
            }
            state = if (data.isEmpty()) {
                MatchUiState.Error("No data found")
            } else {
                MatchUiState.Success(data, (state as MatchUiState.Success).seasons)
            }
        } catch (e: Exception) {
            state = MatchUiState.Error("Impossible de filtrer les matches")
        }
    }
}