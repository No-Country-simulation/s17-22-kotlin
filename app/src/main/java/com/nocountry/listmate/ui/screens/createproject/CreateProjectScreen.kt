package com.nocountry.listmate.ui.screens.createproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nocountry.listmate.R
import com.nocountry.listmate.ui.components.InputTextFieldComponent
import com.nocountry.listmate.ui.components.TopBarComponent
import com.nocountry.listmate.ui.theme.ListMateTheme

@Composable
fun CreateProjectScreen() {

    var projectTitle by rememberSaveable { mutableStateOf("")}

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
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InputTextFieldComponent(
                value = projectTitle,
                onValueChange = {projectTitle = it},
                label = R.string.project_name_input_label,
                leadingIcon = null,
                trailingIcon = { },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            InputTextFieldComponent(
                value = "",
                onValueChange = {},
                label = R.string.find_participants_label,
                leadingIcon = Icons.Default.Search,
                trailingIcon = { /*TODO*/ },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.inversePrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "icon",
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Agregar tarea",
                        color = MaterialTheme.colorScheme.surfaceTint,
                        style = MaterialTheme.typography.bodyMedium,
                    )

                }

            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE086)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Crear proyecto", color = MaterialTheme.colorScheme.surfaceTint,
                    style = MaterialTheme.typography.bodyMedium,
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateProjectScreenPreview() {
    ListMateTheme {
        CreateProjectScreen()
    }
}