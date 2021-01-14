package com.example.themoviedb.mvvm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedb.api.InjectComponent
import com.example.themoviedb.mvvm.model.MainRepository
import com.example.themoviedb.mvvm.viewmodel.MainViewModel
import com.example.themoviedb.utils.App
import javax.inject.Inject

class MainViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var retrofitRepository: MainRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        var injectComponent :InjectComponent =  App.injectComponent
        injectComponent.inject(this)
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(retrofitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}