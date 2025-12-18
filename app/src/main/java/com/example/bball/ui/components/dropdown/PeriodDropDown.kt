package com.example.bball.ui.components.dropdown

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.bball.R

@Composable
fun DropDownPeriod() {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val period = listOf("Complet", "1er quart-temps", "2e quart-temps", "3e quart-temps", "4e quart-temps")
    var selectedPeriod by remember { mutableStateOf(period[0]) }

    Column {
        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(text = selectedPeriod)
                Image(
                    painter = painterResource(id = R.drawable.ic_dropdown),
                    contentDescription = "DropDown Icon"
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = { isDropDownExpanded.value = false }
            ) {
                period.forEach { period ->
                    DropdownMenuItem(
                        text = { Text(text = period) },
                        onClick = {
                            isDropDownExpanded.value = false
                            selectedPeriod = period
                        }
                    )
                }
            }
        }
    }
}