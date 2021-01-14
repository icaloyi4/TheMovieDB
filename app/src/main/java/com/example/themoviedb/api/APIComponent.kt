package com.example.themoviedb.api

import com.example.themoviedb.mvvm.model.DetailRepository
import com.example.themoviedb.mvvm.model.MainRepository
import com.example.themoviedb.mvvm.view.DetailActivity
import com.example.themoviedb.mvvm.view.MainActivity
import com.example.themoviedb.mvvm.viewmodel.DetailViewModel
import com.example.themoviedb.mvvm.factory.DetailViewModelFactory
import com.example.themoviedb.mvvm.viewmodel.MainViewModel
import com.example.themoviedb.mvvm.factory.MainViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIModule::class])
interface APIComponent {
    fun inject(mainRepository: MainRepository)
    fun inject(mainViewModel: MainViewModel)
    fun inject(mainViewModelFactory: MainViewModelFactory)
    fun inject(mainActivity : MainActivity)

    fun inject(detailRepository: DetailRepository)
    fun inject(detailViewModel: DetailViewModel)
    fun inject(detailViewModelFactory: DetailViewModelFactory)
    fun inject(detailActivity : DetailActivity)
}