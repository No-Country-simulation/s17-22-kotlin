package com.nocountry.listmate.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.local.SettingsDataStore
import com.nocountry.listmate.data.repository.ProjectRepositoryImpl
import com.nocountry.listmate.domain.ProjectRepository
import com.nocountry.listmate.ui.screens.createproject.CreateProjectScreen
import com.nocountry.listmate.ui.screens.createtask.CreateTaskScreen
import com.nocountry.listmate.ui.screens.editdeteleproject.EditDeleteProjectScreen
import com.nocountry.listmate.ui.screens.home.HomeScreen
import com.nocountry.listmate.ui.screens.home.HomeScreenViewModel
import com.nocountry.listmate.ui.screens.login.LoginScreen
import com.nocountry.listmate.ui.screens.my_tasks.MyTasksScreen
import com.nocountry.listmate.ui.screens.profile.ProfileScreen
import com.nocountry.listmate.ui.screens.projectdetail.ProjectDetailScreen
import com.nocountry.listmate.ui.screens.register.SignUpScreen
import com.nocountry.listmate.ui.screens.sharedviewmodels.CreateProjectTaskSharedViewModel
import com.nocountry.listmate.ui.screens.sharedviewmodels.CreateProjectTaskSharedViewModelFactory
import com.nocountry.listmate.ui.screens.sharedviewmodels.SharedViewModel
import com.nocountry.listmate.ui.screens.sharedviewmodels.SharedViewModelFactory

@Composable
fun ListMateApp(navHostController: NavHostController = rememberNavController()) {

    val context = LocalContext.current
    val settingsDataStore = remember { SettingsDataStore(context) }
    val userIdFlow = settingsDataStore.getUserId.collectAsState(initial = "")
    val startDestination by remember {
        derivedStateOf { if (userIdFlow.value?.isNotEmpty() == true) Destinations.HOME else Destinations.LOGIN }
    }

    val projectRepository: ProjectRepository =
        ProjectRepositoryImpl(FirebaseFirestore.getInstance())

    val createProjectTaskSharedViewModel: CreateProjectTaskSharedViewModel = viewModel(
        factory = CreateProjectTaskSharedViewModelFactory(projectRepository)
    )

    val sharedViewModel: SharedViewModel = viewModel(
        factory = SharedViewModelFactory(
            settingsDataStore = settingsDataStore,
            savedStateHandle = SavedStateHandle()
        )
    )

    val userId by sharedViewModel.userId.collectAsState()

    val homeScreenViewModel: HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModel.provideFactory(userId)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(navController = navHostController, startDestination = startDestination) {
            composable(Destinations.SIGNUP) {
                SignUpScreen(
                    navHostController = navHostController,
                    settingsDataStore = settingsDataStore,
                    context = context
                )
            }
            composable(Destinations.LOGIN) {
                LoginScreen(
                    navHostController = navHostController,
                    settingsDataStore = settingsDataStore,
                    context = context,
                    sharedViewModel = sharedViewModel
                )
            }
            composable(Destinations.HOME) {
                HomeScreen(
                    navHostController = navHostController,
                    homeScreenViewModel = homeScreenViewModel,
                    userId
                )
            }
            composable(Destinations.MY_TASKS) {
                MyTasksScreen(
                    navHostController = navHostController,
                    sharedViewModel = sharedViewModel
                )
            }
            composable(Destinations.PROFILE) {
                ProfileScreen(
                    navHostController = navHostController,
                    settingsDataStore = settingsDataStore,
                    context = context,
                )
            }
            composable(
                route = "${Destinations.PROJECT_DETAIL}/{${Destinations.PROJECT_ID}}",
                arguments = listOf(navArgument(Destinations.PROJECT_ID) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val projectId = backStackEntry.arguments?.getString(Destinations.PROJECT_ID)
                Log.d("NavHost", "Project ID in NavHost: $projectId")
                if (projectId != null) {
                    ProjectDetailScreen(
                        navHostController = navHostController,
                        projectId = projectId,
                        homeScreenViewModel = homeScreenViewModel
                    )
                } else {
                    Log.e("NavigationError", "Project ID is null in backStackEntry")
                }
            }
            composable(Destinations.CREATE_PROJECT) {
                CreateProjectScreen(
                    navHostController = navHostController,
                    createProjectTaskSharedViewModel = createProjectTaskSharedViewModel,
                    sharedViewModel = sharedViewModel
                )
            }
            composable(Destinations.CREATE_TASK) {
                CreateTaskScreen(
                    navHostController = navHostController,
                    createProjectTaskSharedViewModel = createProjectTaskSharedViewModel
                )
            }
            composable(
                route = "${Destinations.EDIT_DELETE_PROJECT}/{${Destinations.PROJECT_ID}}",
                arguments = listOf(navArgument(Destinations.PROJECT_ID) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val projectId =
                    requireNotNull(backStackEntry.arguments?.getString(Destinations.PROJECT_ID))
                EditDeleteProjectScreen(
                    navHostController = navHostController,
                    projectId = projectId,
                    homeScreenViewModel = homeScreenViewModel
                )
            }
        }
    }
}
