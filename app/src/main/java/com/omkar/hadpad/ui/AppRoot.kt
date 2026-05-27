package com.omkar.hadpad.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.omkar.hadpad.R
import com.omkar.hadpad.data.local.DatabaseProvider
import com.omkar.hadpad.data.local.Task
import com.omkar.hadpad.data.remote.RetrofitProvider
import com.omkar.hadpad.data.repository.QuoteRepository
import com.omkar.hadpad.data.repository.TaskRepository
import com.omkar.hadpad.ui.state.QuoteUiState
import com.omkar.hadpad.ui.theme.Typography
import com.omkar.hadpad.ui.viewmodel.QuoteViewModel
import com.omkar.hadpad.ui.viewmodel.TaskViewModel
import com.omkar.hadpad.ui.viewmodel.factory.QuoteViewModelFactory
import com.omkar.hadpad.ui.viewmodel.factory.TaskViewModelFactory
import java.util.Calendar

@Composable
fun AppRoot() {

    ////////////////////// Quote

    val apiService = remember { RetrofitProvider.api }
    val quoteRepository = remember { QuoteRepository(apiService) }

    val quoteFactory = remember {
        QuoteViewModelFactory(quoteRepository)
    }

    val quoteViewModel: QuoteViewModel = viewModel(factory = quoteFactory)

    ////////////////////// Task

    val appContext = LocalContext.current.applicationContext

    val db = remember { DatabaseProvider.getDatabase(appContext) }
    val dao = remember { db.taskDao() }

    val taskRepository = remember { TaskRepository(dao) }

    val taskFactory = remember {
        TaskViewModelFactory(taskRepository)
    }

    val taskViewModel: TaskViewModel = viewModel(factory = taskFactory)

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeScreen(quoteViewModel = quoteViewModel, taskViewModel = taskViewModel)
    }

}

@Composable
fun HomeScreen(quoteViewModel: QuoteViewModel, taskViewModel: TaskViewModel) {

    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val greetings = when (currentHour) {

        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"

    }

    val subGreeting = when (currentHour) {
        in 5..11 -> "Stay productive today"
        in 12..16 -> "Keep the momentum going"
        in 17..20 -> "Wrap up your tasks smoothly"
        else -> "Plan tomorrow with clarity"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {

            Image(
                modifier = Modifier.matchParentSize(),
                painter = painterResource(R.drawable.quote_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars)
                    .align(Alignment.TopCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background.copy(alpha = 0.55f),
                                Color.Transparent
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
                    .align(alignment = Alignment.Center)

            ) {

                Text(
                    text = greetings,
                    style = Typography.headlineLarge,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = subGreeting,
                    style = Typography.bodyMedium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                QuoteScreen(quoteViewModel)


            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background.copy(alpha = 0.45f),
                            )
                        )
                    )
            )
        }




        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(color = MaterialTheme.colorScheme.secondary)

        Spacer(modifier = Modifier.height(16.dp))

        TaskScreen(taskViewModel)

    }

}


@Composable
fun QuoteScreen(quoteViewModel: QuoteViewModel) {
    val uiState by quoteViewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 180.dp, max = 280.dp)
                .padding(16.dp)
        ) {
            when (val state = uiState) {
                is QuoteUiState.Loading -> {
                    Text(
                        text = "Loading Quote...",
                        style = Typography.displayMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is QuoteUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.quote.q,
                            style = Typography.displayMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "— ${state.quote.a}",
                            style = Typography.labelMedium,
                            textAlign = TextAlign.End,
                            color = Color.Black
                        )
                    }
                }

                is QuoteUiState.Error -> {
                    Text(
                        text = state.message,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {

    val task by taskViewModel.tasks.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(task) { task ->
                TaskRow(task)
            }
        }

    }
}


@Composable
fun TaskRow(task: Task) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Text(
            text = task.title,
            style = Typography.displayMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = task.description,
            style = Typography.displaySmall
        )

    }

}