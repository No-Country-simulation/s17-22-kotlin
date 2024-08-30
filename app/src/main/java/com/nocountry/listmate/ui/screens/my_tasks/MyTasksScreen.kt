package com.nocountry.listmate.ui.screens.my_tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.nocountry.listmate.ui.components.BottomNavigationBar

@Composable
fun MyTasksScreen(navHostController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navHostController)
        }
    ){
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(text = "My Tasks")
        }
    }
}