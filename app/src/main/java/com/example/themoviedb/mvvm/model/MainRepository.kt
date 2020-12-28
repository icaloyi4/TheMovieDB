package com.example.themoviedb.mvvm.model

import com.example.themoviedb.api.ApiClient

class MainRepository(private val api: ApiClient) {
    fun getMovieByGenre(page: Int, genre: String) = api.getMovie(page, genre)
    fun getMovieGenre() = api.getMovieGenre()
}