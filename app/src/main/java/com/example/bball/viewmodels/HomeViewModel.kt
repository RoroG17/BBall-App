
package com.example.bball.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bball.network.AccueilResponse
import com.example.bball.network.MatchApi
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data class Success(val matches: AccueilResponse) : HomeUiState
    data class Error(val message: String) : HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel : ViewModel() {

    var state: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    var matches: AccueilResponse? = null

    init {
        getDataHome()
    }

    fun getDataHome() {
        viewModelScope.launch {
            state = HomeUiState.Loading
            try {
                val accueil = MatchApi.retrofitService.getAccueil()
                matches = AccueilResponse(accueil.previousGame, accueil.nextGame)

                state = HomeUiState.Success(matches!!)
            } catch (e: Exception) {
                state = HomeUiState.Error("Erreur chargement des données")
            }
        }
    }
}
