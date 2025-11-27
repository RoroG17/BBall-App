package com.example.bball.ui.components.DropDown

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.bball.R
import com.example.bball.models.Season
import com.example.bball.viewmodels.MatchViewModel

@Composable
fun DropDownSeasons(vm: MatchViewModel, seasons: List<Season>) {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    Column {
        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(text = vm.season.getText())
                Image(
                    painter = painterResource(id = R.drawable.ic_dropdown),
                    contentDescription = "DropDown Icon"
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                seasons.forEach { season ->
                    DropdownMenuItem(text = {
                        Text(text = season.getText())
                    },
                    onClick = {
                        isDropDownExpanded.value = false
                        vm.season = season
                    })
                }
            }
        }

    }
}