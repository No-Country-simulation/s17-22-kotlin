package com.nocountry.listmate.ui.screens.createproject

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import com.nocountry.listmate.R
import com.nocountry.listmate.ui.components.ButtonComponent
import com.nocountry.listmate.ui.components.InputTextFieldComponent
import com.nocountry.listmate.ui.components.ParticipantSpotComponent
import com.nocountry.listmate.ui.components.TaskItem
import com.nocountry.listmate.ui.components.TopBarComponent
import com.nocountry.listmate.ui.navigation.Destinations
import com.nocountry.listmate.ui.screens.sharedviewmodels.CreateProjectTaskSharedViewModel
import com.nocountry.listmate.ui.theme.ListMateTheme

@Composable
fun CreateProjectScreen(
    navHostController: NavHostController,
    createProjectTaskSharedViewModel: CreateProjectTaskSharedViewModel
) {
    val projectTitle by createProjectTaskSharedViewModel.projectTitle.observeAsState("")
    val tasks by createProjectTaskSharedViewModel.tasks.observeAsState(mutableListOf())
    val dummyParticipants = listOf(
        "Nikoll Quintero Chavez",
        "Pepito Perez",
        "Rosa Rodriguez",
        "Moises",
        "Yonisa"
    )
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBarComponent(title = R.string.create_project, navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Arrow back"
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
                InputTextFieldComponent(
                    value = projectTitle,
                    onValueChange = { createProjectTaskSharedViewModel.setProjectTitle(it) },
                    label = R.string.project_name_input_label,
                    leadingIcon = null,
                    trailingIcon = { },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                InputTextFieldComponent(
                    value = "",
                    onValueChange = {},
                    label = R.string.find_participants_label,
                    leadingIcon = Icons.Default.Search,
                    trailingIcon = { /*TODO*/ },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp
                ) {
                    dummyParticipants.forEach { participant ->
                        ParticipantSpotComponent(name = participant)
                    }
                }
            }
            item {
                ButtonComponent(
                    text = R.string.addtask_button_label,
                    onClick = {
                        if (projectTitle.isNotBlank()) {
                            onAddTaskClick(
                                createProjectTaskSharedViewModel,
                                dummyParticipants,
                                navHostController
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Please insert project name",
                                Toast.LENGTH_SHORT
                            ).show()
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
            item {
                Spacer(modifier = Modifier.height(16.dp))
                ButtonComponent(
                    text = R.string.create_project_button_label,
                    onClick = { /*TODO*/ },
                    backgroundColor = Color(0xFFFFE086),
                    icon = null,
                    textColor = MaterialTheme.colorScheme.surfaceTint,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                )
            }
        }
    }
}

private fun onAddTaskClick(
    createProjectTaskSharedViewModel: CreateProjectTaskSharedViewModel,
    dummyParticipants: List<String>,
    navHostController: NavHostController
) {
    createProjectTaskSharedViewModel.setProjectParticipants(dummyParticipants)
    navHostController.navigate(Destinations.CREATE_TASK)
}

@Preview(showBackground = true)
@Composable
fun CreateProjectScreenPreview() {
    ListMateTheme {
        val mockViewModel = CreateProjectTaskSharedViewModel()
        CreateProjectScreen(
            navHostController = NavHostController(LocalContext.current),
            createProjectTaskSharedViewModel = mockViewModel
        )
    }
}