package com.omkar.hadpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.omkar.hadpad.data.local.DatabaseProvider
import com.omkar.hadpad.data.repository.TaskRepository
import com.omkar.hadpad.ui.theme.ActionNotesTheme
import com.omkar.hadpad.ui.viewmodel.TaskViewModel
import com.omkar.hadpad.data.model.Task

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val db = DatabaseProvider.getDatabase(applicationContext)
            val dao = db.taskDao()
            val repository = TaskRepository(dao)
            val viewModel = TaskViewModel(repository)

            ActionNotesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    TaskScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}



@Composable
fun TaskScreen(viewModel: TaskViewModel, modifier: Modifier) {

    val tasks by viewModel.tasks.collectAsState()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(2) } // default medium

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {

        // Title input
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description input
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Priority selector (simple buttons)
        Row {
            Button(onClick = { priority = 1 }) { Text("Low") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { priority = 2 }) { Text("Medium") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { priority = 3 }) { Text("High") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Task button
        Button(
            onClick = {
                if (title.isNotBlank()) {
                    viewModel.addTask(
                        Task(
                            title = title,
                            description = description,
                            priority = priority,
                            dueDate = System.currentTimeMillis()
                        )
                    )

                    // Clear inputs
                    title = ""
                    description = ""
                    priority = 2
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task list
        LazyColumn {
            items(tasks) { task ->

                val priorityColor = when (task.priority) {
                    1 -> Color.Green
                    2 -> Color.Yellow
                    else -> Color.Red
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(text = task.description)

                            Text(
                                text = when (task.priority) {
                                    1 -> "Low"
                                    2 -> "Medium"
                                    else -> "High"
                                },
                                color = priorityColor
                            )
                        }

                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = {
                                viewModel.toggleComplete(task)
                            }
                        )
                    }
                }
            }
        }
    }
}


