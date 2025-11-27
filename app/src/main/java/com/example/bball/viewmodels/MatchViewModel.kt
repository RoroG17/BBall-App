package com.example.bball.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bball.models.Match
import com.example.bball.models.Season
import com.example.bball.models.sampleSeason
import com.example.bball.network.MatchApi
import com.example.bball.network.SeasonApi
import kotlinx.coroutines.launch


sealed interface MatchUiState {
    data class Success(
        val matchs: List<Match>,
        val seasons: List<Season>
    ) : MatchUiState

    object Error : MatchUiState
    object Loading : MatchUiState
}
class MatchViewModel : ViewModel() {

    var state: MatchUiState by mutableStateOf(MatchUiState.Loading)
        private set

    var season = sampleSeason[3]

    init {
        getListMatch()
    }

    fun getListMatch() {

        viewModelScope.launch {
            state = MatchUiState.Loading
            state = try {
                val dataMatch = MatchApi.retrofitService.getMatchs()
                val dataSeason = SeasonApi.retrofitService.getSeasons()
                MatchUiState.Success(getMatchesBySeason(dataMatch), dataSeason)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API_ERROR", "Erreur API: ${e.message}")
                MatchUiState.Error
            }
        }
    }

    fun getMatchesBySeason(matches : List<Match>): List<Match> {
        Log.d("Test", "Get Matches by Season : ${season.idSeason}")
        return matches.filter { match ->
            Log.d("Filter", "Comparing ${match.idSaison} with ${season.idSeason}")
            match.idSaison == season.idSeason
        }
    }
}