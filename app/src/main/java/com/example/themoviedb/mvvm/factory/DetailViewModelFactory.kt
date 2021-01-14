package com.example.themoviedb.mvvm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedb.api.InjectComponent
import com.example.themoviedb.mvvm.model.DetailRepository
import com.example.themoviedb.mvvm.viewmodel.DetailViewModel
import com.example.themoviedb.utils.App
import javax.inject.Inject

class DetailViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var detailRepository: DetailRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        var injectComponent : InjectComponent =  App.injectComponent
        injectComponent.inject(this)
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(detailRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
