package com.omkar.hadpad.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.omkar.hadpad.R
import com.omkar.hadpad.ui.components.ActionSearchBar
import com.omkar.hadpad.ui.components.ActionTopBar
import com.omkar.hadpad.ui.components.AddTaskBottomSheet
import com.omkar.hadpad.ui.components.CategoryChips
import com.omkar.hadpad.ui.components.TaskCard
import com.omkar.hadpad.ui.viewmodel.TaskViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionNotesHome(viewModel: TaskViewModel) {

    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),


        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                },
                shape = RoundedCornerShape(30.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 2.dp
                ),
                modifier = Modifier
                    .height(58.dp)
                    .width(58.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.rounded_add_24),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        )

    { innerPadding ->

        Column {

            ActionTopBar(modifier = Modifier.padding(innerPadding))

            Spacer(modifier = Modifier.height(6.dp))

            ActionSearchBar()

            Spacer(modifier = Modifier.height(12.dp))

            CategoryChips(
                selectedCategory = selectedCategory,
                onCategorySelected = {
                    selectedCategory = it
                }
            )

            TaskScreen(
                viewModel = viewModel,
                selectedCategory = selectedCategory,
            )

        }

        if (showBottomSheet) {

            AddTaskBottomSheet(
                viewModel = viewModel,
                onDismiss = {
                    showBottomSheet = false
                }
            )
        }

    }
}

@Composable
fun TaskScreen(
    viewModel: TaskViewModel,
    selectedCategory: String,
) {

    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    val filteredTasks = when (selectedCategory) {

        "Pending" -> tasks.filter {
            !it.isCompleted
        }

        "Completed" -> tasks.filter {
            it.isCompleted
        }

        else -> tasks
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Task list
        LazyColumn(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(filteredTasks) { task ->

                TaskCard(
                    task = task,
                    onCheckedChange = {
                        viewModel.toggleComplete(task)
                    }
                )
            }
        }
    }
}
