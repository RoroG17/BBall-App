package com.example.bball.ui.components.Card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bball.R
import com.example.bball.models.Match
import com.example.bball.network.NETWORK_IMAGES_LOGO
import com.example.bball.ui.components.DropDown.DropDownSeasons
import com.example.bball.ui.theme.Primary
import com.example.bball.viewmodels.MatchUiState
import com.example.bball.viewmodels.MatchViewModel
import com.example.bball.ui.components.screen.LoadingScreen
import com.example.bball.ui.components.screen.ErrorScreen

@Composable
fun MatchCard(
    match : Match,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(0) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Journée ${match.numero} - ${match.dateMatch}",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }

        // Content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Équipe domicile
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = "$NETWORK_IMAGES_LOGO${match.logoDom}",
                    contentDescription = "Logo ${match.equipeDom}",
                    modifier = Modifier.size(80.dp)
                )

                Text(
                    text = if (match.equipeDom.length > 10) {
                        match.equipeDom.take(10) + "..."
                    } else {
                        match.equipeDom
                    },
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

            }

            // Score
            Text(
                text = match.getScore(),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            // Équipe extérieur
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = "$NETWORK_IMAGES_LOGO${match.logoExt}",
                    contentDescription = "Logo ${match.equipeExt}",
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = if (match.equipeExt.length > 10) {
                        match.equipeExt.take(10) + "..."
                    } else {
                        match.equipeExt
                    },
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}



@Composable
fun ListMatch(vm: MatchViewModel) {
    when (vm.state) {
        is MatchUiState.Error -> {
            ErrorScreen((vm.state as MatchUiState.Error).message)
        }
        MatchUiState.Loading -> {
            LoadingScreen()
        }
        is MatchUiState.Success -> {
            DropDownSeasons(vm, (vm.state as MatchUiState.Success).seasons)
            MatchList((vm.state as MatchUiState.Success).matches)
        }
    }
}

@Composable
fun MatchList(matches : List<Match>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(matches) { match ->
            MatchCard(
                match = match,
                onClick = {
                    // Action au clic (ex: navigation)
                }
            )
        }
    }
}