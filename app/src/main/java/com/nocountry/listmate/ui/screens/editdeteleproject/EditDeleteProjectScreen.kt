package com.nocountry.listmate.ui.screens.editdeteleproject

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.R
import com.nocountry.listmate.data.repository.ProjectRepositoryImpl
import com.nocountry.listmate.domain.ProjectRepository
import com.nocountry.listmate.ui.components.ButtonComponent
import com.nocountry.listmate.ui.components.InputTextFieldComponent
import com.nocountry.listmate.ui.components.ParticipantSpotComponent
import com.nocountry.listmate.ui.components.TaskItem
import com.nocountry.listmate.ui.components.TopBarComponent
import com.nocountry.listmate.ui.navigation.Destinations
import com.nocountry.listmate.ui.screens.createproject.onAddTaskClick
import com.nocountry.listmate.ui.screens.home.HomeScreenViewModel
import com.nocountry.listmate.ui.screens.sharedviewmodels.CreateProjectTaskSharedViewModel
import androidx.compose.foundation.lazy.items
import com.nocountry.listmate.ui.screens.sharedviewmodels.CreateProjectTaskSharedViewModelFactory


@Composable
fun EditDeleteProjectScreen(
    navHostController: NavHostController,
    projectId: String,
    homeScreenViewModel: HomeScreenViewModel
) {

    val projectRepository: ProjectRepository =
        ProjectRepositoryImpl(FirebaseFirestore.getInstance())

    val createProjectTaskSharedViewModel: CreateProjectTaskSharedViewModel = viewModel(
        factory = CreateProjectTaskSharedViewModelFactory(projectRepository)
    )

    val editDeleteProjectViewModel: EditDeleteProjectViewModel = viewModel(
        factory = EditDeleteViewModelFactory(projectRepository)
    )

    val uiState by homeScreenViewModel.uiState.collectAsState()
    val selectedProject = uiState.projects.find { it.id == projectId }

    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val loading by editDeleteProjectViewModel.loading.observeAsState(false)
    val context = LocalContext.current

    val searchText by createProjectTaskSharedViewModel.searchText.collectAsState()
    val users by createProjectTaskSharedViewModel.users.collectAsState()
    val isSearching by createProjectTaskSharedViewModel.isSearching.collectAsState()
    val projectParticipants by createProjectTaskSharedViewModel.projectParticipants.observeAsState(
        mutableListOf()
    )
    val tasks by createProjectTaskSharedViewModel.tasks.observeAsState(mutableListOf())

    Scaffold(
        topBar = {
            TopBarComponent(title = R.string.edit_project, navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Arrow back"
                    )
                }
            },
                actions = {
                    IconButton(onClick = {
                        showDeleteConfirmation = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete project"
                        )
                    }
                })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                if (selectedProject != null) {
                    var projectName by remember { mutableStateOf(selectedProject.name) }
                    InputTextFieldComponent(
                        value = projectName,
                        onValueChange = { newValue ->
                            projectName = newValue
                            selectedProject.name = newValue
                        },
                        label = R.string.project_name_input_label,
                        leadingIcon = null,
                        trailingIcon = { /*TODO*/ },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        placeholder = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    InputTextFieldComponent(
                        value = searchText,
                        onValueChange = createProjectTaskSharedViewModel::onSearchTextChange,
                        label = R.string.find_participants_label,
                        leadingIcon = Icons.Default.Search,
                        trailingIcon = {},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = null
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (isSearching) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    } else if (searchText.length >= 2) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            users.forEach { participant ->
                                Text(
                                    text = "${participant.name} ${participant.lastName}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp)
                                        .clickable {
                                            createProjectTaskSharedViewModel.onAddParticipantToProject(
                                                participant
                                            )
                                            createProjectTaskSharedViewModel.onSearchTextChange("")
                                        }
                                )
                            }
                        }
                    }
                }
            }
            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp
                ) {
                    projectParticipants.forEach { participant ->
                        ParticipantSpotComponent(name = participant.name + " " + participant.lastName)
                    }
                }
            }
            item {
                Text(text = "Add project description:", style = MaterialTheme.typography.bodyMedium)
            }
            item {
                if (selectedProject != null) {
                    var projectDescription by remember { mutableStateOf(selectedProject.description) }
                    InputTextFieldComponent(
                        value = projectDescription,
                        onValueChange = { newValue ->
                            projectDescription = newValue
                            selectedProject.description = projectDescription
                        },
                        label = null,
                        leadingIcon = null,
                        trailingIcon = { },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        placeholder = null
                    )
                }
            }
            item {
                ButtonComponent(
                    text = R.string.addtask_button_label,
                    onClick = {
                        if (selectedProject != null) {
                            if (selectedProject.name.isNotBlank()) {
                                onAddTaskClick(
                                    createProjectTaskSharedViewModel,
                                    projectParticipants,
                                    navHostController
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please insert project name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                    icon = Icons.Default.Add,
                    textColor = MaterialTheme.colorScheme.surfaceTint,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                )
            }
            if (tasks.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tasks added",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                        )
                    }
                }
            } else {
                items(tasks) { task ->
                    TaskItem(task)
                }
            }
//            item {
//                Spacer(modifier = Modifier.height(16.dp))
//                ButtonComponent(
//                    text = R.string.create_project_button_label,
//                    onClick = {
//                        if (selectedProject != null) {
//                            if (selectedProject.name.isNotBlank()) {
//                                onCreateProjectClick(
//                                    navHostController,
//                                    createProjectTaskSharedViewModel,
//                                    userId,
//                                    projectDescription
//                                )
//
//
//                            } else {
//                                Toast.makeText(
//                                    context,
//                                    "Please insert project name",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    },
//                    backgroundColor = Color(0xFFFFE086),
//                    icon = null,
//                    textColor = MaterialTheme.colorScheme.surfaceTint,
//                    textStyle = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(48.dp),
//                )
//            }
        }
        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text(text = "Confirm Delete") },
                text = { Text("Are you sure you want to delete this project?") },
                confirmButton = {
                    Button(
                        onClick = {
                            editDeleteProjectViewModel.deleteProject(projectId) {
                                Toast.makeText(
                                    context,
                                    "Project and tasks deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navHostController.navigate(Destinations.HOME)
                            }
                            showDeleteConfirmation = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Delete", color = Color.White)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDeleteConfirmation = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Deleting project and tasks",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                )
            }
        }
    }
}