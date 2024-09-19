package com.nocountry.listmate.ui.screens.projectdetail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.R
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.data.repository.ProjectDetailRepositoryImpl
import com.nocountry.listmate.domain.ProjectDetailRepository
import com.nocountry.listmate.ui.components.TopBarComponent
import com.nocountry.listmate.ui.navigation.Destinations
import com.nocountry.listmate.ui.screens.home.HomeScreenViewModel
import com.nocountry.listmate.ui.theme.ListMateTheme

@Composable
fun ProjectDetailScreen(
    navHostController: NavHostController,
    projectId: String,
    homeScreenViewModel: HomeScreenViewModel
) {

    val uiState by homeScreenViewModel.uiState.collectAsState()
    val selectedProject = uiState.projects.find { it.id == projectId }
    val projectDisplayName = selectedProject?.name ?: "Unknown project"

    val projectDetailRepository: ProjectDetailRepository =
        ProjectDetailRepositoryImpl(FirebaseFirestore.getInstance())

    val projectDetailViewModel: ProjectDetailViewModel = viewModel(
        factory = ProjectDetailViewModelFactory(
            projectDetailRepository = projectDetailRepository,
            projectId = projectId
        )
    )
    val projectTask by projectDetailViewModel.projectsTasks.observeAsState(emptyList())

    LaunchedEffect(projectId) {
        projectId.let {
            projectDetailViewModel.getProjectTask(it)
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(title = R.string.my_project_top_bar,
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigate(Destinations.HOME) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Arrow back"
                        )
                    }
                }
            )
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = projectDisplayName,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = project.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            textAlign = TextAlign.Justify
                        )
                        Text(
                            text = "Participants: ${project.participants.size}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Task : ${projectTask.size}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                items(projectTask) { task ->
                    AnimatedTaskItem(task = task, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun AnimatedTaskItem(task: Task, modifier: Modifier = Modifier) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surfaceVariant, label = ""
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = task.taskName, color = MaterialTheme.colorScheme.onSurface)
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
        AnimatedTaskItem(task = Task("", "", "Test", "Nikoll", "", "Description Test", true))
    }
}