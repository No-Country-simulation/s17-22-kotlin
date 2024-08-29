package com.nocountry.listmate.ui.screens.createtask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nocountry.listmate.R
import com.nocountry.listmate.ui.components.InputTextFieldComponent
import com.nocountry.listmate.ui.components.TopBarComponent
import com.google.accompanist.flowlayout.FlowRow

import com.nocountry.listmate.ui.components.ButtonComponent
import com.nocountry.listmate.ui.components.ParticipantSpotComponent
import com.nocountry.listmate.ui.theme.ListMateTheme


@Composable
fun CreateTaskScreen() {
    var taskTitle by rememberSaveable { mutableStateOf("") }
    var taskDescription by rememberSaveable { mutableStateOf("") }

    val dummyParticipants = listOf(
        "Nikoll Quintero Chavez",
        "Pepito Perez",
        "Rosa Rodriguez",
        "Moises",
        "Yonisa"
    )


    Scaffold(
        topBar = {
            TopBarComponent(title = R.string.create_task, navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Arrow back"
                    )
                }
            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InputTextFieldComponent(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                label = R.string.task_name_input_label,
                leadingIcon = null,
                trailingIcon = { },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )
            InputTextFieldComponent(
                value = "",
                onValueChange = {},
                label = R.string.find_participants_label,
                leadingIcon = Icons.Default.Search,
                trailingIcon = { /*TODO*/ },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisSpacing = 8.dp,
                crossAxisSpacing = 8.dp
            ) {
                dummyParticipants.forEach { participant ->
                    ParticipantSpotComponent(name = participant)
                }
            }
            Spacer(modifier = Modifier.padding(0.dp, 10.dp))
            Text(text = "Add task description:", style = MaterialTheme.typography.bodyMedium)
            InputTextFieldComponent(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                label = null,
                leadingIcon = null,
                trailingIcon = { },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            ButtonComponent(
                text = R.string.addtask_button_label,
                onClick = { /*TODO*/ },
                backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                icon = null,
                textColor = MaterialTheme.colorScheme.surfaceTint,
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CreateTaskScreenPreview() {
    ListMateTheme {
        CreateTaskScreen()
    }
}