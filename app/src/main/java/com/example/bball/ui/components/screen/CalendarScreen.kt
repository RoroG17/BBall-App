package com.example.bball.ui.components.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bball.ui.components.Card.ListMatch
import com.example.bball.ui.components.DropDown.DropDownSeasons
import com.example.bball.viewmodels.MatchViewModel

@Composable
fun CalendarScreen(vm: MatchViewModel = MatchViewModel()) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        DropDownSeasons(vm)
        ListMatch(state = vm.state)
    }
}

@Preview
@Composable
fun ViewCalendarPage() {
    HomeScreen()
}