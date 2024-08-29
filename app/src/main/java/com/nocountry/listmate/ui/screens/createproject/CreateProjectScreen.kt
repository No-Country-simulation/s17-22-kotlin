package com.nocountry.listmate.ui.screens.createproject

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.nocountry.listmate.data.tasks
import com.nocountry.listmate.ui.components.ButtonComponent
import com.nocountry.listmate.ui.components.InputTextFieldComponent
import com.nocountry.listmate.ui.components.ParticipantSpotComponent
import com.nocountry.listmate.ui.components.TaskItem
import com.nocountry.listmate.ui.components.TopBarComponent
import com.nocountry.listmate.ui.navigation.Destinations
import com.nocountry.listmate.ui.theme.ListMateTheme

@Composable
fun CreateProjectScreen(navHostController: NavHostController) {

    var projectTitle by rememberSaveable { mutableStateOf("") }
    val tasks = rememberSaveable { tasks }
    val dummyParticipants = listOf(
        "Nikoll Quintero Chavez",
        "Pepito Perez",
        "Rosa Rodriguez",
        "Moises",
        "Yonisa"
    )

    Scaffold(
        topBar = {
            TopBarComponent(title = R.string.create_project, navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
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
                    onValueChange = { projectTitle = it },
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
                    onClick = { navHostController.navigate(Destinations.CREATE_TASK) },
                    backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                    icon = Icons.Default.Add,
                    textColor = MaterialTheme.colorScheme.surfaceTint,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                )
            }
            items(tasks) { task ->
                TaskItem(task = task)
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

@Preview(showBackground = true)
@Composable
fun CreateProjectScreenPreview() {
    ListMateTheme {
        CreateProjectScreen(navHostController = NavHostController(LocalContext.current))
    }
}