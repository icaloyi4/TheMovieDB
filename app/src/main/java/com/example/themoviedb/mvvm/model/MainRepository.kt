package com.example.themoviedb.mvvm.model

import com.example.themoviedb.api.InjectComponent
import com.example.themoviedb.api.ApiClient
import com.example.themoviedb.utils.App
import retrofit2.Retrofit
import javax.inject.Inject

class MainRepository() {
    @Inject
    lateinit var retrofit: Retrofit

    init {

        var injectComponent :InjectComponent =  App.injectComponent
        injectComponent.inject(this)
    }

    var api:ApiClient = retrofit.create(ApiClient::class.java)

    suspend fun getMovieByGenre(page: Int, genre: String) = api.getMovie(page, genre)
    suspend fun getMovieGenre() = api.getMovieGenre()
}