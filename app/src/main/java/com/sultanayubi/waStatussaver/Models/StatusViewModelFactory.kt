package com.sultanayubi.waStatussaver.Models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sultanayubi.waStatussaver.Data.StatusRepo

class StatusViewModelFactory(private val repo: StatusRepo):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatusViewModel(repo) as T
    }
}