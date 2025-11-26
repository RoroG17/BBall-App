package com.example.bball.ui.components.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bball.R
import com.example.bball.ui.components.Card.ListMatch
import com.example.bball.ui.components.DropDown.DropDownSeasons
import com.example.bball.viewmodels.MatchViewModel

@Composable
fun CalendarScreen(vm: MatchViewModel = MatchViewModel()) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        DropDownSeasons()
        ListMatch(state = vm.state)
    }
}

@Preview
@Composable
fun ViewCalendarPage() {
    HomeScreen()
}