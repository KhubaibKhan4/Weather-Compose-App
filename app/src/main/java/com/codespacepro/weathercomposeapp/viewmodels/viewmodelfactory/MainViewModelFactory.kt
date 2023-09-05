package com.codespacepro.weathercomposeapp.viewmodels.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codespacepro.weathercomposeapp.repository.Repository
import com.codespacepro.weathercomposeapp.viewmodels.viewmodel.MainViewModel

class MainViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}