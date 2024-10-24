package com.example.assignmentvrid.ui.fragments.viewmodals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BlogViewModelProviderFactory(
    val blogsRepository: BlogRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BlogViewModel(blogsRepository) as T
    }
}