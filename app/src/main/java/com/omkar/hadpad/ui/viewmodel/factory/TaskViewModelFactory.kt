package com.omkar.hadpad.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omkar.hadpad.data.repository.TaskRepository
import com.omkar.hadpad.ui.viewmodel.TaskViewModel

class TaskViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return TaskViewModel(repository) as T
    }
}