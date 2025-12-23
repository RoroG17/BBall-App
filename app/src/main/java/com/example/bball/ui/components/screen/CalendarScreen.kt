package com.example.bball.ui.components.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bball.ui.components.card.ListMatch
import com.example.bball.viewmodels.MatchViewModel

@Composable
fun CalendarScreen(vm: MatchViewModel = MatchViewModel(LocalContext.current), navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(all = 8.dp)) {
        ListMatch(vm = vm, navController = navController)
    }
}