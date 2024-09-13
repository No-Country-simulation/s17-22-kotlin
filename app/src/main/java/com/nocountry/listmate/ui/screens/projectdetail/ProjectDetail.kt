package com.nocountry.listmate.ui.screens.projectdetail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.ui.screens.home.HomeScreenViewModel
import com.nocountry.listmate.ui.screens.my_tasks.MyTasksViewModel
import com.nocountry.listmate.ui.theme.ListMateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    projectId: String,
    homeScreenViewModel: HomeScreenViewModel = viewModel(),
    myTasksViewModel: MyTasksViewModel = viewModel()
) {
    val uiState by homeScreenViewModel.uiState.collectAsState()
    val myTasks by myTasksViewModel.myTasks.observeAsState(emptyList())

    val selectedProject = uiState.projects.find { it.id == projectId }
    val projectDisplayName = selectedProject?.name ?: "Proyecto desconocido"

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = projectDisplayName) })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            selectedProject?.let { project ->
                item {
                    Column {
                        Text(
                            text = project.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Participants: ${project.participants}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                items(project.tasks.orEmpty()) { taskId ->
                    val task = myTasks.find { it.id == taskId }

                    task?.let {
                        AnimatedTaskItem(task = it, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedTaskItem(task: Task, modifier: Modifier = Modifier) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer, label = ""
    )

    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .padding(16.dp)
        ) {
            Row {
                Text(text = task.taskName)
                TaskItemButton(expanded = expanded, onClick = { expanded = !expanded })
            }
            if (expanded) {
                Text(text = task.description)
            }
        }
    }
}

@Composable
fun TaskItemButton(expanded: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
fun ProjectDetailScreenPreview() {
    ListMateTheme {
        ProjectDetailScreen("", viewModel(), viewModel())
    }
}