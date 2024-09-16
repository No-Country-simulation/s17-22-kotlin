package com.nocountry.listmate.ui.screens.editdeteleproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.R
import com.nocountry.listmate.data.repository.ProjectRepositoryImpl
import com.nocountry.listmate.domain.ProjectRepository
import com.nocountry.listmate.ui.components.InputTextFieldComponent
import com.nocountry.listmate.ui.components.TopBarComponent
import com.nocountry.listmate.ui.navigation.Destinations


@Composable
fun EditDeleteProjectScreen(navHostController: NavHostController, projectId: String) {

    val projectRepository: ProjectRepository =
        ProjectRepositoryImpl(FirebaseFirestore.getInstance())

    val editDeleteProjectViewModel: EditDeleteProjectViewModel = viewModel(
        factory = EditDeleteViewModelFactory(projectRepository)
    )

    var showDeleteConfirmation by remember { mutableStateOf(false) }

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
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete project")
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
                    value = null.toString(),
                    onValueChange = {},
                    leadingIcon = null,
                    trailingIcon = { /*TODO*/ },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    placeholder = null
                )
            }
        }
        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text(text = "Confirm Delete") },
                text = { Text("Are you sure you want to delete this project?") },
                confirmButton = {
                    Button(
                        onClick = {
                            editDeleteProjectViewModel.deleteProject(projectId)
                            showDeleteConfirmation = false
                            navHostController.navigate(Destinations.HOME)
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
}