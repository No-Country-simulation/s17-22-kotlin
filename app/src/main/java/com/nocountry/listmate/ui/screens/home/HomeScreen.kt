package com.nocountry.listmate.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.nocountry.listmate.ui.components.BottomNavigationBar
import com.nocountry.listmate.ui.navigation.Destinations

@Composable
fun HomeScreen(navHostController: NavHostController) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navHostController)
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(text = "Home")
            Button(onClick = { navHostController.navigate(Destinations.CREATE_PROJECT) }) {
                Text(text = "Go to Create Project")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(navHostController = NavHostController(LocalContext.current))
    }
}