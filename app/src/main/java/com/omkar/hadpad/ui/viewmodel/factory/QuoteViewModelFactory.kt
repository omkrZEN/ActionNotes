package com.omkar.hadpad.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omkar.hadpad.data.repository.QuoteRepository
import com.omkar.hadpad.ui.viewmodel.QuoteViewModel

class QuoteViewModelFactory(
    private val repository: QuoteRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return QuoteViewModel(repository) as T
    }
}