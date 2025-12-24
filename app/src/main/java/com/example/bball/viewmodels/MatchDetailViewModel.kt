package com.example.bball.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bball.models.Match
import com.example.bball.models.Season
import com.example.bball.models.Stat
import com.example.bball.network.MatchApi
import kotlinx.coroutines.launch

sealed interface MatchDetailUiState {
    data class Success(
        val match: Match,
        val stats: List<Stat>
    ) : MatchDetailUiState

    data class Error(val message: String) : MatchDetailUiState
    object Loading : MatchDetailUiState
}
class MatchDetailViewModel (val id: Int) : ViewModel() {

    var state: MatchDetailUiState by mutableStateOf(MatchDetailUiState.Loading)

    init {
        getDetailsMatch()
    }

    fun getDetailsMatch() {
        viewModelScope.launch {
            state = MatchDetailUiState.Loading

            state = try {
                val response = MatchApi.retrofitService.getDetailsMatch(id)
                Log.d("Requête match détails", "${response.stats}")
                MatchDetailUiState.Success(response.match, response.stats)
            } catch (e: Exception) {
                e.printStackTrace()
                MatchDetailUiState.Error("Erreur chargement des données")
            }
        }
    }

    fun getLabelsChart() : List<String> {
        return listOf("Pts", "Reb", "P Dec", "Int", "C", "BP", "F")
    }

    fun getAverageStat(stats : List<Stat>) : List<Int> {
        val points = stats.sumOf { it.points }
        val rebonds = stats.sumOf { it.rebonds }
        val passes = stats.sumOf { it.passesDecisives }
        val int = stats.sumOf { it.interceptions }
        val contres = stats.sumOf { it.contres }
        val bp = stats.sumOf { it.ballonsPerdus }
        val fouls = stats.sumOf { it.fautes }

        return listOf(points, rebonds, passes, int, contres, bp, fouls)
    }
}

class MatchDetailViewModelFactory(private val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MatchDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MatchDetailViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}