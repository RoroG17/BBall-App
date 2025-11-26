package com.example.bball.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bball.models.Match
import com.example.bball.network.MatchApi
import kotlinx.coroutines.launch


sealed interface MatchUiState {
    //data class Success(val matchs: List<Match>) : MatchUiState

    object Success : MatchUiState
    object Error : MatchUiState
    object Loading : MatchUiState
}
class MatchViewModel : ViewModel() {

    var state: MatchUiState by mutableStateOf(MatchUiState.Loading)
        private set

    init {
        getListMatch()
    }

    fun getListMatch() {

        viewModelScope.launch {
            state = MatchUiState.Loading
            state = try {
                val dataList = MatchApi.retrofitService.getMatchs()
                Log.d("Response API", dataList.size.toString())
                MatchUiState.Success
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API_ERROR", "Erreur API: ${e.message}")
                MatchUiState.Error
            }
        }
    }
}