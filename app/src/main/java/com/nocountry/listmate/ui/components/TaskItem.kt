package com.nocountry.listmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.nocountry.listmate.data.Task
import com.nocountry.listmate.data.tasks
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: Task
) {
    Card(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp)) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.taskName,
                modifier = Modifier.weight(1f),
                color = Color(0xFFCFE4FF),
                style = MaterialTheme.typography.labelMedium
            )
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "Task icon",
                tint = Color(0xFFCFE4FF)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    MaterialTheme {
        TaskItem(task = tasks[0])
    }
}