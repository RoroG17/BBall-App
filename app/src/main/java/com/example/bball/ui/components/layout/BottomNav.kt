package com.example.bball.ui.components.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bball.R
import com.example.bball.ui.components.screen.CalendarScreen
import com.example.bball.ui.components.screen.HomeScreen
import com.example.bball.ui.components.screen.PlayerScreen
import com.example.bball.viewmodels.LoginViewModel
import com.example.bball.viewmodels.PlayerViewModel


sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object Accueil : BottomNavItem("accueil", R.drawable.ic_home, "Accueil")
    object Calendrier : BottomNavItem("calendrier", R.drawable.ic_calendar, "Calendrier")
    object Stats : BottomNavItem("stats", R.drawable.ic_stat, "Stats")
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Accueil,
        BottomNavItem.Calendrier,
        BottomNavItem.Stats
    )

    NavigationBar(containerColor = Color.Red) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = Color.White
                    )
                },
                label = { Text(text = item.label, color = Color.White) }
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun NavMenu(
    userViewModel: LoginViewModel,
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {AppTopBar(
            onLogoutClick = { userViewModel.logout() }
        )},
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Accueil.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Accueil.route) { HomeScreen() }
            composable(BottomNavItem.Calendrier.route) { CalendarScreen() }
            composable(BottomNavItem.Stats.route) { PlayerScreen(PlayerViewModel(LocalContext.current)) }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBottomNavBar() {
    BottomNavBar(navController = rememberNavController())
}
