package com.nocountry.listmate.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nocountry.listmate.R
import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.data.model.dummyProjects
import com.nocountry.listmate.ui.components.BottomNavigationBar
import com.nocountry.listmate.ui.navigation.Destinations
import com.nocountry.listmate.ui.theme.ListMateTheme

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val projects = dummyProjects
    val dummyName by rememberSaveable { mutableStateOf("Michael") }
    val dummyProjectsCount by rememberSaveable { mutableIntStateOf(2) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navHostController)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navHostController.navigate(Destinations.CREATE_PROJECT) },
                icon = {
                    Icon(
                        Icons.Filled.Add,
                        "Create button",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                text = {
                    Text(
                        text = stringResource(id = R.string.fab_button),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ProjectsOverview(userName = dummyName, projectsCount = dummyProjectsCount)
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
//            if (projects.isNotEmpty()) {
//                ProjectsList(projects = projects)
//            }

        }

    }
}

@Composable
fun ProjectsOverview(userName: String, projectsCount: Int) {

    Column(
        modifier = Modifier
            .width(360.dp)
            .height(160.dp)
            .padding(start = 30.dp, top = 50.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Hello, $userName",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Your\nProjects ($projectsCount)",
            style = MaterialTheme.typography.titleLarge.copy(
                lineHeight = 40.sp
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ProjectsList(
    projects: List<Project>
) {
    val colorsProjects =
        listOf(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.errorContainer
        )

    LazyColumn {
        itemsIndexed(projects) { index, project ->
            val backgroundColor = colorsProjects[index % colorsProjects.size]
            ProjectSection(project = project, backgroundColor)
        }
    }
}

@Composable
fun ProjectSection(project: Project, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = project.name,
                    modifier = Modifier.width(210.dp),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 40.sp,
                        lineHeight = 44.sp
                    ),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
                Text(
                    text = project.tasks?.size.toString(), modifier = Modifier.width(210.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 30.sp,
                        lineHeight = 44.sp
                    ),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
                Text(
                    text = project.participants.size.toString(), modifier = Modifier.width(210.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                    ),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )


            }
            SmallFloatingActionButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.edit_icon),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ListMateTheme {
        HomeScreen(navHostController = NavHostController(LocalContext.current))
    }
}