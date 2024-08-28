package com.nocountry.listmate.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nocountry.listmate.ui.screens.home.HomeScreen
import com.nocountry.listmate.ui.screens.my_tasks.MyTasksScreen
import com.nocountry.listmate.ui.screens.profile.ProfileScreen

@Composable
fun ListMateApp(navHostController: NavHostController = rememberNavController()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(navController = navHostController, startDestination = Destinations.HOME) {
            composable(Destinations.HOME) {
                HomeScreen(navHostController = navHostController)
            }
            composable(Destinations.MY_TASKS) {
                MyTasksScreen(navHostController = navHostController)
            }
            composable(Destinations.PROFILE) {
                ProfileScreen(navHostController = navHostController)
            }
        }
    }
}