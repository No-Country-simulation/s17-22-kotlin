package com.nocountry.listmate.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.repository.ProjectRepositoryImpl
import com.nocountry.listmate.domain.ProjectRepository
import com.nocountry.listmate.ui.screen.LoginScreen
import com.nocountry.listmate.ui.screen.SignUpScreen
import com.nocountry.listmate.ui.screens.createproject.CreateProjectScreen
import com.nocountry.listmate.ui.screens.createtask.CreateTaskScreen
import com.nocountry.listmate.ui.screens.home.HomeScreen
import com.nocountry.listmate.ui.screens.my_tasks.MyTasksScreen
import com.nocountry.listmate.ui.screens.profile.ProfileScreen
import com.nocountry.listmate.ui.screens.sharedviewmodels.CreateProjectTaskSharedViewModel
import com.nocountry.listmate.ui.screens.sharedviewmodels.CreateProjectTaskSharedViewModelFactory

@Composable
fun ListMateApp(navHostController: NavHostController = rememberNavController()) {
    val projectRepository: ProjectRepository = ProjectRepositoryImpl(FirebaseFirestore.getInstance())
    val createProjectTaskSharedViewModel: CreateProjectTaskSharedViewModel = viewModel(
        factory = CreateProjectTaskSharedViewModelFactory(projectRepository)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(navController = navHostController, startDestination = Destinations.LOGIN) {
            composable(Destinations.SIGNUP) {
                SignUpScreen(navHostController = navHostController)
            }
            composable(Destinations.LOGIN) {
                LoginScreen(navHostController = navHostController)
            }
            composable(Destinations.HOME) {
                HomeScreen(navHostController = navHostController)
            }
            composable(Destinations.MY_TASKS) {
                MyTasksScreen(navHostController = navHostController)
            }
            composable(Destinations.PROFILE) {
                ProfileScreen(navHostController = navHostController)
            }
            composable(Destinations.CREATE_PROJECT) {
                CreateProjectScreen(navHostController = navHostController, sharedViewModel = createProjectTaskSharedViewModel)
            }
            composable(Destinations.CREATE_TASK){
                CreateTaskScreen(navHostController = navHostController, sharedViewModel = createProjectTaskSharedViewModel)
            }
        }
    }
}